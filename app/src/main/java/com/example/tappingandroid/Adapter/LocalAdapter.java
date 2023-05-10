package com.example.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Constants;
import com.example.tappingandroid.R;

import java.io.File;
import java.util.ArrayList;

import com.example.tappingandroid.Dades.Local;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    // Definició de la interfície OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(int position) throws WriterException;
    }
    // Llista de locals
    private ArrayList<Local> locales;
    // Listener per a les interaccions de clics en els ítems de la llista
    private OnItemClickListener onItemClickListener;
    public LocalAdapter(ArrayList<Local> locales) {
        this.locales = locales;
    }

    // Estableix el listener per a les interaccions de clics en els ítems de la llista
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Obté el listener per a les interaccions de clics en els ítems de la llista
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }


    // Crea una nova vista per a cada ítem de la llista
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.local_item, parent, false);
        return new ViewHolder(view);
    }

    //Assigna els valors de les dades del local als elements de la vista al ViewHolder
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Obtenir l'objecte local de la llista segons la posició
        Local local = locales.get(position);

        //Gestiones les imatges amb picasso
        Log.d("juliaaaaaaa","FOTO LOCAL:"+local.getFoto().get(0));
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download" +Constants.rutaArxiusLocal+ local.getId() +"/" + local.getFoto().get(0);

        Picasso.get().load(new File(path)).into(holder.ivFoto);


        // Establir els valors dels TextView de l'ítem segons els valors de l'objecte local
        holder.tvNom.setText(local.getNom());
        holder.tvUbicacio.setText(local.getUbicacio());
        holder.tvHorari.setText(local.getHorari());
        holder.tvPuntuacio.setText(String.format("%.1f", local.getPuntuacio()));

        // Afegim un listener al clicar en l'ítem
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                try {
                    onItemClickListener.onItemClick(position);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Obté el nombre d'elements a la llista de locals
    @Override
    public int getItemCount() {
        return locales.size();
    }

    // Classe estatica ViewHolder que representa cada ítem a la llista de RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNom;
        TextView tvUbicacio;
        TextView tvHorari;
        TextView tvPuntuacio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.iv_local_foto);
            tvNom = itemView.findViewById(R.id.tv_local_nom);
            tvUbicacio = itemView.findViewById(R.id.tv_local_ubicacio);
            tvHorari = itemView.findViewById(R.id.tv_local_horari);
            tvPuntuacio = itemView.findViewById(R.id.tv_puntuacio);
        }
    }
}