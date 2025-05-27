package com.example.myapplication.clases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Modelo.Albaran;
import com.example.myapplication.R;
import com.example.myapplication.api.ApiService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NuevoAlbaran extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz
    private EditText etNombre, etCIF, etCantidad;
    private CheckBox cbPagado;
    private Button btnEnviar, btnAtras;
    EditText txtPrecio;
    private String foto;
    private static final int REQUEST_IMAGE_CAPTURE = 1; // Código para cámara
    private static final int REQUEST_IMAGE_PICK = 2;    // Código para galería
    private ImageView imgPreview;
    private String fotoBase64 = null; // Imagen codificada en Base64 para enviar
    private ApiService apiService;

    // Guarda el estado de la imagen en caso de que se reinicie la actividad
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fotoBase64", fotoBase64);
    }

    // Restaura la imagen si la actividad se reinicia
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
        setContentView(R.layout.activity_nuevo_albaran); // Establece el layout

        // Inicializa vistas de imagen y botones de foto
        imgPreview = findViewById(R.id.imgPreview);
        Button btnTomarFoto = findViewById(R.id.btnTomarFoto);
        Button btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);

        // Acciones para tomar foto o seleccionar imagen
        btnTomarFoto.setOnClickListener(v -> abrirCamara());
        btnSeleccionarImagen.setOnClickListener(v -> abrirGaleria());

        // Inicializar los campos de entrada
        txtPrecio = findViewById(R.id.txtPrecio);
        etNombre = findViewById(R.id.txtNombe);
        etCIF = findViewById(R.id.CIF);
        etCantidad = findViewById(R.id.txtCantidad);
        cbPagado = findViewById(R.id.checkPagado);
        btnEnviar = findViewById(R.id.btnAceptar);
        btnAtras = findViewById(R.id.btnAtras);

        // Configura Retrofit para hacer llamadas a la API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Acción al hacer clic en el botón de enviar
        btnEnviar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String CIF = etCIF.getText().toString();
            String cantidadStr = etCantidad.getText().toString();
            String precioText = txtPrecio.getText().toString();

            // Validación de campos
            if (nombre.isEmpty() || CIF.isEmpty() || cantidadStr.isEmpty() || precioText.isEmpty()) {
                Toast.makeText(NuevoAlbaran.this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (fotoBase64 == null) {
                Toast.makeText(NuevoAlbaran.this, "Selecciona o toma una foto antes de enviar", Toast.LENGTH_SHORT).show();
                return;
            }

            // Conversión de valores
            float precio = Float.parseFloat(precioText);
            int cantidad = Integer.parseInt(cantidadStr);
            String estado = cbPagado.isChecked() ? "Pagado" : "No Pagado";

            // Obtener la fecha actual si es compatible con Android
            String fecha = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                fecha = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
            }

            // Crear objeto Albaran
            Albaran albaran = new Albaran(
                    null,
                    fotoBase64,
                    nombre,
                    CIF,
                    cantidad,
                    estado,
                    fecha,
                    precio
            );

            // Llamar a método para enviar el albarán
            enviarAlbaran(albaran);
        });

        // Acción para el botón de regresar
        btnAtras.setOnClickListener(view -> {
            Intent intent = new Intent(NuevoAlbaran.this, FuncionesAdmin.class);
            startActivity(intent);
            finish();
        });
    }

    // Método para abrir la cámara
    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // Método para abrir la galería
    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    // Método que recibe el resultado de cámara o galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;

            // Obtener imagen de la cámara
            if (requestCode == REQUEST_IMAGE_CAPTURE && data.getExtras() != null) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
            }
            // Obtener imagen desde galería
            else if (requestCode == REQUEST_IMAGE_PICK && data.getData() != null) {
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Mostrar la imagen y convertirla a Base64
            if (imageBitmap != null) {
                imgPreview.setImageBitmap(imageBitmap);
                fotoBase64 = convertirBitmapABase64(imageBitmap);
            }
        }
    }

    // Método para convertir una imagen a Base64
    private String convertirBitmapABase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // Método para enviar el albarán al servidor mediante Retrofit
    private void enviarAlbaran(Albaran albaran) {
        Call<Albaran> call = apiService.crearAlbaran(albaran);
        call.enqueue(new Callback<Albaran>() {
            @Override
            public void onResponse(Call<Albaran> call, Response<Albaran> response) {
                if (response.isSuccessful()) {
                    Log.d("DebugNuevoAlbaran", "CIF text: " + etCIF.getText().toString());
                    Toast.makeText(NuevoAlbaran.this, "Albarán creado correctamente", Toast.LENGTH_SHORT).show();
                    // Redirigir a la pantalla de administración
                    Intent intent = new Intent(NuevoAlbaran.this, FuncionesAdmin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NuevoAlbaran.this, "Error al crear albarán: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Albaran> call, Throwable t) {
                Toast.makeText(NuevoAlbaran.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}