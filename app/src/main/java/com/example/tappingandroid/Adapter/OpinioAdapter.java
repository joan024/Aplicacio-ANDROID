package com.example.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class OpinioAdapter extends RecyclerView.Adapter<OpinioAdapter.OpinioViewHolder> {
    private List<Opinio> opinions;

    public OpinioAdapter(List<Opinio> opinions) {
        this.opinions = opinions;
    }

    @NonNull
    @Override
    public OpinioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opinio_item, parent, false);
        return new OpinioViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OpinioViewHolder holder, int position) {
        Opinio opinio = opinions.get(position);
        holder.nomUsuari.setText(opinio.getNomUsuari());
        // Creamos un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Convertimos el objeto Date a un String con el formato deseado
        String data = dateFormat.format(opinio.getData());

        // Asignamos el String al TextView
        holder.data.setText(data);
        holder.comentari.setText(opinio.getComentari());
        holder.puntuacio.setText(opinio.getPuntuacio()+"");
    }

    @Override
    public int getItemCount() {
        return opinions.size();
    }

    public static class OpinioViewHolder extends RecyclerView.ViewHolder {
        public TextView nomUsuari;
        public TextView data;
        public TextView comentari;
        public TextView puntuacio;

        public OpinioViewHolder(View itemView) {
            super(itemView);
            nomUsuari = itemView.findViewById(R.id.tv_nom_usuari);
            data = itemView.findViewById(R.id.tv_data);
            comentari = itemView.findViewById(R.id.tv_comentari);
            puntuacio = itemView.findViewById(R.id.tv_puntuacio);
        }
    }
}

