package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.example.myapplication.api.ApiService;
import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
public class ProductoAdapter extends ArrayAdapter<Producto> {
    private List<Producto> productos;
    private Context context;
    private ApiService apiService;

    public ProductoAdapter(List<Producto> productos, Context context) {
        super(context, 0, productos);
        this.productos = productos;
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.ip_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        }

        Producto producto = productos.get(position);

        ImageView fotoProducto = convertView.findViewById(R.id.fotoProducto);
        TextView nombreProducto = convertView.findViewById(R.id.nombreProducto);
        TextView cantidadProducto = convertView.findViewById(R.id.cantidadProducto);
        TextView avisoMinimo = convertView.findViewById(R.id.avisoMinimo); // Nuevo TextView

        ImageButton btnSumar = convertView.findViewById(R.id.btnSumar);
        ImageButton btnRestar = convertView.findViewById(R.id.btnRestar);

        // Imagen
        String base64Foto = producto.getFoto();
        if (base64Foto != null && !base64Foto.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Foto, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            fotoProducto.setImageBitmap(decodedBitmap);
        } else {
            fotoProducto.setImageResource(R.drawable.default_profile);
        }

        // Datos básicos
        nombreProducto.setText(producto.getNombre());
        cantidadProducto.setText(String.valueOf(producto.getCantidad()));

        // Mostrar u ocultar el aviso de cantidad mínima
        if (producto.getCantidad() <= producto.getCantidadMinima()) {
            avisoMinimo.setVisibility(View.VISIBLE);
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
        } else {
            avisoMinimo.setVisibility(View.GONE);
            convertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent)); // o blanco
        }


        // Botón Sumar
        View finalConvertView = convertView;
        btnSumar.setOnClickListener(v -> {
            int nuevaCantidad = producto.getCantidad() + 1;
            producto.setCantidad(nuevaCantidad);
            cantidadProducto.setText(String.valueOf(nuevaCantidad));

            // Mostrar/ocultar aviso
            if (nuevaCantidad <= producto.getCantidadMinima()) {
                avisoMinimo.setVisibility(View.VISIBLE);
                finalConvertView.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
            } else {
                avisoMinimo.setVisibility(View.GONE);
                finalConvertView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
            }

            actualizarProductoEnBaseDeDatos(producto);
        });

        // Botón Restar
        View finalConvertView1 = convertView;
        btnRestar.setOnClickListener(v -> {
            int nuevaCantidad = producto.getCantidad() - 1;
            if (nuevaCantidad >= 0) {
                producto.setCantidad(nuevaCantidad);
                cantidadProducto.setText(String.valueOf(nuevaCantidad));

                // Mostrar/ocultar aviso
                if (nuevaCantidad <= producto.getCantidadMinima()) {
                    avisoMinimo.setVisibility(View.VISIBLE);
                    finalConvertView1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
                } else {
                    avisoMinimo.setVisibility(View.GONE);
                    finalConvertView1.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                }

                actualizarProductoEnBaseDeDatos(producto);
            }
        });

        return convertView;
    }

    private void actualizarProductoEnBaseDeDatos(Producto producto) {
        Call<Producto> call = apiService.actualizarProducto(producto.getId(), producto);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Log.d("ProductoAdapter", "Producto actualizado correctamente");
                } else {
                    Log.e("ProductoAdapter", "Error al actualizar el producto: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ProductoAdapter", "Error al realizar la solicitud de actualización: " + t.getMessage());
            }
        });
    }
}