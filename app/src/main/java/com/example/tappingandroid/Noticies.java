package com.example.tappingandroid;

import static com.example.tappingandroid.Conexio.ConexioBD.closeConnection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Descompte;
import com.example.tappingandroid.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Adapter.NoticiaAdapter;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Noticia;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Noticies extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NoticiaAdapter adaptador;
    private ArrayList<Noticia> noticies;
    Statement stmt = null;
    ResultSet rs = null;
    Connection conexio;
    @BindView(R.id.iv_tornar) ImageView ivTornar;
    private String titol,descripcio, foto,data_publicacio,data_inici,data_final;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticies);
        ButterKnife.bind(this);
        //Acció en fer clic al botó "tornar"
        ivTornar.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.rv_noticia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noticies = new ArrayList<>();
        AgafarNoticies();

        adaptador = new NoticiaAdapter((ArrayList<Noticia>) noticies);

        adaptador.setOnItemClickListener(position -> {
            Noticia noticiaSelecionada = noticies.get(position);

            Intent intent = new Intent(this, DetallsNoticia.class);
            intent.putExtra("noticia", noticiaSelecionada);
            startActivity(intent);

        });
        recyclerView.setAdapter(adaptador);
    }
    private void AgafarNoticies() {
        // Obtener la fecha actual en formato ISO
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataActual = new Date();
        String dataActualString = dateFormat.format(dataActual);

        // Construir la consulta SQL
        String sql = "SELECT * FROM noticia WHERE data_inici <= '"+dataActualString+"' AND (data_fi >= '"+dataActualString+"' OR data_fi IS NULL) ORDER BY data_inici DESC";

        conexio = ConexioBD.CONN();
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                titol = rs.getString("titol");
                descripcio = rs.getString("descripcio");
                foto = rs.getString("foto");
                data_publicacio = String.valueOf(rs.getDate("data_publicacio"));
                data_inici = String.valueOf(rs.getDate("data_inici"));
                data_final = String.valueOf(rs.getDate("data_fi"));

                noticies.add(new Noticia(foto,titol,descripcio,data_inici,data_final,data_publicacio));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection(conexio);

    }

}
