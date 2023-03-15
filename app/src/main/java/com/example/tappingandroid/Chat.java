package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.tappingandroid.Adapter.XatAdapter;
import com.example.tappingandroid.Dades.Missatge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Chat extends AppCompatActivity {

    //Es defineixen variables privades per a cadascun dels elements de la interfície d'usuari que s'utilitzaran a l'activitat
    private ListView xatView;
    private EditText etMissatge;
    private Button btnEnviar;
    private ImageView iv_tornar;

    private XatAdapter chatAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Obtener referencias a los elementos de la interfaz de usuario
        xatView = findViewById(R.id.xatListView);
        etMissatge = findViewById(R.id.et_missatge);
        btnEnviar = findViewById(R.id.btn_enviar);
        iv_tornar = findViewById(R.id.iv_tornar);

        iv_tornar.setOnClickListener(v -> onBackPressed());

        // Es defineix un objecte de tipus XatAdapter per gestionar l'adaptador personalitzat
        chatAdapter = new XatAdapter(this, R.layout.misatge_item, new ArrayList<>());
        xatView.setAdapter(chatAdapter);

        // Configurar el botó d'enviament de missatges per afegir missatges nous a l'adaptador
        btnEnviar.setOnClickListener(view -> {
            String message = etMissatge.getText().toString();
            String name = "Jo"; // Obtenir el nom de l'usuari actual
            String time = getCurrentTime(); // Obtenir l'hora actual

            Missatge newMessage = new Missatge(name, message, time);
            chatAdapter.add(newMessage);

            etMissatge.setText("");
        });
    }

    //torna l'hora actual en format "HH:mm".
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

