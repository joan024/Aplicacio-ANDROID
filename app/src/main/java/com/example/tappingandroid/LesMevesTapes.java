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
        setContentView(R.layout.activity_les_meves_tapes); // Establir la vista de l'activitat.
        ButterKnife.bind(this); // Inicialitzar Butter Knife.

        // Obtenir l'identificador del restaurant des de les preferències compartides.
        id = Utilitats.agafarIdShared(this);

        // Configurar el botó de tornar per a quan es premi.
        ivTornar.setOnClickListener(v -> onBackPressed());

        try {
            locals = Utilitats.getLocals(locals, id, this); // Obtenir la llista de locals del restaurant a través de la classe Utilitats.
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }

        for(int i=0; i<locals.size(); i++){
            Local local = locals.get(i); // Obtenir la instància de Local en la posició i.
            // Establir el disseny del RecyclerView
            recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));
            // Establir l'adaptador del RecyclerView amb les tapes del local
            recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));
        }
    }
}