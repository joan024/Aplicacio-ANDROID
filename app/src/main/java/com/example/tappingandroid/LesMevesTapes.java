package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tappingandroid.Adapter.TapasAdapter;
import com.example.tappingandroid.Dades.Local;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LesMevesTapes extends AppCompatActivity {

    @BindView(R.id.recyclerview_tapes) RecyclerView recyclerViewTapes;
    @BindView(R.id.iv_tornar) ImageView ivTornar;
    int id;
    List<Local> locals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_meves_tapes);
        ButterKnife.bind(this);

        id = Utilitats.agafarIdShared(this);

        ivTornar.setOnClickListener(v -> onBackPressed());

        try {
            locals = Utilitats.getLocals(locals, id, this); // funció que obte els locals d'un restaurant
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }

        for(int i=0; i<locals.size(); i++){
            Local local = locals.get(i); // Aquí obtenemos la instancia de Local en la posición i
            // Establir el disseny del RecyclerView
            recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));
            // Establir l'adaptador del RecyclerView
            recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));
        }
    }
}