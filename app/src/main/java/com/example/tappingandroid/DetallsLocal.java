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

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetallsLocal extends AppCompatActivity {

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

        ivTornar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Rebem les dades del local
        Intent intent = getIntent();
        Local local = (Local) intent.getSerializableExtra("local");

        tvNomLocal.setText(local.getNom());
        ivImatge.setImageResource(local.getFoto());
        tvUbicacio.setText(local.getUbicacio());
        tvTelefon.setText(local.getTelefon());
        tvHorari.setText(local.getHorari());
        tvPuntuacio.setText(local.getPuntuacio() + "");
        tvDescripcio.setText(local.getDescripcio());

        // Establecer el diseño del RecyclerView
        recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOpinions.setLayoutManager(new LinearLayoutManager(this));

        // Establecer el adaptador del RecyclerView
        recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));
        recyclerViewOpinions.setAdapter((new OpinioAdapter(local.getOpinions())));

        btn_comentari.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallsLocal.this,AfegirOpinio.class);
                intent.putExtra("local", local);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
