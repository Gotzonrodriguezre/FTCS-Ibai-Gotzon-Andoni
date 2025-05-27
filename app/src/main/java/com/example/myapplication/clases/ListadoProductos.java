package com.example.myapplication.clases;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.R;
import com.example.myapplication.adapters.PerfilesAdapter;
import com.example.myapplication.adapters.ProductoAdapter;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListadoProductos extends AppCompatActivity {

    // Declaración de elementos de la interfaz y listas de datos
    ListView listViewProductos;
    List<Producto> productos;
    ApiService crudInterface;
    ListView listViewPerfiles;
    List<Perfil> perfiles;

    // Botones de navegación y acciones
    Button atras, anadir, funcionesU, funcionesA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_productos);

        // Asociación de vistas del layout con variables Java
        atras = findViewById(R.id.btnAtras);
        anadir = findViewById(R.id.btnAnadir);
        funcionesU = findViewById(R.id.btnFuncionesU);
        funcionesA = findViewById(R.id.btnFuncionesA);
        listViewPerfiles = findViewById(R.id.listViewPerfiles);
        listViewProductos = findViewById(R.id.listViewAlbaran); // Este ID puede ser confuso, pero se usa para productos

        // Cargar datos de productos y perfiles desde la API
        getAllProductos();
        getAll();

        // Botón para volver a la pantalla de Perfiles
        atras.setOnClickListener(view -> {
            Intent intent = new Intent(ListadoProductos.this, Perfiles.class);
            startActivity(intent);
            finish();
        });

        // Botón para ir a la pantalla de añadir productos
        anadir.setOnClickListener(view -> {
            Intent intent = new Intent(ListadoProductos.this, AnadirProducto.class);
            startActivity(intent);
            finish();
        });

        // Botón para funciones de usuario
        funcionesU.setOnClickListener(view -> {
            Intent intent = new Intent(ListadoProductos.this, FuncionesUser.class);
            startActivity(intent);
            finish();
        });

        // Botón para funciones de administrador
        funcionesA.setOnClickListener(view -> {
            Intent intent = new Intent(ListadoProductos.this, FuncionesAdmin.class);
            startActivity(intent);
            finish();
        });
    }

    // Método que obtiene todos los perfiles desde la API
    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url)) // URL base definida en strings.xml
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crudInterface = retrofit.create(ApiService.class);
        Call<List<Perfil>> call = crudInterface.getAllPerfiles();

        call.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }

                // Si la respuesta fue correcta, se muestran los perfiles en el ListView
                perfiles = response.body();
                PerfilesAdapter perfilesAdapter = new PerfilesAdapter(perfiles, getApplicationContext());
                listViewPerfiles.setAdapter(perfilesAdapter);
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.e("Throw err; ", t.getMessage()); // En caso de error de red u otro fallo
            }
        });
    }

    // Método que obtiene todos los productos desde la API
    private void getAllProductos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crudInterface = retrofit.create(ApiService.class);
        Call<List<Producto>> call = crudInterface.getAllProductos();

        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }

                // Filtrar productos que estén marcados como "Disponible"
                List<Producto> todosLosProductos = response.body();
                List<Producto> productosDisponibles = new ArrayList<>();
                for (Producto p : todosLosProductos) {
                    if ("Disponible".equalsIgnoreCase(p.getEstado())) {
                        productosDisponibles.add(p);
                    }
                }

                // Se pasan los productos disponibles al adaptador para mostrarlos en el ListView
                ProductoAdapter productoAdapter = new ProductoAdapter(productosDisponibles, getApplicationContext());
                listViewProductos.setAdapter(productoAdapter);
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage()); // Error de conexión o fallo inesperado
            }
        });
    }
}