package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.List;

import Dades.Noticia;
import Dades.NoticiaViewHolder;

public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaViewHolder> {
    private List<Noticia> noticias;

    public NoticiaAdapter(List<Noticia> noticias) {
        this.noticias = noticias;
    }

    @NonNull
    @Override
    public NoticiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_noticia, parent, false);

        return new NoticiaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiaViewHolder holder, int position) {
        Noticia noticia = noticias.get(position);
        holder.bind(noticia);
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }
}

