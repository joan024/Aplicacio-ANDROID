package Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.ArrayList;

import Dades.Local;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.ViewHolder> {

    private ArrayList<Local> locales;

    public LocalAdapter(ArrayList<Local> locales) {
        this.locales = locales;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.local_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Local local = locales.get(position);
        holder.ivFoto.setImageResource(local.getFoto());
        holder.tvNom.setText(local.getNom());
        holder.tvUbicacio.setText(local.getUbicacio());
        holder.tvHorari.setText(local.getHorari());
        holder.tvPuntuacio.setText(String.format("%.1f", local.getPuntuacio()));
    }

    @Override
    public int getItemCount() {
        return locales.size();
    }

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

