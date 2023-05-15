package com.example.tappingandroid;

import static com.example.tappingandroid.Conexio.ConexioBD.closeConnection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Adapter.PreguntaAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Pregunta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreguntesFrequents extends AppCompatActivity {

    @BindView(R.id.iv_tornar) ImageView ivTornar;
    RecyclerView recyclerView;
    private PreguntaAdapter adaptador;
    private ArrayList<Pregunta> preguntas;


     Connection conexio;
     Statement stmt = null;
     ResultSet rs = null;
    int id=0;String pregunta,resposta;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_item);
        ButterKnife.bind(this);

        // Configurar el botó de tornada
        ivTornar.setOnClickListener(v -> onBackPressed());

        // Configurar el RecyclerView i obtenir les dades de la base de dades
        recyclerView = findViewById(R.id.rv_pregunta);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        preguntas = new ArrayList<>();
        try {
            agafarDades();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Configurar l'adaptador i afegir el listener d'elements
        adaptador = new PreguntaAdapter((ArrayList<Pregunta>) preguntas);
        adaptador.setOnItemClickListener(position -> {
            Pregunta preguntaSelec = preguntas.get(position);

            Intent intent = new Intent(this, DetallsPregunta.class);
            intent.putExtra("pregunta", (CharSequence) preguntaSelec);
            startActivity(intent);

        });

        // Establir l'adaptador del RecyclerView
        recyclerView.setAdapter(adaptador);

    }

    // Mètode per obtenir les dades de la base de dades
    private void agafarDades() throws SQLException {

        // Establir la connexió a la base de dades i fer la consulta SQL
        conexio = ConexioBD.connectar();
        String sql = "SELECT * FROM preguntafrequent";

        boolean esValid = false;
        try {
            // Executar la consulta i afegir les dades a l'ArrayList de preguntes
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {

                id = rs.getInt("id");
                    pregunta = rs.getString("pregunta");
                    resposta = rs.getString("resposta");
                    preguntas.add(new Pregunta(id,pregunta,resposta));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Tancar la connexió a la base de dades
        closeConnection(conexio);
    }
    // Configurar el botó d'entrada a la barra d'acció
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
