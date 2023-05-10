package com.example.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Dades.Descompte;

import com.example.tappingandroid.R;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.List;

public class DescomptesAdapter extends RecyclerView.Adapter<DescomptesAdapter.ViewHolder> {

    // Definim la interfície per al listener que es cridarà quan es faci clic en un ítem
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    // Declarem les variables que utilitzarem
    private LocalAdapter.OnItemClickListener onItemClickListener;
    private List<Descompte> descomptes;

    // Constructor que rep una llista d'objectes Descompte
    public DescomptesAdapter(List<Descompte> descomptes) {
        this.descomptes = descomptes;
    }

    // Mètode que crea un ViewHolder per a cada ítem de la llista
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar la vista d'item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.descompte_item, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(LocalAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return (OnItemClickListener) onItemClickListener;
    }

    // Mètode que defineix el contingut de cada ítem de la llista
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Obtenir l'objecte descompte de la llista segons la posició
        Descompte descompte = descomptes.get(position);

        // Creem un objecte SimpleDateFormat amb el format desitjat
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Convertim l'objecte Date a un String amb el format desitjat
        String dataInici = dateFormat.format(descompte.getDataInici());
        String dataCaducitat = dateFormat.format(descompte.getDataCaducitat());

        // Establir els valors dels TextView de l'ítem segons els valors de l'objecte descompte
        holder.tvCodi.setText(descompte.getCodi());
        holder.tvDescripcio.setText(descompte.getDescripcio());
        holder.tvDataInici.setText(dataInici);
        holder.tvDataCaducitat.setText(dataCaducitat);
        holder.tvLocal.setText(descompte.getLocal());

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

    // Tornar la quantitat d'ítems a la llista
    @Override
    public int getItemCount() {
        return descomptes.size();
    }

    // Definir una classe ViewHolder per al RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCodi;
        private TextView tvDescripcio;
        private TextView tvDataCaducitat;
        private TextView tvLocal;
        private TextView tvDataInici;

        public ViewHolder(View itemView) {
            super(itemView);

            // Obtenir les referències dels TextView de l'item
            tvCodi = itemView.findViewById(R.id.tv_codi);
            tvDescripcio = itemView.findViewById(R.id.tv_descripcio);
            tvDataCaducitat = itemView.findViewById(R.id.tv_data_caducitat);
            tvDataInici = itemView.findViewById(R.id.tv_data_inici);
            tvLocal = itemView.findViewById(R.id.tv_local_nom);
        }
    }

}
