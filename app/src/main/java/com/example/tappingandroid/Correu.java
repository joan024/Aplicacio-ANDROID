package com.example.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Correu extends AppCompatActivity {
    Button btnMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correu);

        btnMensaje = findViewById(R.id.btn_enviar);

        btnMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistreActivity();
            }
        });

    }
    private void startRegistreActivity() {
        Intent intent = new Intent(this, Inici.class);
        intent.putExtra("usuari", btnMensaje.getText().toString());
        startActivity(intent);
    }
}

