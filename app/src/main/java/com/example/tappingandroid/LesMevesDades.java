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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

@SuppressLint("NonConstantResourceId")
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

        // Obtenim l'intent que ha llançat aquesta activitat
        Intent intentUsuari = getIntent();

        // Obtenim l'intent que ha llançat aquesta activitat
        String correu = intentUsuari.getStringExtra("usuari");
        agafarDades(correu);
        setegarDades();
        ivTornar.setOnClickListener(v -> onBackPressed());
        btnGuardar.setOnClickListener(v -> {
            try {
                actualitzarDades(1);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"No s'han pogut actualitzar les dades.",Toast.LENGTH_SHORT).show();
            }
        });
        btnModificarContrasenya.setOnClickListener(v -> {
            modificarContrassenya();
        });
    }

    // Mètode que mostra un quadre de diàleg per a canviar la contrasenya
    private void modificarContrassenya(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova Contrasenya");

        // Creem un EditText perquè l'usuari pugui introduir la nova contrasenya
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Afegim un botó per confirmar la nova contrasenya
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String novaContrasenya = input.getText().toString();
                // Aquí es pot fer el que sigui amb la nova contrasenya
                contrasenya=passwordHash(novaContrasenya);
                try {
                    actualitzarDades(2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"No s'ha pogut actualitzar la contrassenya.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Se'afegeix un boto per cancelar la operació
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());
        // Es mostre el quadre
        builder.show();
    }

    private String passwordHash(String contrasenya) {
        String hashedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(contrasenya.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : hash) { // Convertim el hash en una cadena hexadecimal
                stringBuilder.append(String.format("%02x", b));
            }
            hashedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    private void actualitzarDades(int opcio) throws ParseException {
        conexio2 = ConexioBD.connectar();
        int rowsAffected = 0;
        if(opcio == 1) {
            // Obtenemos una instancia del calendario
            calendari = Calendar.getInstance();

            String dob = etNaix.getText().toString(); //obtindre la data de naix introduida en format cadena
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //Establir el format de la data
            java.util.Date parsedDate = dateFormat.parse(dob); //Convertir la data de cadena a un objecte Date

            String sql = "UPDATE usuari SET nom = ?, correu = ? WHERE id = ?";
            String sql2 = "UPDATE consumidor SET cognom = ?, telefon = ?, data_naixament = ? WHERE id_usuari = ?";

            try {
                PreparedStatement stmt1 = conexio2.prepareStatement(sql);
                PreparedStatement stmt2 = conexio2.prepareStatement(sql2);

                stmt1.setString(1, etNom.getText().toString());
                stmt1.setString(2, etEmail.getText().toString());
                stmt1.setInt(3, id);

                stmt2.setString(1, etCognom.getText().toString());
                stmt2.setString(2, etTelefon.getText().toString());
                stmt2.setDate(3, new java.sql.Date(parsedDate.getTime()));
                stmt2.setInt(4, id);

                rowsAffected = stmt1.executeUpdate() + stmt2.executeUpdate();

                if (rowsAffected > 0) {
                    Toast.makeText(this, "S'han actualitzat les dades", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No s'han actualitzat les dades", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(opcio == 2) {
            String sql = "UPDATE usuari SET contrasenya = ? WHERE id = ?";
            try {
                PreparedStatement stmt = conexio2.prepareStatement(sql);
                stmt.setString(1, contrasenya);
                stmt.setInt(2, id);
                rowsAffected = stmt.executeUpdate();

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

    //Aquest mètode estableix les dades del consumidor als EditTexts corresponents
    private void setegarDades() {
        etNom.setText(nom);
        etCognom.setText(cognom);
        etEmail.setText(email);
        etNaix.setText(data_naixament);
        etTelefon.setText(telefon);
    }

    //Aquest mètode obté les dades del consumidor a través del seu correu electrònic i les guarda en les variables corresponents
    private void agafarDades(String correu) {
        conexio = ConexioBD.connectar();
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

    //Aquest mètode s'executa quan l'usuari fa clic a l'EditText etNaix, mostra un diàleg DatePicker per seleccionar la data de naixement
    public void mostrarDatePicker(View view) {
        calendari = Calendar.getInstance();
        // Obtenir la data de l'EditText etNaix i establir-la a l'objecte Calendar
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
            String data = any1 + "-" + (mes1 +1) + "-" + dia1;
            etNaix.setText(data);
        }, any, mes, dia);
        dialogo.show();
    }
}