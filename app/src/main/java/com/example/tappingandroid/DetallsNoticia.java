package com.example.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.ArrayList;

import com.example.tappingandroid.Adapter.NoticiaAdapter;
import com.example.tappingandroid.Dades.Noticia;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetallsNoticia extends AppCompatActivity {
    @BindView(R.id.iv_noticia_foto) ImageView ivImatge;
    @BindView(R.id.tv_titol_noticia) TextView tvTitol;
    @BindView(R.id.tv_descripcio_noticia) TextView tvDescripcio;
    @BindView(R.id.tv_data_publicacio) TextView tvDataPublicacio;
    @BindView(R.id.iv_tornar) ImageView ivTornar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        ButterKnife.bind(this);

        // Configuració de l'acció en fer clic al botó "tornar".
        ivTornar.setOnClickListener(v -> onBackPressed());

        // S'obté la notícia que es vol mostrar.
        Intent intent = getIntent();
        Noticia noticia = (Noticia) intent.getSerializableExtra("noticia");

        // S'obté la notícia que es vol mostrar.
        Picasso.get().load(noticia.getImagen()).into(ivImatge);
        tvTitol.setText(noticia.getTitol());
        tvDataPublicacio.setText(noticia.getData_publicacio());
        tvDescripcio.setText(noticia.getDescripcio());
    }
}