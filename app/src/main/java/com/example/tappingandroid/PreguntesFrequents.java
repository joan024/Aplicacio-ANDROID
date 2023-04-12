package com.example.tappingandroid;

import static com.example.tappingandroid.Conexio.ConexioBD.closeConnection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Adapter.NoticiaAdapter;
import com.example.tappingandroid.Adapter.OpinioAdapter;
import com.example.tappingandroid.Adapter.PreguntaAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Noticia;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Pregunta;
import com.example.tappingandroid.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreguntesFrequents extends AppCompatActivity {

    @BindView(R.id.tv_pregunta) TextView tv_pregunta;
    @BindView(R.id.tv_resposta) TextView tv_resposta;
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
        //ButterKnife.bind(this);
        //ivTornar.setOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.rv_pregunta);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        preguntas = new ArrayList<>();
        try {
            agafarDades();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adaptador = new PreguntaAdapter((ArrayList<Pregunta>) preguntas);
        adaptador.setOnItemClickListener(position -> {
            Pregunta preguntaSelec = preguntas.get(position);

            Intent intent = new Intent(this, DetallsPregunta.class);
            intent.putExtra("pregunta", (CharSequence) preguntaSelec);
            startActivity(intent);

        });


        recyclerView.setAdapter(adaptador);

    }

    private void agafarDades() throws SQLException {

        conexio = ConexioBD.CONN();
        String sql = "SELECT * FROM preguntafrequent";

        boolean esValid = false;
        try {
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
        closeConnection(conexio);
    }
}
