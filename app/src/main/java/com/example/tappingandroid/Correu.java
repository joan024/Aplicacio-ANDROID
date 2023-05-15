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

        //Obtenim els elements de la vista XML
        correu = findViewById(R.id.correu);
        correu.setEnabled(false);
        titol = findViewById(R.id.titol);
        misatge = findViewById(R.id.missatge);
        btnMensaje = findViewById(R.id.btn_enviar);

        // Associem el botó de l'activitat amb una acció al ser clicat.
        btnMensaje.setOnClickListener(view -> {
            enviar();
        });
    }

    //Mètode que envia el correu electrònic mitjançant l'aplicació de correu per defecte del dispositiu.
    public void enviar (){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));

        //Afegim els continguts del correu.
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{correu.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT, titol.getText().toString());
        intent.putExtra(Intent.EXTRA_TEXT, misatge.getText().toString());

        //Iniciem l'activitat d'enviament de correu.
        startActivity(intent);
    }
}