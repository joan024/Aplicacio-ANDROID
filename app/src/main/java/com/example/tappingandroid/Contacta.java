package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

public class Contacta extends AppCompatActivity {

    Button mensaje;
    ImageView ivTornar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacta); // Carregar el layout de l'activitat

        // Inicialitzar les variables amb els elements del layout corresponents
        mensaje= findViewById(R.id.btn_mensaje);
        ivTornar = findViewById(R.id.iv_tornar);

        // Afegir un listener per a la imatge d'enrere que torna a l'activitat anterior
        ivTornar.setOnClickListener(v -> onBackPressed());

        // Permetre clicar als enllaços en format text de les xarxes socials
        ((TextView) findViewById(R.id.contacte_insta)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.contacte_tiktok)).setMovementMethod(LinkMovementMethod.getInstance());

        // Afegir un listener per al botó d'enviar un missatge a través d'un intent
        mensaje.setOnClickListener(v -> {
            Intent intent = new Intent(this, Correu.class);
            intent.putExtra("usuari", mensaje.getText().toString());
            startActivity(intent); // Iniciar l'activitat Correu
        });
    }
}
