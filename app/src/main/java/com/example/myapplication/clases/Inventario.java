package com.example.myapplication.clases;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inventario extends AppCompatActivity {
    // Tabla donde se mostrarán los productos
    private TableLayout tablaProductos;
    // Interfaz de comunicación con la API
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Carga el layout XML de la actividad
        setContentView(R.layout.activity_inventario);
        // Referencia a la tabla en el layout
        tablaProductos = findViewById(R.id.tablaProductos);
        // Botón para volver a la pantalla anterior
        Button btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> finish());
        // Configura Retrofit para conectarse con la API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Crea una instancia de la interfaz de la API
        apiService = retrofit.create(ApiService.class);
        // Carga los datos del inventario desde la API
        cargarInventario();
    }
    // Método que hace la llamada a la API para obtener los productos
    private void cargarInventario() {
        apiService.getAllProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()) {
                    // Si la respuesta es exitosa, muestra la tabla con los productos
                    mostrarTabla(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                // Si falla la conexión, muestra un mensaje de error
                Toast.makeText(Inventario.this, "Error al cargar productos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Método que genera y muestra la tabla con los productos
    private void mostrarTabla(List<Producto> productos) {
        // Crea una fila para los encabezados de la tabla
        TableRow headerRow = new TableRow(this);
        String[] headers = {"ID", "Nombre", "Cantidad", "Mínima", "Estado"};
        for (String h : headers) {
            TextView tv = new TextView(this);
            tv.setText(h); // Texto del encabezado
            tv.setPadding(10, 10, 10, 10); // Espaciado
            tv.setTypeface(null, Typeface.BOLD); // Texto en negrita
            headerRow.addView(tv); // Añade el encabezado a la fila
        }
        tablaProductos.addView(headerRow); // Añade la fila de encabezados a la tabla

        // Filas
        for (Producto p : productos) {
            TableRow row = new TableRow(this);
            // Añade las celdas con los datos del producto
            row.addView(crearCelda(String.valueOf(p.getId())));
            row.addView(crearCelda(p.getNombre()));
            row.addView(crearCelda(String.valueOf(p.getCantidad())));
            row.addView(crearCelda(String.valueOf(p.getCantidadMinima())));
            row.addView(crearCelda(p.getEstado() != null ? p.getEstado() : "-"));
            // Añade la fila a la tabla
            tablaProductos.addView(row);
        }
    }
    // Método auxiliar para crear una celda (TextView) con formato
    private TextView crearCelda(String texto) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setPadding(10, 10, 10, 10);
        return tv;
    }
}
