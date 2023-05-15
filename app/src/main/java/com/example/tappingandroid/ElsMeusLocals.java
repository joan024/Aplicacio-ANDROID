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
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_els_meus_locals);

        // Defineix una acció quan es clica sobre la imatge de tornaR
        iv_tornar = findViewById(R.id.iv_tornar);
        iv_tornar.setOnClickListener(v -> onBackPressed());

        // Agafa l'identificador del local a través de les Shared Preferences de l'aplicació
        id = Utilitats.agafarIdShared(this);

        RecyclerView recyclerView = findViewById(R.id.rv_locals); // Busca la vista del RecyclerView on es mostraran els locals
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Defineix el LinearLayoutManager per al RecyclerView

        try {
            locals = Utilitats.getLocals(locals, id, this); // funció que obte els locals d'un restaurant
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }

        // Crea un adaptador per a la llista de locals
        LocalAdapter adaptador = new LocalAdapter((ArrayList<Local>) locals);

        adaptador.setOnItemClickListener(position -> {
            // Obtenir l'objecte local a la posició seleccionada
            Local localSeleccionado = locals.get(position);

            // Crear un Intent per obrir l'activitat LocalDetail i passar la informació del local seleccionat
            Intent intent = new Intent(ElsMeusLocals.this, DetallsLocal.class);
            intent.putExtra("local", localSeleccionado);
            startActivity(intent); // Inicia l'activitat LocalDetail amb l'objecte local seleccionat
        });

        // Estableix l'adaptador per al RecyclerView
        recyclerView.setAdapter(adaptador);
    }
}