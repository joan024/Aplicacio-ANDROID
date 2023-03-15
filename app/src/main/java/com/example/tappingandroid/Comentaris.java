package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.tappingandroid.Adapter.OpinioAdapter;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.example.tappingandroid.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Comentaris extends AppCompatActivity {

    @BindView(R.id.iv_tornar) ImageView ivTornar;
    @BindView(R.id.rv_opinions) RecyclerView recyclerViewOpinions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentaris);
        ButterKnife.bind(this);

        //List<Local> locals = new ArrayList<Local>();
        ivTornar.setOnClickListener(v -> onBackPressed());

        // Crear objectes de dades per al local i les seves opinions
        Local local = null;
        List<Tapa> tapes = new ArrayList<Tapa>();
        tapes.add(new Tapa("Braves",6.4));
        List <Opinio> opinions =  new ArrayList<Opinio>();
        try {
            opinions.add(new Opinio("Juan","12/02/2022","Aquest local es top.",7.8));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        local = new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions);


        recyclerViewOpinions.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOpinions.setAdapter((new OpinioAdapter(local.getOpinions())));

    }
}