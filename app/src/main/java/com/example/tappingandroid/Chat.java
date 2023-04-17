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
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Missatge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Chat extends AppCompatActivity {

    //Es defineixen variables privades per a cadascun dels elements de la interfície d'usuari que s'utilitzaran a l'activitat
    private ListView xatView;
    private EditText etMissatge;
    private Button btnEnviar;
    private ImageView iv_tornar;
    private XatAdapter chatAdapter;
    private int userId = 2,id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        id = Utilitats.agafarIdShared(this);

        // Obtener referencias a los elementos de la interfaz de usuario
        xatView = findViewById(R.id.xatListView);
        etMissatge = findViewById(R.id.et_missatge);
        btnEnviar = findViewById(R.id.btn_enviar);
        iv_tornar = findViewById(R.id.iv_tornar);

        iv_tornar.setOnClickListener(v -> onBackPressed());

        // Es defineix un objecte de tipus XatAdapter per gestionar l'adaptador personalitzat
        chatAdapter = new XatAdapter(this, R.layout.misatge_item, new ArrayList<>());
        xatView.setAdapter(chatAdapter);

        // Obtener los mensajes de la base de datos
        List<Missatge> missatges = getMissatgesFromDatabase();

        // Mostrar los mensajes en el chat
        chatAdapter.addAll(missatges);

        // Configurar el botó d'enviament de missatges per afegir missatges nous a l'adaptador
        btnEnviar.setOnClickListener(view -> {
            String message = etMissatge.getText().toString();
            String name = "Jo";
            String time = getCurrentTime();
            Missatge newMessage = new Missatge(name, message, time);

            // Insertar el nuevo mensaje en la base de datos
            insertMissatgeToDatabase(newMessage);

            chatAdapter.add(newMessage);
            etMissatge.setText("");
        });
    }

    private int getIdXat() throws SQLException {
        Connection conn = ConexioBD.CONN();
        int id_xat = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM xat WHERE id_empresa = " + id);

            while (rs.next()) {
                id_xat = rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_xat;
    }

        // Obtener los mensajes de la base de datos
    private List<Missatge> getMissatgesFromDatabase() {
        List<Missatge> missatges = new ArrayList<>();
        Connection conn = ConexioBD.CONN();

        try {
            int id_xat = getIdXat();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM lineaxat WHERE id_xat = "+id_xat);

            while (rs.next()) {
                String name= rs.getInt("usuari") == 1 ? "Administrador" : "Jo"; // Si el usuari de la BD es 1 se pone "Empresa" si no se pone "Jo"
                String message = rs.getString("missatge");
                String time = rs.getString("temps");
                Missatge missatge = new Missatge(name, message, time);
                missatges.add(missatge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return missatges;
    }

    private void insertMissatgeToDatabase(Missatge missatge) {
        Connection conn = ConexioBD.CONN();

        try {
            int id_xat = getIdXat();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO lineaxat (id_xat, usuari, missatge, temps) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, id_xat);
            pstmt.setInt(2, userId);
            pstmt.setString(3, missatge.getMissatge());
            pstmt.setString(4, missatge.getHora());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    //torna l'hora actual en format "HH:mm".
    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}

