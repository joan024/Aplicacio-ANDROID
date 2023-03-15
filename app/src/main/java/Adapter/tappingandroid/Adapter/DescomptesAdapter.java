package Adapter.tappingandroid.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import Adapter.tappingandroid.Dades.Descompte;

import com.example.tappingandroid.R;

import java.text.SimpleDateFormat;
import java.util.List;

public abstract class DescomptesAdapter extends RecyclerView.Adapter<DescomptesAdapter.ViewHolder> {

    private List<Descompte> descomptes;

    public DescomptesAdapter(List<Descompte> descomptes) {
        this.descomptes = descomptes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.descompte_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Descompte descompte = descomptes.get(position);
        holder.tvCodi.setText(descompte.getCodi());
        holder.tvDescripcio.setText(descompte.getDescripcio());

        // Creamos un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // Convertimos el objeto Date a un String con el formato deseado
        String dataInici = dateFormat.format(descompte.getDataInici());
        String dataCaducitat = dateFormat.format(descompte.getDataCaducitat());

        // Asignamos el String al TextView
        holder.tvDataInici.setText(dataInici);
        holder.tvDataCaducitat.setText(dataCaducitat);

        holder.tvLocal.setText(descompte.getLocal()+"");
    }

    @Override
    public int getItemCount() {
        return descomptes.size();
    }

    public abstract void onDescompteClick(Descompte descompte);

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCodi;
        private TextView tvDescripcio;
        private TextView tvDataCaducitat;
        private TextView tvLocal;
        private TextView tvDataInici;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCodi = itemView.findViewById(R.id.tv_codi);
            tvDescripcio = itemView.findViewById(R.id.tv_descripcio);
            tvDataCaducitat = itemView.findViewById(R.id.tv_data_caducitat);
            tvDataInici = itemView.findViewById(R.id.tv_data_inici);
            tvLocal = itemView.findViewById(R.id.tv_local_nom);
        }
    }

}
