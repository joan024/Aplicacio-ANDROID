package com.example.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Contacta extends AppCompatActivity {

    Button mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacta);

        mensaje= findViewById(R.id.btn_mensaje);

        ((TextView) findViewById(R.id.contacte_insta)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.contacte_tiktok)).setMovementMethod(LinkMovementMethod.getInstance());

        mensaje.setOnClickListener(v -> {
            Intent intent = new Intent(this, Correu.class);
            intent.putExtra("usuari", mensaje.getText().toString());
            startActivity(intent);
        });



    }
}
