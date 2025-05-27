package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.R;

import java.util.List;

public class ProductosAdapter extends ArrayAdapter<Producto> {
    private List<Producto> productos;
    private Context context;

    public ProductosAdapter(List<Producto> productos, Context context) {
        super(context, 0, productos);
        this.productos = productos;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto2, parent, false);
        }

        Producto producto = productos.get(position);

        ImageView fotoProducto = convertView.findViewById(R.id.fotoProducto);
        TextView nombreProducto = convertView.findViewById(R.id.nombreProducto);
        TextView cantidadProducto = convertView.findViewById(R.id.cantidadProducto);
        TextView avisoMinimo = convertView.findViewById(R.id.avisoMinimo); // Nuevo TextView

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
        } else {
            avisoMinimo.setVisibility(View.GONE);
        }

        return convertView;
    }
}
