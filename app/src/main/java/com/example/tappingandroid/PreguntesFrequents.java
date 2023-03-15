package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PreguntesFrequents extends AppCompatActivity {

    private TextView pregunta;
    private TextView resposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntes_frequents);

    }
}