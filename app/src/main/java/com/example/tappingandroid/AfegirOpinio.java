package com.example.tappingandroid;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class AfegirOpinio extends AppCompatActivity implements Serializable {

    // Declarem els elements de la vista
    private EditText etNomUsuari, etPuntuacio, etComentari;
    private Button btnEnviar;
    private Calendar calendari = Calendar.getInstance();
    private String nomUsuari,comentari;
    private double puntuacio;
    private Statement stmt = null;
    private int id,id2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_opinio);

        id = Utilitats.agafarIdShared(this);

        // Assignem els elements de la vista a les variables corresponents
        etPuntuacio = findViewById(R.id.et_puntuacio);
        etComentari = findViewById(R.id.et_comentari);
        btnEnviar = findViewById(R.id.btn_enviar);

        //Rebem les dades del local
        Intent intent = getIntent();
        id2 = intent.getIntExtra("local",0);

        btnEnviar.setOnClickListener(v -> {
            // Obtenir les dades introduïdes per l'usuari
             puntuacio = Double.parseDouble(etPuntuacio.getText().toString());
             comentari = etComentari.getText().toString();

            // Crear un objecte d'opinió amb les dades introduïdes per l'usuari
            Opinio opinion = new Opinio(nomUsuari, new Date(), comentari, puntuacio);
            insertar(id,id2,puntuacio,comentari,calendari);

            // Tornar el local modificat a l'activitat anterior
            Intent intent1 = new Intent();
            setResult(RESULT_OK, intent);
            // Finalitzar l'activitat actual
            finish();
        });
    }
    public void insertar(int id, int idLocal, Double puntuacio, String comentari, Calendar calendari) {
        Connection conn = ConexioBD.CONN();
        PreparedStatement stmt = null;
        String sql = "INSERT INTO valoracio (id_usuari, id_local, puntuacio, comentari, data) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = 0;
        Log.d("JULIAAAA", String.valueOf(id));
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setInt(2, idLocal);
            stmt.setDouble(3, puntuacio);
            stmt.setString(4, comentari);
            stmt.setDate(5, new java.sql.Date(calendari.getTimeInMillis()));
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                Toast.makeText(this,"La valoració s'ha publicat correctament.",Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Toast.makeText(this,"Hi ha hagut un error al publicar la valoració.",Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}