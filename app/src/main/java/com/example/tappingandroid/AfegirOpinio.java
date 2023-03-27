package com.example.tappingandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class AfegirOpinio extends AppCompatActivity {

    // Declarem els elements de la vista
    private EditText etNomUsuari, etPuntuacio, etComentari;
    private Button btnEnviar;
    private Calendar calendari = Calendar.getInstance();
    private Local local;
    private String nomUsuari,comentari;
    private double puntuacio;
    private Connection conexio, conexio2;
    private Statement stmt = null;
    private int id,id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_opinio);
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id",0);
        // Assignem els elements de la vista a les variables corresponents
        etNomUsuari = findViewById(R.id.et_nom_usuari);
        etPuntuacio = findViewById(R.id.et_puntuacio);
        etComentari = findViewById(R.id.et_comentari);
        btnEnviar = findViewById(R.id.btn_enviar);

        //Rebem les dades del local
        Intent intent = getIntent();
       // local = (Local) intent.getSerializableExtra("local");

        btnEnviar.setOnClickListener(v -> {
            // Obtenir les dades introduïdes per l'usuari
             nomUsuari = etNomUsuari.getText().toString();
             puntuacio = Double.valueOf(etPuntuacio.getText().toString());
             comentari = etComentari.getText().toString();

             //webapps.insjoanbrudieu.cat

            // Crear un objecte d'opinió amb les dades introduïdes per l'usuari
            Opinio opinion = new Opinio(nomUsuari, new Date(), comentari, puntuacio);

            insertar(id,id2,puntuacio,comentari,calendari);
            // Afegir l'opinió al local corresponent
            local.afegirOpinio(opinion);
            // Tornar el local modificat a l'activitat anterior
            Intent intent1 = new Intent();
            intent.putExtra("local", local);
            setResult(RESULT_OK, intent);




            // Finalitzar l'activitat actual
            finish();
        });
    }
    public void insertar (int id,int idLocal,Double puntuacio,String comentari, Calendar calendari){
        conexio2 = ConexioBD.CONN();
        calendari = Calendar.getInstance();
        String sql = "INSERT INTO VALORACIO (id_usuario, id_local, puntuacio, comentari, date) VALUES (" + id + ", " +idLocal + ", " + puntuacio + ", '" + comentari + "', '" + new java.sql.Date(calendari.getTimeInMillis()) + "')";        int rowsAffected=0;
        try {
            stmt = conexio2.createStatement();
            Statement stmt = conexio2.createStatement();
            rowsAffected = stmt.executeUpdate(sql)+rowsAffected;
            if (rowsAffected > 0) {
                Toast.makeText(this, "S'ha insertado les dades", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No s'ha insertado les dades", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }
}
