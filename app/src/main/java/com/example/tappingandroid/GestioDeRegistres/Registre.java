package com.example.tappingandroid.GestioDeRegistres;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Inici;
import com.example.tappingandroid.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class Registre extends AppCompatActivity {

    // Creem les vistes que necessitem utilitzant ButterKnife per al binding
    @BindView(R.id.et_nom)
    EditText etNom;

    @BindView(R.id.et_cognom)
    EditText etCognom;

    @BindView(R.id.et_telefon)
    EditText etTelefon;

    @BindView(R.id.et_correu)
    EditText etCorreu;

    @BindView(R.id.et_data_naixement)
    EditText etDataNaixement;

    @BindView(R.id.et_contrasenya)
    EditText etContrasenya;

    @BindView(R.id.btn_registre)
    Button btnRegistre;

    String nom, cognom, telefon, dataNaix, correu, contrasenya;
    Connection conexio;

    private Calendar calendari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        // Bind views de ButterKnife per enllaçar les variables declarades anteriorment amb els elements del layout
        ButterKnife.bind(this);

        // Obtenim una instància del calendari
        calendari = Calendar.getInstance();

        // Configurem el botó de registre
        btnRegistre.setOnClickListener(v -> {
            boolean correcte = validarDades();
            if (correcte) {
                contrasenya= passwordHash(contrasenya);
                //Toast.makeText(Registre.this, String.valueOf(contrasenya), Toast.LENGTH_SHORT).show();
                correcte= inserirDades();
                if(correcte){
                    Toast.makeText(getApplicationContext(), "T'has registrat correctament.", Toast.LENGTH_SHORT).show();
                    startMainActivity(correu);
                }else{
                    Toast.makeText(getApplicationContext(), "Hi ha hagut un error al registre, torna a intentar-ho.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean inserirDades() {
        Connection cn = null;
        boolean insercioCorrecte = false;
        try{
            DateFormat dfNou = new SimpleDateFormat("yyyy-MM-dd");
            cn = ConexioBD.CONN();
            // Insertamos un nuevo registro en la tabla "usuari"
            String sql = "INSERT INTO usuari (nom, correu, contrasenya, data_registre) " +
                    "VALUES (?, ?, ?, ?)";

            // Obtener la fecha actual del sistema
            java.util.Calendar calendari2 = java.util.Calendar.getInstance();
            java.sql.Date dataActual = new java.sql.Date(calendari2.getTimeInMillis());
            PreparedStatement pstmt = cn.prepareStatement(sql);
            pstmt.setString(1, nom);
            pstmt.setString(2, correu);
            pstmt.setString(3, contrasenya);
            pstmt.setDate(4, dataActual);
            pstmt.executeUpdate();

            // Insertamos un nuevo registro en la tabla "consumidor"
            sql = "INSERT INTO consumidor (id_usuari, cognom, telefon, data_naixament) " +
                    "VALUES (LAST_INSERT_ID(), ?, ?, ?)";
            PreparedStatement pstmt2 = cn.prepareStatement(sql);
            pstmt2.setString(1, cognom);
            pstmt2.setString(2, telefon);
            pstmt2.setDate(3, new java.sql.Date(calendari.getTimeInMillis()));
            pstmt2.executeUpdate();

            insercioCorrecte = true;

        } catch (SQLException e) {
            Log.e("SQL", "Error al insertar dades", e);
            insercioCorrecte = false;
        }
        //ConexioBD.tencarConexio();
        return insercioCorrecte;
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


    private boolean validarDades() {

        nom = String.valueOf(etNom.getText());
        cognom = String.valueOf(etCognom.getText());
        telefon = String.valueOf(etTelefon.getText());
        dataNaix = String.valueOf(etDataNaixement.getText());
        correu = String.valueOf(etCorreu.getText());
        contrasenya = String.valueOf(etContrasenya.getText());

        if (nom.equals("") || cognom.equals("") || telefon.equals("") || dataNaix.equals("") || correu.equals("") || contrasenya.equals("")) {
            Toast.makeText(this, "Els camps no poden estar buits.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!telefon.matches("^[0-9]{9}$")) {
            Toast.makeText(this, "El format del telefon es incorrecte.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validarFormatCorreu(correu)) {
            Toast.makeText(this, "El format del correu es incorrecte.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validarContrasenya(contrasenya)) {
            new AlertDialog.Builder(this)
                    .setTitle("Error de contrasenya")
                    .setMessage("La contrassenya ha de contenir 8 caràcters, una lletra majúscula, una minúscula, un número i un símbol.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return false;
        }
        return true;
    }

    private boolean validarContrasenya(String contrassenya) {
        // Mínim 8 caràcters, una lletra majúscula, una minúscula, un número i un símbol
        String contrassenyaRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        // Comprova si la contrasenya compleix el patró
        return contrassenya.matches(contrassenyaRegex);
    }

    public boolean validarFormatCorreu(String correu) {
        String expresioRegular = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern patro = Pattern.compile(expresioRegular, Pattern.CASE_INSENSITIVE);
        Matcher matcher = patro.matcher(correu);
        return matcher.matches();
    }

    // Mostrem un diàleg per seleccionar la data
    public void mostrarDatePicker(View view) {
        int dia = calendari.get(Calendar.DAY_OF_MONTH);
        int mes = calendari.get(Calendar.MONTH);
        int any = calendari.get(Calendar.YEAR);

        // Creem un diàleg DatePicker
        DatePickerDialog dialog = new DatePickerDialog(Registre.this, (view1, any1, mes1, dia1) -> {
            //Aquest mètode executa l'acció quan l'usuari selecciona una data
            calendari.set(any1, mes1, dia1);
            etDataNaixement.setText(getFormattedDate());
        }, any, mes, dia);
        dialog.show();
    }

    // Formatem la data seleccionada
    @SuppressLint("DefaultLocale")
    private String getFormattedDate() {
        String data = "";

        if (calendari != null) {
            Calendar avui = Calendar.getInstance();
            if (calendari.after(avui)) {
                Toast.makeText(this, "La data de naixement no pot ser posterior a la data actual", Toast.LENGTH_SHORT).show();
            } else {
                int dia = calendari.get(Calendar.DAY_OF_MONTH);
                int mes = calendari.get(Calendar.MONTH) + 1;
                int any = calendari.get(Calendar.YEAR);

                data = String.format("%02d/%02d/%d", dia, mes, any);
            }
        }

        return data;
    }

    // Iniciem l'activitat de registre
    private void startMainActivity(String usuari) {
        Intent intent = new Intent(Registre.this, Inici.class);
        intent.putExtra("usuari", usuari);
        intent.putExtra("nom",nom);
        startActivity(intent);
    }
}