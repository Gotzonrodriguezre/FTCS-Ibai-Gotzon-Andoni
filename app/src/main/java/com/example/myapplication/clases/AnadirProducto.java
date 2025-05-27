package com.example.myapplication.clases;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.R;


import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnadirProducto extends AppCompatActivity {

    // Variables para los elementos de la interfaz
    private ApiService apiService;
    private EditText txtNombre, txtCantidad, txtCantidadMin;
    private Button btnAnadir, atras, btnSeleccionarImagen, btnTomarFoto;
    private ImageView imgPreview;
    private String fotoBase64 = null;

    // Constantes para los códigos de solicitud de imagen
    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_CAMERA = 1002;

    // Guardar estado (imagen en base64)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fotoBase64", fotoBase64);
    }

    // Restaurar estado (imagen en base64)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fotoBase64 = savedInstanceState.getString("fotoBase64");
        if (fotoBase64 != null) {
            byte[] imageBytes = Base64.decode(fotoBase64, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgPreview.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_producto);

        // Configuración de Retrofit para consumir API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Vincular elementos de UI
        txtNombre = findViewById(R.id.txtNombreProducto);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtCantidadMin = findViewById(R.id.txtCantidadMin);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        btnAnadir = findViewById(R.id.btnAnadir);
        atras = findViewById(R.id.btnAtras);

        imgPreview = findViewById(R.id.imgPreview);

        // Botón para seleccionar imagen desde galería
        btnSeleccionarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });
        // Botón para tomar foto con la cámara
        btnTomarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                Toast.makeText(this, "No hay aplicación de cámara disponible", Toast.LENGTH_SHORT).show();
            }
        });
        // Botón para volver atrás
        atras.setOnClickListener(view -> {
            Intent intent = new Intent(AnadirProducto.this, ListadoProductos.class);
            startActivity(intent);
            finish();
        });
        // Botón para añadir producto
        btnAnadir.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();
            String cantidadStr = txtCantidad.getText().toString().trim();
            String cantidadMinStr = txtCantidadMin.getText().toString().trim();

            // Validar que todos los campos estén llenos
            if (nombre.isEmpty() || cantidadStr.isEmpty() || cantidadMinStr.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int cantidad = Integer.parseInt(cantidadStr);
            int cantidadMin = Integer.parseInt(cantidadMinStr);

            // Llamar función para comprobar nombre y añadir producto
            comprobarNombreYAnadir(nombre, cantidad, cantidadMin);
        });
    }

    // Método para recibir el resultado de la imagen seleccionada o foto tomada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    procesarImagen(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == REQUEST_CAMERA && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if (photo != null) {
                    procesarImagen(photo);
                } else {
                    Toast.makeText(this, "Error al tomar la foto", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Convertir la imagen en base64 y mostrarla en el ImageView
    private void procesarImagen(Bitmap bitmap) {
        imgPreview.setImageBitmap(bitmap);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] imageBytes = stream.toByteArray();
        fotoBase64 = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }
    // Comprobar si ya existe un producto con el mismo nombre
    private void comprobarNombreYAnadir(String nombre, int cantidad, int cantidadMin) {
        apiService.getAllProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    boolean existe = false;
                    for (Producto p : response.body()) {
                        if (p.getNombre().equalsIgnoreCase(nombre)) {
                            existe = true;
                            break;
                        }
                    }

                    if (existe) {
                        Toast.makeText(AnadirProducto.this, "Ese producto ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        anadirProductoNuevo(nombre, cantidad, cantidadMin);
                    }
                } else {
                    Toast.makeText(AnadirProducto.this, "Error al comprobar productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(AnadirProducto.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Añadir un nuevo producto llamando al API
    private void anadirProductoNuevo(String nombre, int cantidad, int cantidadMin) {
        Producto nuevoProducto = new Producto(nombre, cantidad, cantidadMin, fotoBase64);
        nuevoProducto.setEstado("Disponible");

        apiService.crearProducto(nuevoProducto).enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AnadirProducto.this, "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AnadirProducto.this, ListadoProductos.class));
                    finish();
                } else {
                    Toast.makeText(AnadirProducto.this, "Error al añadir producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(AnadirProducto.this, "Fallo al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
