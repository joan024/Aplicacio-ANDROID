package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Resultats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        Intent intent = getIntent();
        String query = intent.getStringExtra("filtre");

        // Aquí puedes hacer lo que quieras con la consulta, por ejemplo mostrarla en un TextView
        //TextView textView = findViewById(R.id.tv_resultatProva);
        //textView.setText(query);
    }
}