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
        //Acció en fer clic al botó "tornar"
        ivTornar.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        Noticia noticia = (Noticia) intent.getSerializableExtra("noticia");

        tvTitol.setText(noticia.getTitol());
        tvDataPublicacio.setText(noticia.getData_publicacio());
        tvDescripcio.setText(noticia.getDescripcio());
        //ivImatge.setImageResource();


    }
}


