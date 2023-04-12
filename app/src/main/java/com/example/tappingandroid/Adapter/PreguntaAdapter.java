package com.example.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Dades.Noticia;
import com.example.tappingandroid.Dades.Pregunta;
import com.example.tappingandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreguntaAdapter extends RecyclerView.Adapter<PreguntaAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<Pregunta> preguntas;
    private PreguntaAdapter.OnItemClickListener onItemClickListener;

    public PreguntaAdapter(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public void setOnItemClickListener(PreguntaAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public PreguntaAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    @NonNull
    @Override
    public PreguntaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pregunta_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pregunta pregunta = preguntas.get(position);
        holder.PreguntaTextView.setText(pregunta.getPregunta());
        holder.RespostaTextView.setText(pregunta.getResposta());
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
    public int getItemCount() {return preguntas.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView PreguntaTextView;
        private TextView RespostaTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            PreguntaTextView = itemView.findViewById(R.id.tv_pregunta);
            RespostaTextView = itemView.findViewById(R.id.tv_resposta);
        }
    }
}
