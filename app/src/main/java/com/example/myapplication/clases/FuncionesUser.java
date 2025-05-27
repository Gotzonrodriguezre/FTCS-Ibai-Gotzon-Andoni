package com.example.myapplication.clases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ProductosAdapter;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FuncionesUser extends AppCompatActivity {
    // Declaración de variables para los elementos de la interfaz
    Button atras;
    ListView listViewProductos;
    List<Producto> productos;
    ApiService crudInterface;
    EditText editNombre, editCantidad, editCantidadMinima;
    CheckBox checkDisponible;
    Button btnFoto, btnAceptar, btnEliminar;
    ImageView imageViewFoto;
    Producto productoSeleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funciones_user);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar componentes
        editNombre = findViewById(R.id.editTextText);
        editCantidad = findViewById(R.id.editTextText3);
        editCantidadMinima = findViewById(R.id.editTextText2);
        checkDisponible = findViewById(R.id.checkDisponible);
         imageViewFoto = findViewById(R.id.imageViewFoto);
        btnEliminar = findViewById(R.id.button8);
        btnAceptar = findViewById(R.id.button3);
        listViewProductos = findViewById(R.id.listViewAlbaran);
        atras = findViewById(R.id.btnAtras);
        // Carga todos los productos al iniciar
        getAllProductos();

        // Botón volver
        atras.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesUser.this, ListadoProductos.class);
            startActivity(intent);
            finish();
        });

        // Botón eliminar
        btnEliminar.setOnClickListener(view -> {
            if (productoSeleccionado != null) {
                eliminarProducto(productoSeleccionado.getId());
            } else {
                Toast.makeText(this, "Selecciona un producto primero", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón aceptar (actualizar)
        btnAceptar.setOnClickListener(view -> {
            if (productoSeleccionado != null) {
                actualizarProducto();
            } else {
                Toast.makeText(this, "Selecciona un producto primero", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Método para obtener todos los productos desde la API
    private void getAllProductos() {

        // Configura Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Inicializa la interfaz de la API
        crudInterface = retrofit.create(ApiService.class);
        Call<List<Producto>> call = crudInterface.getAllProductos();
        // Llama a la API de forma asíncrona
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                // Guarda la lista de productos
                productos = response.body();

                // Carga los productos en el ListView con un adaptador
                ProductosAdapter productoAdapter = new ProductosAdapter(productos, FuncionesUser.this);
                listViewProductos.setAdapter(productoAdapter);
                // Acción al seleccionar un producto
                listViewProductos.setOnItemClickListener((parent, view, position, id) -> {
                    productoSeleccionado = productos.get(position);
                    // Llena los campos con la info del producto seleccionado
                    editNombre.setText(productoSeleccionado.getNombre());
                    editCantidad.setText(String.valueOf(productoSeleccionado.getCantidad()));
                    editCantidadMinima.setText(String.valueOf(productoSeleccionado.getCantidadMinima()));
                    checkDisponible.setChecked("Disponible".equalsIgnoreCase(productoSeleccionado.getEstado()));
                    // Muestra la imagen del producto si existe
                    String base64Foto = productoSeleccionado.getFoto();
                    if (base64Foto != null && !base64Foto.isEmpty()) {
                        byte[] decodedBytes = Base64.decode(base64Foto, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        imageViewFoto.setImageBitmap(decodedBitmap);
                    } else {
                        imageViewFoto.setImageResource(R.drawable.default_profile);
                    }

                });
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }
    // Método para eliminar un producto
    private void eliminarProducto(int idProducto) {
        Call<Void> call = crudInterface.eliminarProducto(idProducto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FuncionesUser.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    getAllProductos();
                    productoSeleccionado = null;
                } else {
                    Toast.makeText(FuncionesUser.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FuncionesUser.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                Log.e("Delete error", t.getMessage());
            }
        });
    }
    // Método para actualizar un producto
    private void actualizarProducto() {
        String nombre = editNombre.getText().toString().trim();
        String cantidadStr = editCantidad.getText().toString().trim();
        String cantidadMinimaStr = editCantidadMinima.getText().toString().trim();
        boolean disponible = checkDisponible.isChecked();
        // Verifica que los campos no estén vacíos
        if (nombre.isEmpty() || cantidadStr.isEmpty() || cantidadMinimaStr.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        // Convierte los valores ingresados
        int cantidad = Integer.parseInt(cantidadStr);
        int cantidadMinima = Integer.parseInt(cantidadMinimaStr);
        String estado = disponible ? "Disponible" : "No Disponible";
        // Asigna los valores al objeto producto
        productoSeleccionado.setNombre(nombre);
        productoSeleccionado.setCantidad(cantidad);
        productoSeleccionado.setCantidadMinima(cantidadMinima);
        productoSeleccionado.setEstado(estado);
        // Llama a la API para actualizar el producto
        Call<Producto> call = crudInterface.actualizarProducto(productoSeleccionado.getId(), productoSeleccionado);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FuncionesUser.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                    getAllProductos();
                    limpiarCampos();
                    productoSeleccionado = null;
                } else {
                    Toast.makeText(FuncionesUser.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(FuncionesUser.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
                Log.e("Update error", t.getMessage());
            }
        });
    }
    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        editNombre.setText("");
        editCantidad.setText("");
        editCantidadMinima.setText("");
        checkDisponible.setChecked(false);
        btnFoto.setText("Foto");
        btnFoto.setBackgroundResource(R.drawable.default_profile);
    }
}
