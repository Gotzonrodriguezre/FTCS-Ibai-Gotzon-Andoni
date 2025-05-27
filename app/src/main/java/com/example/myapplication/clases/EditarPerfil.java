package com.example.myapplication.clases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.R;
import com.example.myapplication.adapters.PerfilAdapter;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPerfil extends AppCompatActivity {

    // Declaración de elementos de la interfaz y variables
    Button atras, buttonAceptar, buttonEliminar;
    EditText editTextNombre, editTextClave, editTextCorreo;
    CheckBox checkBoxAdmin, checkBoxEstado;
    ListView listViewPerfiles;
    List<Perfil> perfiles;
    Perfil perfilSeleccionado;
    ApiService crudInterface;
    ImageView imageViewPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.editar_perfil);

        // Ajuste del padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de referencias a los elementos del layout
        editTextNombre = findViewById(R.id.editTextText);
        editTextClave = findViewById(R.id.editTextText3);
        editTextCorreo = findViewById(R.id.editTextText2);
        checkBoxAdmin = findViewById(R.id.checkBoxAdmin);
        checkBoxEstado = findViewById(R.id.checkBoxEstado);
        listViewPerfiles = findViewById(R.id.listViewPerfiles);
        atras = findViewById(R.id.btnAtras);
        buttonAceptar = findViewById(R.id.button3);
        buttonEliminar = findViewById(R.id.button8);
        imageViewPerfil = findViewById(R.id.imageViewPerfil);


        // Configuración de Retrofit para las peticiones HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        crudInterface = retrofit.create(ApiService.class);

        getAll(); // Cargar perfiles

        // Botón para volver a la pantalla anterior
        atras.setOnClickListener(view -> {
            Intent intent = new Intent(EditarPerfil.this, FuncionesAdmin.class);
            startActivity(intent);
            finish();
        });

        // Al seleccionar un perfil de la lista
        listViewPerfiles.setOnItemClickListener((adapterView, view, i, l) -> {
            perfilSeleccionado = perfiles.get(i);
            // Mostrar imagen en el ImageView
            String base64Foto = perfilSeleccionado.getFoto();
            if (base64Foto != null && !base64Foto.isEmpty()) {
                byte[] decodedBytes = Base64.decode(base64Foto, Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                imageViewPerfil.setImageBitmap(decodedBitmap);
            } else {
                imageViewPerfil.setImageResource(R.drawable.default_profile);
            }
            // Rellena los campos con los datos del perfil
            editTextNombre.setText(perfilSeleccionado.getNombre());
            editTextClave.setText(perfilSeleccionado.getClave());
            editTextCorreo.setText(perfilSeleccionado.getCorreo());
            checkBoxAdmin.setChecked(perfilSeleccionado.getTipo().equalsIgnoreCase("Administrador"));
            checkBoxEstado.setChecked(perfilSeleccionado.getEstado().equalsIgnoreCase("Activo"));
        });
        // Botón para aceptar y guardar cambios en el perfil seleccionado
        buttonAceptar.setOnClickListener(view -> {
            if (perfilSeleccionado != null) {
                perfilSeleccionado.setNombre(editTextNombre.getText().toString());
                perfilSeleccionado.setClave(editTextClave.getText().toString());
                perfilSeleccionado.setCorreo(editTextCorreo.getText().toString());
                perfilSeleccionado.setTipo(checkBoxAdmin.isChecked() ? "Administrador" : "No Administrador");
                perfilSeleccionado.setEstado(checkBoxEstado.isChecked() ? "Activo" : "No Activo");

                actualizarPerfil(perfilSeleccionado);
            } else {
                Toast.makeText(this, "Selecciona un perfil primero", Toast.LENGTH_SHORT).show();
            }
        });
        // Botón para eliminar el perfil seleccionado
        buttonEliminar.setOnClickListener(view -> {
            if (perfilSeleccionado != null) {
                eliminarPerfil(perfilSeleccionado.getId());
            } else {
                Toast.makeText(this, "Selecciona un perfil primero", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Método para obtener todos los perfiles del servidor
    private void getAll() {
        Call<List<Perfil>> call = crudInterface.getAllPerfiles();
        call.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Response err: ", response.message());
                    return;
                }
                perfiles = response.body();
                listViewPerfiles.setAdapter(new PerfilAdapter(perfiles, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.e("Throw err: ", t.getMessage());
            }
        });
    }
    // Método para actualizar un perfil en el servidor
    private void actualizarPerfil(Perfil perfil) {
        Call<Void> call = crudInterface.actualizarPerfil(perfil.getId(), perfil);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    getAll();
                } else {
                    Toast.makeText(EditarPerfil.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditarPerfil.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Método para eliminar un perfil
    private void eliminarPerfil(int id) {
        Call<Void> call = crudInterface.borrarPerfil(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "Perfil eliminado", Toast.LENGTH_SHORT).show();
                    perfilSeleccionado = null;
                    limpiarCampos();
                    getAll();
                } else {
                    Toast.makeText(EditarPerfil.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditarPerfil.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextClave.setText("");
        editTextCorreo.setText("");
        checkBoxAdmin.setChecked(false);
        checkBoxEstado.setChecked(false);
    }
}
