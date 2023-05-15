package com.example.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.List;

import com.example.tappingandroid.Dades.Tapa;

public class TapasAdapter extends RecyclerView.Adapter<TapasAdapter.ViewHolder> {

    private List<Tapa> tapas;

    // Constructor de l'adaptador que rep una llista de tapes com a paràmetre
    public TapasAdapter(List<Tapa> mTapas) {
        tapas = mTapas;
    }

    // Crea una nova vista per a les tapes
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el disseny dels ítems de la llista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tapa_item, parent, false);
        return new ViewHolder(view);
    }

    // Omple les dades de les tapes en una vista
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tapa tapa = tapas.get(position);
        holder.bind(tapa);
    }

    // Retorna el nombre d'elements de la llista
    @Override
    public int getItemCount() {
        return tapas.size();
    }

    // Classe interna per a la gestió de les vistes dels ítems de la llista
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomView;
        private TextView preuView;

        // Constructor de la vista per a les tapes
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomView = itemView.findViewById(R.id.tv_nom);
            preuView = itemView.findViewById(R.id.tv_preu);
        }

        // Omple la vista amb les dades de la tapa
        @SuppressLint("SetTextI18n")
        public void bind(Tapa tapa) {
            nomView.setText(tapa.getNom());
            preuView.setText(tapa.getPreu()+"€");
            if(tapa.getPersonalitzacio()!=null){
                nomView.setText(tapa.getNom()+" "+tapa.getPersonalitzacio());
            }

        }
    }
}

