package com.example.myapplication.adapters;

import android.content.Context;
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

import java.util.List;

public class PerfilAdapter extends BaseAdapter {
    List<Perfil> perfiles;
    Context context;

    public PerfilAdapter(List<Perfil> perfiles, Context context) {
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
            view = LayoutInflater.from(context).inflate(R.layout.perfiles_list, viewGroup, false);
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



        return view;
    }
}
