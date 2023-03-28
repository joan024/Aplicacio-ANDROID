package com.example.tappingandroid;

import static com.example.tappingandroid.Conexio.ConexioBD.closeConnection;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    @BindView(R.id.btn_guardar) Button btnGuardar;
    @BindView(R.id.iv_tornar) ImageView ivTornar;
    @BindView(R.id.btn_modificar_contrasenya) Button btnModificarContrasenya;

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
            try {
                actualitzarDades(1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        btnModificarContrasenya.setOnClickListener(v -> {
            modificarContrassenya();
        });
    }

    private void modificarContrassenya(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova Contrasenya");

        // Se crea un EditText para que el usuario pueda introducir la nueva contraseña
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Se añade un botón para confirmar la nueva contraseña
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String novaContrasenya = input.getText().toString();
                // Aquí se puede hacer lo que sea con la nueva contraseña
                contrasenya=passwordHash(novaContrasenya);
                try {
                    actualitzarDades(2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        // Se añade un botón para cancelar la operación
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Se muestra el cuadro de diálogo
        builder.show();
    }

    private String passwordHash(String contrasenya) {
        String hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(contrasenya.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hash) {
                stringBuilder.append(String.format("%02x", b));
            }
            hashedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    private void actualitzarDades(int opcio) throws ParseException {
        conexio2 = ConexioBD.CONN();
        int rowsAffected = 0;
        if(opcio==1) {
            // Obtenim una instància del calendari
            //S'HA DE CANVIAR LA DATA NAIX, ARA GUARDA L'ACTUAL
            calendari = Calendar.getInstance();
            String sql = "UPDATE usuari SET nom=\"" + etNom.getText() + "\", correu = \"" + etEmail.getText() + "\" WHERE id=" + id;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = dateFormat.parse(etNaix.getText().toString());
            calendari.setTime(date);
            // Formateamos la fecha en el formato deseado
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat2.format(calendari.getTime());
            String sql2 = "UPDATE consumidor SET cognom=\"" + etCognom.getText() + "\", telefon = \"" + etTelefon.getText() + "\", data_naixament=\"" + formattedDate + "\" WHERE id_usuari=" + id;

            try {
                Statement stmt = conexio2.createStatement();
                rowsAffected = stmt.executeUpdate(sql);
                rowsAffected = stmt.executeUpdate(sql2) + rowsAffected;
                if (rowsAffected > 0) {
                    Toast.makeText(this, "S'han actualitzat les dades", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No s'han actualitzat les dades", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(opcio==2){
            String sql = "UPDATE usuari SET contrasenya=\"" + contrasenya + "\" WHERE id=" + id;
            try {
                Statement stmt = conexio2.createStatement();
                rowsAffected = stmt.executeUpdate(sql);
                if (rowsAffected > 0) {
                    Toast.makeText(this, "S'han actualitzat les dades", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No s'han actualitzat les dades", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        closeConnection(conexio2);
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

        closeConnection(conexio);
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