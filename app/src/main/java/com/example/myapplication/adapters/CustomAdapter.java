package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

private final List<Perfil> perfiles;

public CustomAdapter(List<Perfil> potatoes){
    this.perfiles = potatoes;
}

public static class CustomViewHolder extends RecyclerView.ViewHolder {

    public final View myView;


    private final TextView textName;


    CustomViewHolder(View itemView) {
        super(itemView);
        myView = itemView;


        textName = myView.findViewById(R.id.textViewName);

    }
}

@NonNull
@Override
public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.list_line, parent, false);
    return new CustomViewHolder(view);
}

@Override
public void onBindViewHolder(CustomViewHolder holder, int position) {

    holder.textName.setText(perfiles.get(position).getNombre());

}

@Override
public int getItemCount() {
    return perfiles.size();
}
}
