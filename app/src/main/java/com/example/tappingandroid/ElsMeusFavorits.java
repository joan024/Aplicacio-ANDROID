package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ElsMeusFavorits extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LocalAdapter adaptador;
    private List<Local> locals;
    private List<Tapa> tapes;
    private List<Opinio> opinions;
    @BindView(R.id.iv_tornar) ImageView ivTornar;
    private Statement stmt = null;
    private ResultSet rs = null;
    private Connection conexio;
    private int id, id_local,id_horari;
    private String nom_local,direccio_local,telefon_local,descripcio, horari, link_foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_els_meus_favorits);
        ButterKnife.bind(this);

        id = Utilitats.agafarIdShared(this);

        ivTornar.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.rv_locals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locals = new ArrayList<>();
        try {
            getLocalsFavorits(); // funció que obté els locals favorits de la persona
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "No h'han pogut carregar els locals afegits a favorits.", Toast.LENGTH_SHORT).show();
        }

        adaptador = new LocalAdapter((ArrayList<Local>) locals); // adaptador per mostrar els locals favorits

        adaptador.setOnItemClickListener(position -> {
            // Obtenir l'objecte local a la posició seleccionada
            Local localSeleccionado = locals.get(position);

            // Crear un Intent per obrir l'activitat LocalDetail i passar la informació del local seleccionat
            Intent intent = new Intent(ElsMeusFavorits.this, DetallsLocal.class);
            intent.putExtra("local", localSeleccionado);
            startActivity(intent);
        });

        recyclerView.setAdapter(adaptador);

    }

    private void getLocalsFavorits() throws SQLException {
        conexio = ConexioBD.CONN();
        String sql = "SELECT * FROM local " +
                "INNER JOIN guarda ON guarda.id_local=local.id "+
                "WHERE guarda.id_usuari="+ id +
                " AND guarda.data_fi IS NULL";
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);
            Statement stmt2 = conexio.createStatement();
            Statement stmt3 = conexio.createStatement();
            Statement stmt4 = conexio.createStatement();
            Statement stmt5 = conexio.createStatement();

            while (rs.next()) {
                id_local = rs.getInt("id");
                nom_local = rs.getString("nom");
                direccio_local = rs.getString("direccio");
                telefon_local = rs.getString("telefon");
                id_horari = rs.getInt("id_horari");
                descripcio = rs.getString("descripcio");

                horari = Utilitats.queryHorari(stmt2,id_horari);
                tapes = Utilitats.queryTapes(stmt3, tapes, id_local);
                opinions = Utilitats.queryOpinions(stmt4, opinions, id_local);
                link_foto = Utilitats.queryFotoPrincipal(stmt5,id_local);

                Double mitjana= Utilitats.calcularMitjanaPuntuacio(opinions);

                locals.add(new Local(id_local,link_foto,nom_local,direccio_local,horari,mitjana,telefon_local,descripcio,tapes,opinions));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conexio.close();

    }
}