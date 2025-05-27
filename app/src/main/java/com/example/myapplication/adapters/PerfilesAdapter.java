package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.R;
import com.example.myapplication.clases.ListadoProductos;

import java.util.List;

public class PerfilesAdapter extends BaseAdapter {

    List<Perfil> perfiles;
    Context context;

    public PerfilesAdapter(List<Perfil> perfiles, Context context) {
        this.perfiles = perfiles;
        this.context = context;
    }

    @Override
    public int getCount() {
        return perfiles.size();
    }

    @Override
    public Object getItem(int i) {
        return perfiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return perfiles.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.perfil_list, viewGroup, false);
        }

        ImageView profileImage = view.findViewById(R.id.profileImage);
        TextView textViewNombre = view.findViewById(R.id.textViewNombrePerfil);

        Perfil perfil = perfiles.get(position);

        // Mostrar la imagen del perfil
        String base64Foto = perfil.getFoto();
        if (base64Foto != null && !base64Foto.isEmpty()) {
            byte[] decodedBytes = Base64.decode(base64Foto, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            profileImage.setImageBitmap(decodedBitmap);
        } else {
            profileImage.setImageResource(R.drawable.default_profile);
        }

        // Mostrar el nombre del perfil
        textViewNombre.setText(perfil.getNombre());

        // Navegar al hacer clic en la imagen
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListadoProductos.class);
            intent.putExtra("perfil_id", perfil.getId());
            intent.putExtra("perfil_nombre", perfil.getNombre());
            intent.putExtra("perfil_correo", perfil.getCorreo());

            if (context instanceof android.app.Activity) {
                context.startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

        return view;
    }
}
