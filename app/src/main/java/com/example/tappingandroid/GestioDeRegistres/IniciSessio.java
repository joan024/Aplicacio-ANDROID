package com.example.tappingandroid.GestioDeRegistres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Inici;
import com.example.tappingandroid.R;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IniciSessio extends AppCompatActivity {

    // Declarem els objectes que utilitzarem per als elements visuals del layout
    @BindView(R.id.et_usuari)
    EditText usuari;

    @BindView(R.id.et_password)
    EditText password;

    @BindView(R.id.btn_login)
    Button login;

    @BindView(R.id.btn_registre)
    Button registre;

    Connection conexio;
    int id;
    String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici_sessio);

        // Bind views de ButterKnife per enllaçar les variables declarades anteriorment amb els elements del layout
        ButterKnife.bind(this);
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        login.setOnClickListener(v -> {
            if (validarFormulari()) {
                editor.putInt("id", id);
                editor.commit();
                startIniciActivity();
            }else{
                Toast.makeText(getApplicationContext(), "Hi ha hagut un error al intentar iniciar sessió, torna a intentar-ho.", Toast.LENGTH_SHORT).show();
            }
        });

        registre.setOnClickListener(v -> startRegistreActivity());
    }

    // Funció que s'encarrega de validar els camps del formulari
    private boolean validarFormulari() {
        String sUsuari = usuari.getText().toString();
        String sPassword = password.getText().toString();
        String email = null, contrasenya = null;

        conexio = ConexioBD.CONN();

        if (TextUtils.isEmpty(sUsuari)) {
            usuari.setError("Ha d'introduir un usuari");
            return false;
        }

        if (TextUtils.isEmpty(sPassword)) {
            password.setError("Ha d'introduir una contrasenya");
            return false;
        }

        sPassword=passwordHash(sPassword);
        String sql = "SELECT * FROM usuari WHERE correu=\""+ sUsuari +"\" AND contrasenya=\""+sPassword+"\"";

        Statement stmt = null;
        ResultSet rs = null;
        boolean esValid = false;
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
                email = rs.getString("correu");
                contrasenya = rs.getString("contrasenya");
                nom = rs.getString("nom");
                if(!(email.equals(sUsuari) && contrasenya.equals(sPassword))){
                    esValid = false;
                } else{
                    esValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return esValid;
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

    // Funció que s'encarrega d'iniciar l'activitat Inici
    private void startIniciActivity() {
        Intent intent = new Intent(IniciSessio.this, Inici.class);
        // Afegim el nom d'usuari com a extra a l'intent
        intent.putExtra("usuari", usuari.getText().toString());
        intent.putExtra("nom",nom);
        startActivity(intent);
    }

    // Funció que s'encarrega d'iniciar l'activitat de registre
    private void startRegistreActivity() {
        Intent intent = new Intent(IniciSessio.this, Registre.class);
        startActivity(intent);
    }
}
