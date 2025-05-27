package com.example.myapplication.clases;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class FuncionesAdmin extends AppCompatActivity {
    // Declaración de botones
    Button listadoAlbaranes, atras, nuevoAlbaran, editarPerfil, nuevoPerfil, inventario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funciones_admin);

        // Ajusta el padding según las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inicializa los botones referenciando su ID en el layout
        nuevoAlbaran = findViewById(R.id.btnNuevoAlbaran);
        atras = findViewById(R.id.btnAtras);
        editarPerfil = findViewById(R.id.btnEditarPerfil);
        nuevoPerfil = findViewById(R.id.btnNuevoPerfil);
        inventario = findViewById(R.id.btnInventario);
        listadoAlbaranes = findViewById(R.id.btnListadoAlbaranes);
        // Botón para ir a la pantalla de Inventario
        inventario.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesAdmin.this, Inventario.class);
            startActivity(intent);
        });
        // Botón para ir a la pantalla de creación de nuevo perfil
        nuevoPerfil.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesAdmin.this, NuevoPerfil.class);
            startActivity(intent);
        });
        // Botón para ir a la pantalla de edición de perfiles
        editarPerfil.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesAdmin.this, EditarPerfil.class);
            startActivity(intent);
        });
        // Botón para volver atrás (a ListadoProductos)
        atras.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesAdmin.this, ListadoProductos.class);
            startActivity(intent);
            finish(); // Opcional, si quieres cerrar la activity actual
        });
        // Botón para ir a la pantalla de creación de nuevo albarán
        nuevoAlbaran.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesAdmin.this, NuevoAlbaran.class);
            startActivity(intent);
        });
        // Botón para ir a la pantalla de listado de albaranes
        listadoAlbaranes.setOnClickListener(view -> {
            Intent intent = new Intent(FuncionesAdmin.this, ListadoAlbaranes.class);
            startActivity(intent);
        });

    }
}