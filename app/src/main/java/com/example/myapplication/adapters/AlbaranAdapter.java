package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.myapplication.Modelo.Albaran;
import com.example.myapplication.R;

import java.util.List;

public class AlbaranAdapter extends ArrayAdapter<Albaran> {

    private Context context;
    private List<Albaran> albaranes;

    public AlbaranAdapter(List<Albaran> albaranes, Context context) {
        super(context, 0, albaranes);
        this.albaranes = albaranes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_albaran, parent, false);
        }

        Albaran albaran = albaranes.get(position);

        // Obtener las vistas dentro del layout del item
        ImageView fotoAlbaran = convertView.findViewById(R.id.fotoAlbaran);
        TextView nombreTextView = convertView.findViewById(R.id.nombreAlbaran);
        TextView precioTextView = convertView.findViewById(R.id.precioAlbaran);
        TextView cifTextView = convertView.findViewById(R.id.cifAlbaran);
        TextView cantidadTextView = convertView.findViewById(R.id.cantidadAlbaran);
        TextView estadoTextView = convertView.findViewById(R.id.estadoAlbaran);

        // Imagen
        String base64Foto = albaran.getFoto();
        if (base64Foto != null && !base64Foto.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Foto, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            fotoAlbaran.setImageBitmap(decodedBitmap);
        } else {
            fotoAlbaran.setImageResource(R.drawable.default_profile);
        }

        // Establecer los valores correspondientes para cada albarán
        nombreTextView.setText(albaran.getNombre());
        precioTextView.setText("Precio: " + albaran.getPrecio());
        cifTextView.setText("CIF: " + albaran.getCIF());
        cantidadTextView.setText("Cantidad: " + albaran.getCantidad());
        estadoTextView.setText("Estado: " + albaran.getEstado());

        return convertView;
    }
}
