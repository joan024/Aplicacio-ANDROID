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
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        id = sharedPref.getInt("id", 0);

        ivTornar.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.rv_locals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locals = new ArrayList<>();
        try {
            getLocalsFavorits(); // funció que obté els locals favorits de la persona
        } catch (SQLException e) {
            e.printStackTrace();
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

                queryHorari(stmt2);
                queryTapes(stmt3);
                queryOpinions(stmt4);
                queryFotoPrincipal(stmt5);

                Double mitjana= calcularMitjanaPuntuacio();

                locals.add(new Local(link_foto,nom_local,direccio_local,horari,mitjana,telefon_local,descripcio,tapes,opinions));

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        conexio.close();

    }

    private void queryFotoPrincipal(Statement stmt5) throws SQLException {
        String sql5 = "SELECT * FROM foto WHERE id_local=" + id_local;
        ResultSet rsFoto = stmt5.executeQuery(sql5);
        if (rsFoto.next()) {
            link_foto = rsFoto.getString("link");
        }
        rsFoto.close();
    }

    private void queryHorari(Statement stmt2) throws SQLException {
        String sql2 = "SELECT * FROM horari WHERE id=" + id_horari;
        ResultSet rsHorari = stmt2.executeQuery(sql2);
        while (rsHorari.next()) {
            String dia= obtindreElDiaSetmanal();
            horari = rsHorari.getString(dia);
        }
        rsHorari.close();
    }

    private void queryTapes(Statement stmt3) throws SQLException {
        tapes = new ArrayList<>();
        String sql3 = "SELECT tapa.nom, local_tapa.personalitzacio, local_tapa.preu FROM tapa INNER JOIN local_tapa ON local_tapa.id_tapa=tapa.id WHERE local_tapa.id_local=" + id_local;
        ResultSet rsTapes = stmt3.executeQuery(sql3);
        while (rsTapes.next()) {
            String nom_tapa = rsTapes.getString("nom");
            String personalitzacio_tapa = rsTapes.getString("personalitzacio");
            double preu = rsTapes.getDouble("preu");

            tapes.add(new Tapa(nom_tapa,preu,personalitzacio_tapa));
        }
        rsTapes.close();
    }

    private void queryOpinions(Statement stmt4) throws SQLException {
        opinions = new ArrayList<>();
        String sql4 = "SELECT * FROM valoracio INNER JOIN usuari ON usuari.id=valoracio.id_usuari WHERE valoracio.id_local=" + id_local;
        ResultSet rsOpinions = stmt4.executeQuery(sql4);
        while (rsOpinions.next()) {
            String nom_valoracio = rsOpinions.getString("nom");
            double puntuacio = rsOpinions.getDouble("puntuacio");
            String comentari = rsOpinions.getString("comentari");
            Date data = rsOpinions.getDate("data");

            Log.d("juliaaa", String.valueOf(puntuacio));
            opinions.add(new Opinio(nom_valoracio,data,comentari,puntuacio));
        }
        rsOpinions.close();
    }

    private Double calcularMitjanaPuntuacio() {
        double sumaPuntuacions = 0;
        int numOpinions = opinions.size();

        for (Opinio opinio : opinions) {
            sumaPuntuacions += opinio.getPuntuacio();
        }
        return sumaPuntuacions / numOpinions;
    }

    private String obtindreElDiaSetmanal() {
        // Obtener el día de la semana actual
        Calendar calendari = Calendar.getInstance();
        int diaSetmana = calendari.get(Calendar.DAY_OF_WEEK);

        // Obtener el nombre del día de la semana
        String[] dayNames = new String[]{
                "diumenge",
                "dilluns",
                "dimarts",
                "dimecres",
                "dijous",
                "divendres",
                "dissabte"
        };

        return dayNames[diaSetmana - 1];
    }
/*
    private List<Local> getLocalsFavorits() throws ParseException {
        locals = new ArrayList<>();
        List <Tapa> tapes = obternirTapes();
        List <Opinio> opinions = obternirOpinions();
        locals.add(new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions));
        locals.add(new Local(R.drawable.logotiptapping, "Tercer local", "C/Un altre carrer", "18:00-22:00", 7.8, "659673959","El restaurant \"La Cuina del Mar\" és un lloc acollidor i elegant ubicat al centre de la ciutat. La decoració és d'estil marí, amb parets de rajoles blaves i blanques que recorden l'oceà i els detalls de fusta que evoquen l'ambient d'un vaixell.", tapes, opinions));

        return locals;
    }
    private List<Tapa> obternirTapes() {
        List <Tapa> tapes = new ArrayList<>();
        tapes.add(new Tapa("Braves",4.5));
        tapes.add(new Tapa("Chipirons", 6.7));
        tapes.add(new Tapa("Croquetes", 3));
        tapes.add(new Tapa("Patates fregides", 2.4));
        tapes.add(new Tapa("Truita de patates", 5.8));
        return tapes;
    }
    private List<Opinio> obternirOpinions() throws ParseException {
        List <Opinio> opinions = new ArrayList<>();
        opinions.add(new Opinio("Juan","12/02/2022","Aquest local es top.",7.8));
        opinions.add(new Opinio("Maria","01/03/2022","Tornare a prendre unes braves segur.",9.2));
        opinions.add(new Opinio("Lluc","03/02/2022","No crec que hi torni, personal desagradable.",4.5));
        return opinions;
    }
    */

}