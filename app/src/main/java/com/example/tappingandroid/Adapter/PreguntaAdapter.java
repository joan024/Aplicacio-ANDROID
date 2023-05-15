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

    // Interfície per a gestionar els esdeveniments de clic en la llista de preguntes
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private List<Pregunta> preguntas;
    private PreguntaAdapter.OnItemClickListener onItemClickListener;

    // Constructor de l'adaptador
    public PreguntaAdapter(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    //Defineix el listener per a esdeveniments de clic
    public void setOnItemClickListener(PreguntaAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //Retorna el listener per a esdeveniments de clic
    public PreguntaAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    //Crea una nova vista per a la llista de preguntes
    @NonNull
    @Override
    public PreguntaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pregunta_item, parent, false);

        return new ViewHolder(itemView);
    }

    //Actualitza la vista amb les dades de la pregunta
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

    //Retorna la quantitat d'elements a la llista
    @Override
    public int getItemCount() {return preguntas.size();}

    //Classe que defineix la vista de cada element a la llista de preguntes
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView PreguntaTextView;
        private TextView RespostaTextView;

        //Constructor que inicialitza les vistes
        public ViewHolder(View itemView) {
            super(itemView);

            PreguntaTextView = itemView.findViewById(R.id.tv_pregunta);
            RespostaTextView = itemView.findViewById(R.id.tv_resposta);
        }
    }
}
