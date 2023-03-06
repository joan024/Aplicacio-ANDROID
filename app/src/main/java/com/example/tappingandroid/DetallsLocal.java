package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Adapter.TapasAdapter;
import Dades.Local;

public class DetallsLocal extends AppCompatActivity {

    @Override
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalls_local);

        ImageView iv_tornar = findViewById(R.id.iv_tornar);

        iv_tornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView iv_imatge = findViewById(R.id.iv_imatge_local);
        TextView tv_nom_local = findViewById(R.id.tv_local_nom);
        TextView tv_ubicacio = findViewById(R.id.tv_ubicacio);
        TextView tv_telefon = findViewById(R.id.tv_telefon);
        TextView tv_horari = findViewById(R.id.tv_horari);
        TextView tv_puntuacio = findViewById(R.id.tv_puntuacio);
        TextView tv_descripcio = findViewById(R.id.tv_descripcio);

        //Rebem les dades del local
        Intent intent = getIntent();
        Local local = (Local) intent.getSerializableExtra("local");

        tv_nom_local.setText(local.getNom());
        iv_imatge.setImageResource(local.getFoto());
        tv_ubicacio.setText(local.getUbicacio());
        tv_telefon.setText(local.getTelefon());
        tv_horari.setText(local.getHorari());
        tv_puntuacio.setText(local.getPuntuacio() + "");
        tv_descripcio.setText(local.getDescripcio());

        // Obtener una instancia del RecyclerView
        RecyclerView recyclerViewTapes = findViewById(R.id.recyclerview_tapes);

        // Establecer el diseño del RecyclerView
        recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));

        // Crear un adaptador para el RecyclerView
        TapasAdapter tapesAdapter = new TapasAdapter(local.getTapes());

        // Establecer el adaptador del RecyclerView
        recyclerViewTapes.setAdapter(tapesAdapter);

    }
}