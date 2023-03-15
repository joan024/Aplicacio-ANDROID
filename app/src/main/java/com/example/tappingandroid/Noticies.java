package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Adapter.NoticiaAdapter;
import com.example.tappingandroid.Dades.Noticia;

import java.util.ArrayList;

public class Noticies extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Noticia noticia;
    private ArrayList<Noticia> noticies;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticies);

        ImageView ivTornar = findViewById(R.id.iv_tornar);

        ivTornar.setOnClickListener(v -> onBackPressed());


        noticies = new ArrayList<>();
        noticia = new Noticia(R.drawable.logotiptapping,"Noticia1","Holaaaaaa","12/03/2023","19/05/2023","28/03/2023");
        noticies.add(noticia);

        recyclerView = findViewById(R.id.rv_noticia);
        recyclerView = new RecyclerView(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NoticiaAdapter adaptador = new NoticiaAdapter((ArrayList<Noticia>) noticies);
        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(position -> {
            Intent intent = new Intent(this, DetallsNoticia.class);
            intent.putExtra("valor", "noticia");
            startActivity(intent);
        });
    }

}

