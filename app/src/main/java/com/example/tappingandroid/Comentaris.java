package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tappingandroid.Adapter.OpinioAdapter;
import com.example.tappingandroid.Adapter.TapasAdapter;
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
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Comentaris extends AppCompatActivity {

    @BindView(R.id.iv_tornar) ImageView ivTornar;
    @BindView(R.id.rv_opinions) RecyclerView recyclerViewOpinions;


    Connection conexio, conexio2;
    Statement stmt = null;
    ResultSet rs = null;
    int id=0;String comentari,data;
    double puntuacion;
    List<Local> locals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentaris);
        ButterKnife.bind(this);

        id = Utilitats.agafarIdShared(this);

        ivTornar.setOnClickListener(v -> onBackPressed());

        try {
            locals = Utilitats.getLocals(locals, id); // funció que obte els locals d'un restaurant
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }

        for(int i=0; i<locals.size(); i++){
            Local local = locals.get(i); // Aquí obtenemos la instancia de Local en la posición i
            // Establir el disseny del RecyclerView
            recyclerViewOpinions.setLayoutManager(new LinearLayoutManager(this));
            // Establir l'adaptador del RecyclerView
            recyclerViewOpinions.setAdapter((new OpinioAdapter(local.getOpinions())));
        }
    }
}