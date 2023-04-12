package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Correu extends AppCompatActivity {
    Button btnMensaje;
    EditText correu, titol, misatge;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correu);
        correu = findViewById(R.id.correu);
        correu.setEnabled(false);
        titol = findViewById(R.id.titol);
        misatge = findViewById(R.id.missatge);

        btnMensaje = findViewById(R.id.btn_enviar);

        btnMensaje.setOnClickListener(view -> {
            enviar();
        });
    }

    public void enviar (){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{correu.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, titol.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, misatge.getText().toString());

        startActivity(intent);
    }
}