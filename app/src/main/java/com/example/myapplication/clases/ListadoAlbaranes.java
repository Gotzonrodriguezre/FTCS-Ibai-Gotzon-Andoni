package com.example.myapplication.clases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.Modelo.Albaran;
import com.example.myapplication.R;
import com.example.myapplication.adapters.AlbaranAdapter;
import com.example.myapplication.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ListadoAlbaranes extends AppCompatActivity {
    // ListView para mostrar los albaranes en pantalla
    ListView listViewAlbaranes;
    // Lista que contendrá los albaranes obtenidos de la API
    List<Albaran> albaranes;
    // Interfaz de Retrofit para interactuar con la API
    ApiService crudInterface;
    // Botón para volver atrás
    Button atras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Carga el diseño de la actividad desde el archivo XML
        setContentView(R.layout.activity_listado_albaranes);
        // Asocia el ListView del layout
        listViewAlbaranes = findViewById(R.id.listViewAlbaran);
        // Llama a la función para obtener todos los albaranes
        getAllAlbaranes();
        // Asocia el botón de atrás y define su comportamiento
        atras = findViewById(R.id.btnAtras);
        atras.setOnClickListener(view -> {
            // Cambia a la actividad FuncionesAdmin
            Intent intent = new Intent(ListadoAlbaranes.this, FuncionesAdmin.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual
        });
    }

    // Método para obtener todos los albaranes desde la API
    private void getAllAlbaranes() {
        // Configuración de Retrofit con la URL base y convertidor Gson
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url)) // URL definida en strings.xml
                .addConverterFactory(GsonConverterFactory.create()) // Convierte JSON a objetos Java
                .build();
        // Se crea la instancia del servicio de la API
        crudInterface = retrofit.create(ApiService.class);
        Call<List<Albaran>> call = crudInterface.getAllAlbaranes(); // Llamada para obtener albaranes

        // Se ejecuta la llamada de forma asíncrona
        call.enqueue(new Callback<List<Albaran>>() {
            @Override
            public void onResponse(Call<List<Albaran>> call, Response<List<Albaran>> response) {
                if (!response.isSuccessful()) {
                    // Si la respuesta no fue exitosa, se muestra en log
                    Log.e("Response err: ", response.message());
                    return;
                }

                // Si la respuesta fue exitosa, se obtiene la lista de albaranes
                albaranes = response.body();

                // Se crea el adaptador y se asigna al ListView para mostrar los datos
                AlbaranAdapter albaranAdapter = new AlbaranAdapter(albaranes, ListadoAlbaranes.this);
                listViewAlbaranes.setAdapter(albaranAdapter);
            }

            @Override
            public void onFailure(Call<List<Albaran>> call, Throwable t) {
                // Si hay un error en la conexión, se registra en log
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }
}