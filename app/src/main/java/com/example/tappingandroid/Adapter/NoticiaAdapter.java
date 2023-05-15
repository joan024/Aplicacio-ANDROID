package com.example.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.List;

import com.example.tappingandroid.Dades.Noticia;
import com.squareup.picasso.Picasso;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<Noticia> noticias;
    private NoticiaAdapter.OnItemClickListener onItemClickListener;

    // Constructor de la classe, rep una llista de notícies
    public NoticiaAdapter(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    // Mètode per a definir el listener d'events de clic
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Mètode per obtenir el listener d'events de clic
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    // Sobrescriu el mètode onCreateViewHolder per a inflar la vista de cada notícia a partir del seu disseny (R.layout.noticia_item)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticia_item, parent, false);

        return new ViewHolder(itemView);
    }

    // Sobrescriu el mètode onBindViewHolder per a vincular les dades de les notícies a la vista corresponent
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Noticia noticia = noticias.get(position);

        Picasso.get().load(noticia.getImagen()).into(holder.imagenImageView);
        holder.tituloTextView.setText(noticia.getTitol());
        holder.descripcionTextView.setText(noticia.getDescripcio());
        holder.data_publicacio.setText(noticia.getData_publicacio());

        // Defineix un listener de clic per a la vista de cada notícia
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                // Si el listener no és nul, executa el mètode onItemClick i li passa la posició de la notícia que ha estat clicada
                onItemClickListener.onItemClick(position);
            }
        });
    }

    // Sobrescriu el mètode getItemCount per a retornar el número total de notícies
    @Override
    public int getItemCount() {
        return noticias.size();
    }

    // Classe ViewHolder que conté la vista de cada notícia
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloTextView;
        private TextView descripcionTextView;
        private TextView data_publicacio;
        private ImageView imagenImageView;

        // Constructor de la classe ViewHolder, rep la vista de cada notícia
        public ViewHolder(View itemView) {
            super(itemView);

            imagenImageView = itemView.findViewById(R.id.iv_noticia_foto);
            tituloTextView = itemView.findViewById(R.id.tv_titol_noticia);
            descripcionTextView = itemView.findViewById(R.id.tv_descripcio);
            data_publicacio = itemView.findViewById(R.id.tv_data_publicacio);

        }
    }
}


