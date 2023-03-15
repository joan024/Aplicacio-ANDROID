package Adapter.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.List;

import Adapter.tappingandroid.Dades.Tapa;

public class TapasAdapter extends RecyclerView.Adapter<TapasAdapter.ViewHolder> {

    private List<Tapa> tapas;

    public TapasAdapter(List<Tapa> mTapas) {
        tapas = mTapas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tapa_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tapa tapa = tapas.get(position);
        holder.bind(tapa);
    }

    @Override
    public int getItemCount() {
        return tapas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomView;
        private TextView preuView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomView = itemView.findViewById(R.id.tv_nom);
            preuView = itemView.findViewById(R.id.tv_preu);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Tapa tapa) {
            nomView.setText(tapa.getNom());
            preuView.setText(tapa.getPreu()+"€");
        }
    }
}

