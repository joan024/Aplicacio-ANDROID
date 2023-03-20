package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.ArrayList;
import java.util.List;

import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Adapter.NoticiaAdapter;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Noticia;

public class Noticies extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticiaAdapter adaptador;
    private ArrayList<Noticia> noticias;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticies);

        recyclerView = findViewById(R.id.rv_noticia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticias = llenarNoticia();
        adaptador = new NoticiaAdapter((ArrayList<Noticia>) noticias);

        adaptador.setOnItemClickListener(position -> {
            //Noticia noticies = noticias.get(position);

            Intent intent = new Intent(this, DetallsNoticia.class);
           // intent.putExtra("valor", (CharSequence) noticies);
            startActivity(intent);

        });
        recyclerView.setAdapter(adaptador);
    }

    public ArrayList<Noticia> llenarNoticia(){
        noticias = new ArrayList<>();
        noticias.add(new Noticia(R.drawable.logotiptapping,"Apertura","Se inagura Tapping","21/03/2023","Fecha Indefinida","23/03/2023", noticias));
        noticias.add(new Noticia(R.drawable.logotiptapping,"Noticia 2", "Descomptes","23/03/2021","26/04/2023","22/03/2023",noticias));
        return noticias;
    }
}
