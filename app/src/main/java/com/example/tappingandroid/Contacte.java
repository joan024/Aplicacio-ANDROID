package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class Contacte extends AppCompatActivity {

    Button mensaje;
    ImageView ivTornar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacte);

        mensaje= findViewById(R.id.btn_mensaje);
        ivTornar = findViewById(R.id.iv_tornar);
        ivTornar.setOnClickListener(v -> onBackPressed());

        mensaje.setOnClickListener(v -> {
            Intent intent = new Intent(this, Correu.class);
            intent.putExtra("usuari", mensaje.getText().toString());
            startActivity(intent);
        });

    }
}
