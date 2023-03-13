package com.example.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;

import java.util.Date;

public class AfegirOpinio extends AppCompatActivity {

    private EditText etNomUsuari, etPuntuacio, etComentari;
    private Button btnEnviar;

    private Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_opinio);

        etNomUsuari = findViewById(R.id.et_nom_usuari);
        etPuntuacio = findViewById(R.id.et_puntuacio);
        etComentari = findViewById(R.id.et_comentari);
        btnEnviar = findViewById(R.id.btn_enviar);

        //Rebem les dades del local
        // Intent intent = getIntent();
        //local = (Local) intent.getSerializableExtra("local");

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos introducidos por el usuario
                String nomUsuari = etNomUsuari.getText().toString();
                Double puntuacio = Double.valueOf(etPuntuacio.getText().toString());
                String comentari = etComentari.getText().toString();

                // Crear un objeto de opinión con los datos introducidos por el usuario
                Opinio opinion = new Opinio(nomUsuari, new Date(), comentari, puntuacio);

                // Añadir la opinión al local correspondiente
                local.afegirOpinio(opinion);
                // Devolver el local modificado a la actividad anterior
                Intent intent = new Intent();
                intent.putExtra("local", local);
                setResult(RESULT_OK, intent);

                // Finalizar la actividad actual
                finish();
            }
        });
    }
}
