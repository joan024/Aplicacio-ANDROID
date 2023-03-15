package com.example.tappingandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Dades.Noticia;
import com.example.tappingandroid.R;

import java.util.ArrayList;
import java.util.List;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private List<Noticia> noticias;
    private NoticiaAdapter.OnItemClickListener onItemClickListener;

    public NoticiaAdapter(ArrayList<Noticia> noticies) {
        this.noticias = noticies;
    }

    public void setOnItemClickListener(NoticiaAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public NoticiaAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }
    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticia_item, parent, false);

        return new NoticiaViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {
        Noticia noticia = noticias.get(position);
        holder.imagenImageView.setImageResource(noticia.getImagen());
        holder.tituloTextView.setText(noticia.getTitol());
        holder.descripcionTextView.setText(noticia.getDescripcio());
        holder.data_publicacio.setText(noticia.getData_publicacio());

        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

    }
    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public static class NoticiaViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloTextView;
        private TextView descripcionTextView;
        private TextView data_publicacio;
        private ImageView imagenImageView;

        public NoticiaViewHolder(View itemView) {
            super(itemView);

            imagenImageView = itemView.findViewById(R.id.iv_noticia_foto);
            tituloTextView = itemView.findViewById(R.id.tv_titol);
            descripcionTextView = itemView.findViewById(R.id.tv_descripcio);
            data_publicacio = itemView.findViewById(R.id.tv_data_publicacio);

        }

        public void bind(Noticia noticia) {
            imagenImageView.setImageResource(noticia.getImagen());
            tituloTextView.setText(noticia.getTitol());
            descripcionTextView.setText(noticia.getDescripcio());
            data_publicacio.setText(noticia.getData_publicacio());
        }
    }

}

