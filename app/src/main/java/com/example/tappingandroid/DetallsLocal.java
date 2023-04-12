package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Adapter.OpinioAdapter;
import com.example.tappingandroid.Adapter.TapasAdapter;
import com.example.tappingandroid.Dades.Local;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.tappingandroid.Dades.Tapa;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetallsLocal extends AppCompatActivity {

    //Definim les vistes que farem servir en aquesta classe
    @BindView(R.id.iv_tornar) ImageView ivTornar;
    @BindView(R.id.iv_imatge_local) ImageView ivImatge;
    @BindView(R.id.tv_local_nom) TextView tvNomLocal;
    @BindView(R.id.tv_ubicacio) TextView tvUbicacio;
    @BindView(R.id.tv_telefon) TextView tvTelefon;
    @BindView(R.id.tv_horari) TextView tvHorari;
    @BindView(R.id.tv_puntuacio) TextView tvPuntuacio;
    @BindView(R.id.tv_descripcio) TextView tvDescripcio;
    @BindView(R.id.recyclerview_tapes) RecyclerView recyclerViewTapes;
    @BindView(R.id.rv_opinions) RecyclerView recyclerViewOpinions;
    @BindView(R.id.btn_afegir_comentari) Button btn_comentari;

    @Override
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalls_local);
        ButterKnife.bind(this);

        //Acció en fer clic al botó "tornar"
        ivTornar.setOnClickListener(v -> onBackPressed());

        //Rebem les dades del local
        Intent intent = getIntent();
        Local local = (Local) intent.getSerializableExtra("local");

        //Assignem les dades del local a les vistes corresponents
        tvNomLocal.setText(local.getNom());
        Picasso.get().load(local.getFoto()).into(ivImatge);
        tvUbicacio.setText(local.getUbicacio());
        tvTelefon.setText(local.getTelefon());
        tvHorari.setText(local.getHorari());
        DecimalFormat df = new DecimalFormat("#.#");
        tvPuntuacio.setText(df.format(local.getPuntuacio()));
        tvDescripcio.setText(local.getDescripcio());

        //Establim el disseny del RecyclerView per a les tapes i les opinions
        recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOpinions.setLayoutManager(new LinearLayoutManager(this));

        //Establim l'adaptador per als RecyclerView de tapes i opinions
        recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));
        recyclerViewOpinions.setAdapter((new OpinioAdapter(local.getOpinions())));

        //Acció en fer clic al botó "afegir comentari"
        btn_comentari.setOnClickListener(v -> {
            Intent intent1 = new Intent(DetallsLocal.this,AfegirOpinio.class);
            intent1.putExtra("local", local);
            startActivity(intent1);
        });
    }

    //Mètode per tornar enrere
    public void onBackPressed() {
        super.onBackPressed();
    }

}