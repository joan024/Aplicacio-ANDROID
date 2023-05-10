package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;


import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ElsMeusLocals extends AppCompatActivity {

    private List<Local> locals;

    private ImageView iv_tornar;
    private Statement stmt = null;
    private ResultSet rs = null;
    private int id, id_local,id_horari;
    private String nom_local,direccio_local,telefon_local, link_foto,horari,descripcio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_els_meus_locals);

        iv_tornar = findViewById(R.id.iv_tornar);
        iv_tornar.setOnClickListener(v -> onBackPressed());

        id = Utilitats.agafarIdShared(this);

        RecyclerView recyclerView = findViewById(R.id.rv_locals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            locals = Utilitats.getLocals(locals, id, this); // funció que obte els locals d'un restaurant
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }

        LocalAdapter adaptador = new LocalAdapter((ArrayList<Local>) locals); // adaptador para mostrar els locals d'un restaurant

        adaptador.setOnItemClickListener(position -> {
            // Obtenir l'objecte local a la posició seleccionada
            Local localSeleccionado = locals.get(position);

            // Crear un Intent per obrir l'activitat LocalDetail i passar la informació del local seleccionat
            Intent intent = new Intent(ElsMeusLocals.this, DetallsLocal.class);
            intent.putExtra("local", localSeleccionado);
            startActivity(intent);
        });

        recyclerView.setAdapter(adaptador);
    }


}