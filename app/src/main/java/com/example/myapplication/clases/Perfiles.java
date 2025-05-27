package com.example.myapplication.clases;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.R;
import com.example.myapplication.adapters.PerfilesAdapter;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Perfiles extends AppCompatActivity {

    ListView listViewPerfiles; // Lista para mostrar perfiles
    List<Perfil> perfiles; // Lista completa de perfiles
    List<Perfil> perfilesFiltrados; // Lista de perfiles filtrados por búsqueda
    ApiService crudInterface; // Servicio API para llamadas

    EditText editTextFiltro; // Campo para filtrar perfiles por nombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfiles); // Layout que contiene ListView y EditText

        // Vinculamos las vistas con variables
        listViewPerfiles = findViewById(R.id.listViewPerfiles);
        editTextFiltro = findViewById(R.id.filtrarNombre);

        // Obtener todos los perfiles desde el backend
        getAll();

        // Añadir listener para detectar cambios en el texto del filtro
        editTextFiltro.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            // Cada vez que cambia el texto, filtramos la lista por nombre
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarPorNombre(s.toString());
            }
        });
    }

    // Método para obtener todos los perfiles del servidor con Retrofit
    private void getAll() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url)) // URL base del API
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
                // Guardamos la lista completa y la filtrada (inicialmente igual)
                perfiles = response.body();
                perfilesFiltrados = perfiles;

                // Mostramos la lista en el ListView usando el adapter personalizado
                listViewPerfiles.setAdapter(new PerfilesAdapter(perfilesFiltrados, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.e("Throw err; ", t.getMessage()); // Error de conexión o similar
            }
        });
    }

    // Filtrar la lista de perfiles por nombre usando texto ingresado
    private void filtrarPorNombre(String texto) {
        if (perfiles == null) return; // Si no hay datos, salir

        perfilesFiltrados = new ArrayList<>(); // Nueva lista para resultados filtrados
        for (Perfil perfil : perfiles) {
            if (perfil.getNombre() != null && perfil.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                // Si el nombre contiene el texto (sin distinguir mayúsculas), agregar a filtrados
                perfilesFiltrados.add(perfil);
            }
        }
        // Actualizamos el adapter con la lista filtrada
        listViewPerfiles.setAdapter(new PerfilesAdapter(perfilesFiltrados, getApplicationContext()));
    }
}