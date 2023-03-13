package Dades;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

public class NoticiaViewHolder extends RecyclerView.ViewHolder {
    private TextView tituloTextView;
    private TextView descripcionTextView;
    private ImageView imagenImageView;

    public NoticiaViewHolder(View itemView) {
        super(itemView);

        tituloTextView = itemView.findViewById(R.id.noticiaNom);
        descripcionTextView = itemView.findViewById(R.id.Descripcio);
        imagenImageView = itemView.findViewById(R.id.imgNoticia);
    }

    public void bind(Noticia noticia) {
        tituloTextView.setText(noticia.getTitulo());
        descripcionTextView.setText(noticia.getDescripcion());
        imagenImageView.setImageResource(noticia.getImagenId());
    }
}