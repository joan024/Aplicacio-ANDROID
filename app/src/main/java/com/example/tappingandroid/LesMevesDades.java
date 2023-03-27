package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.R;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;


public class LesMevesDades extends AppCompatActivity {
    @BindView(R.id.et_nom) EditText etNom;
    @BindView(R.id.et_cognom) EditText etCognom;
    @BindView(R.id.et_email) EditText etEmail;
    @BindView(R.id.et_data_naixement) EditText etNaix;
    @BindView(R.id.et_telefon) EditText etTelefon;
    @BindView(R.id.et_password) EditText etPassword;
    @BindView(R.id.btn_guardar) Button btnGuardar;
    @BindView(R.id.iv_tornar) ImageView ivTornar;

    Calendar calendari;
    Connection conexio, conexio2;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String email,contrasenya, nom, cognom, telefon, data_naixament;
    int id = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_meves_dades);
        ButterKnife.bind(this);

        // Recibe el intent
        Intent intentUsuari = getIntent();

        // Obtiene el valor del String con la clave "usuari"
        String correu = intentUsuari.getStringExtra("usuari");
        agafarDades(correu);
        setegarDades();
        ivTornar.setOnClickListener(v -> onBackPressed());
        btnGuardar.setOnClickListener(v -> {
            actualitzarDades();
        });

    }

    private void actualitzarDades() {
        conexio2 = ConexioBD.CONN();
        // Obtenim una instància del calendari
        //S'HA DE CANVIAR LA DATA NAIX, ARA GUARDA L'ACTUAL
        calendari = Calendar.getInstance();
        String sql = "UPDATE usuari SET nom=\""+etNom.getText()+"\", correu = \""+etEmail.getText()+"\" WHERE id="+id;
        String sql2 = "UPDATE consumidor SET cognom=\""+etCognom.getText()+"\", telefon = \""+etTelefon.getText()+"\", data_naixament=\""+new java.sql.Date(calendari.getTimeInMillis())+"\" WHERE id_usuari="+id;
        int rowsAffected=0;


        try {
            stmt = conexio2.createStatement();
            Statement stmt = conexio2.createStatement();
            rowsAffected = stmt.executeUpdate(sql);
            rowsAffected = stmt.executeUpdate(sql2)+rowsAffected;
            if (rowsAffected > 0) {
                Toast.makeText(this, "S'han actualitzat les dades", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No s'han actualitzat les dades", Toast.LENGTH_SHORT).show();
            }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setegarDades() {
        etNom.setText(nom);
        etCognom.setText(cognom);
        etEmail.setText(email);
        etNaix.setText(data_naixament);
        etTelefon.setText(telefon);
    }

    private void agafarDades(String correu) {
        conexio = ConexioBD.CONN();
        String sql = "SELECT * FROM usuari WHERE correu=\""+ correu +"\"";


        boolean esValid = false;
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
                email = rs.getString("correu");
                contrasenya = rs.getString("contrasenya");
                nom = rs.getString("nom");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "SELECT * FROM consumidor WHERE id_usuari="+ id;

        try {
            rs2 = stmt.executeQuery(sql);

            while (rs2.next()) {
                cognom = rs2.getString("cognom");
                telefon = rs2.getString("telefon");
                data_naixament = rs2.getString("data_naixament");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrarDatePicker(View view) {
        calendari = Calendar.getInstance();
        // Obtenir la data de l'EditText etNaix i establir-la a l'objecte Calendar
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date data = dateFormat.parse(etNaix.getText().toString());
            calendari.setTime(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int dia = calendari.get(Calendar.DAY_OF_MONTH);
        int mes = calendari.get(Calendar.MONTH);
        int any = calendari.get(Calendar.YEAR);

        DatePickerDialog dialogo = new DatePickerDialog(LesMevesDades.this, (view1, any1, mes1, dia1) -> {
            //Aquest mètode executa l'acció quan l'usuari selecciona una data
            String data = dia1 + "/" + (mes1 +1) + "/" + any1;
            etNaix.setText(data);
        }, any, mes, dia);
        dialogo.show();
    }
}