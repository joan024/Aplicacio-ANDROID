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

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<Noticia> noticias;
    private NoticiaAdapter.OnItemClickListener onItemClickListener;

    public NoticiaAdapter(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticia_item, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Noticia noticia = noticias.get(position);
        //holder.imagenImageView.setImageResource(noticia.getImagen());
        holder.tituloTextView.setText(noticia.getTitol());
        holder.descripcionTextView.setText(noticia.getDescripcio());
        holder.data_publicacio.setText(noticia.getData_publicacio());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tituloTextView;
        private TextView descripcionTextView;
        private TextView data_publicacio;
        private ImageView imagenImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imagenImageView = itemView.findViewById(R.id.iv_noticia_foto);
            tituloTextView = itemView.findViewById(R.id.tv_titol_noticia);
            descripcionTextView = itemView.findViewById(R.id.tv_descripcio);
            data_publicacio = itemView.findViewById(R.id.tv_data_publicacio);

        }
    }
}


