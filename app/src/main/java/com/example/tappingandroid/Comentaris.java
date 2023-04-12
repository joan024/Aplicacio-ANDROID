package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tappingandroid.Adapter.OpinioAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Comentaris extends AppCompatActivity {

    @BindView(R.id.iv_tornar) ImageView ivTornar;
    @BindView(R.id.rv_opinions) RecyclerView recyclerViewOpinions;


    Connection conexio, conexio2;
    Statement stmt = null;
    ResultSet rs = null;
    int id=0;String comentari,data;
    double puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentaris);
        ButterKnife.bind(this);

        //List<Local> locals = new ArrayList<Local>();
        ivTornar.setOnClickListener(v -> onBackPressed());

        // Crear objectes de dades per al local i les seves opinions
        Local local = null;
        List<Tapa> tapes = new ArrayList<Tapa>();
        //tapes.add(new Tapa("Braves",6.4));
        List <Opinio> opinions =  new ArrayList<Opinio>();
        try {
            opinions.add(new Opinio("Juan","12/02/2022","Aquest local es top.",7.8));
            select(opinions);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentOpinion =getIntent();
        String opinion = intentOpinion.getStringExtra("Opinion");
        local = new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions);


        recyclerViewOpinions.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOpinions.setAdapter((new OpinioAdapter(local.getOpinions())));

    }

    public void select(List <Opinio> opinions){

        conexio = ConexioBD.CONN();
        String sql = "SELECT * FROM valoracio";


        boolean esValid = false;
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
                puntuacion = rs.getDouble("puntuacio");
                comentari = rs.getString("comentari");
                data = rs.getString("data");

                Opinio op = new Opinio(id+"",data,comentari,puntuacion);
                opinions.add(op);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}