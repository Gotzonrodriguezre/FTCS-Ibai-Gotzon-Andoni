package com.example.myapplication.clases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.R;
import com.example.myapplication.api.ApiService;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class NuevoPerfil extends AppCompatActivity {

    // Constantes para identificar acciones de seleccionar imagen o tomar foto
    private static final int REQUEST_IMAGE_PICK = 1001;
    private static final int REQUEST_CAMERA = 1002;

    // Declaración de variables para UI
    private EditText txtNombre, txtCorreo, txtClave;
    private CheckBox checkBoxEstado, checkBoxTipo;
    private Button btnAceptar, btnAtras, btnTomarFoto, btnSeleccionarImagen;
    private ImageView imgPreview;

    private ApiService apiService;  // Servicio API para llamadas REST
    private String fotoBase64 = null; // Variable para almacenar la foto en Base64

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardar foto codificada en Base64 para restaurarla si cambia la configuración
        outState.putString("fotoBase64", fotoBase64);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Recuperar foto Base64 guardada
        fotoBase64 = savedInstanceState.getString("fotoBase64");
        if (fotoBase64 != null) {
            // Decodificar la foto Base64 a Bitmap y mostrarla en ImageView
            byte[] imageBytes = Base64.decode(fotoBase64, Base64.NO_WRAP);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgPreview.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_perfil);

        // Configurar Retrofit para llamadas a la API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Vincular variables con elementos de la interfaz
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtClave = findViewById(R.id.txtClave);
        checkBoxEstado = findViewById(R.id.checkBoxEstado);
        checkBoxTipo = findViewById(R.id.checkBoxTipo);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnAtras = findViewById(R.id.btnAtras);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        imgPreview = findViewById(R.id.imgPreview);

        // Acción botón Atrás: ir a pantalla anterior y cerrar esta actividad
        btnAtras.setOnClickListener(v -> {
            startActivity(new Intent(NuevoPerfil.this, FuncionesAdmin.class));
            finish();
        });

        // Acción botón Aceptar: validar campos y crear perfil
        btnAceptar.setOnClickListener(v -> {
            String nombre = txtNombre.getText().toString().trim();
            String correo = txtCorreo.getText().toString().trim();
            String clave = txtClave.getText().toString().trim();

            // Comprobar que los campos no estén vacíos
            if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validar que el correo no exista antes de crear perfil
            validarCorreoYCrearPerfil(nombre, correo, clave);
        });

        // Acción botón seleccionar imagen: abrir galería para seleccionar foto
        btnSeleccionarImagen.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE_PICK);
        });

        // Acción botón tomar foto: abrir cámara para capturar imagen
        btnTomarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                Toast.makeText(this, "No hay app de cámara disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para recibir resultados de cámara o galería
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = null;
            try {
                if (requestCode == REQUEST_IMAGE_PICK) {
                    // Si viene de galería, obtener URI y decodificar la imagen
                    Uri uri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } else if (requestCode == REQUEST_CAMERA) {
                    // Si viene de cámara, obtener imagen como Bitmap
                    bitmap = (Bitmap) data.getExtras().get("data");
                }
                if (bitmap != null) {
                    // Mostrar imagen en el ImageView y convertirla a Base64 para enviar
                    imgPreview.setImageBitmap(bitmap);
                    fotoBase64 = convertirBitmapBase64(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Convertir Bitmap a String Base64 para enviar por red
    private String convertirBitmapBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // Validar que el correo no esté registrado antes de crear el perfil
    private void validarCorreoYCrearPerfil(String nombre, String correo, String clave) {
        apiService.getAllPerfiles().enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (response.isSuccessful()) {
                    boolean existe = false;
                    // Revisar todos los perfiles para ver si el correo ya existe
                    for (Perfil p : response.body()) {
                        if (p.getCorreo().equalsIgnoreCase(correo)) {
                            existe = true;
                            break;
                        }
                    }
                    if (existe) {
                        Toast.makeText(NuevoPerfil.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show();
                    } else {
                        // Si no existe, crear nuevo perfil
                        crearNuevoPerfil(nombre, correo, clave);
                    }
                } else {
                    Toast.makeText(NuevoPerfil.this, "Error al obtener perfiles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Toast.makeText(NuevoPerfil.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Crear un nuevo perfil y enviarlo al backend
    private void crearNuevoPerfil(String nombre, String correo, String clave) {
        Perfil perfil = new Perfil();
        perfil.setNombre(nombre);
        perfil.setCorreo(correo);
        perfil.setClave(clave);
        perfil.setEstado(String.valueOf(checkBoxEstado.isChecked() ? "Activo" : "NoActivo"));
        perfil.setTipo(String.valueOf(checkBoxTipo.isChecked() ? "Administrador" : "Usuario"));
        perfil.setFoto(fotoBase64);

        // Llamada API para crear perfil
        apiService.crearPerfil(perfil).enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NuevoPerfil.this, "Perfil creado con éxito", Toast.LENGTH_SHORT).show();
                    limpiarCampos(); // Limpiar formulario
                } else {
                    Toast.makeText(NuevoPerfil.this, "Error al crear perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Toast.makeText(NuevoPerfil.this, "Error de conexión al crear perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Limpiar los campos del formulario después de crear perfil
    private void limpiarCampos() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtClave.setText("");
        checkBoxEstado.setChecked(false);
        checkBoxTipo.setChecked(false);
        imgPreview.setImageResource(R.drawable.ic_launcher_background); // Imagen por defecto
        fotoBase64 = null;
    }
}