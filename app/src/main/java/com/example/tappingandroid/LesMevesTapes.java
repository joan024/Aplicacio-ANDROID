package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tappingandroid.Adapter.TapasAdapter;
import com.example.tappingandroid.Dades.Local;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LesMevesTapes extends AppCompatActivity {

    @BindView(R.id.recyclerview_tapes) RecyclerView recyclerViewTapes;
    @BindView(R.id.iv_tornar) ImageView ivTornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_meves_tapes);
        ButterKnife.bind(this);

        ivTornar.setOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        Local local = (Local) intent.getSerializableExtra("local");

        // Establir el disseny del RecyclerView
        recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));
        // Establir l'adaptador del RecyclerView
        recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));


    }
}