package com.example.tappingandroid.GestioDeRegistres;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Inici;
import com.example.tappingandroid.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici_sessio);

        // Bind views de ButterKnife per enllaçar les variables declarades anteriorment amb els elements del layout
        ButterKnife.bind(this);

        login.setOnClickListener(v -> {
            if (validarFormulari()) {
                startIniciActivity();
            }
        });

        registre.setOnClickListener(v -> startRegistreActivity());
    }

    // Funció que s'encarrega de validar els camps del formulari
    private boolean validarFormulari() {
        String sUsuari = usuari.getText().toString();
        String sPassword = password.getText().toString();

        conexio = ConexioBD.conectar();

        String sql = "SELECT * FROM usuarios WHERE correu=\""+ sUsuari +"\" AND contrasenya=\""+sPassword+"\"";

        if (TextUtils.isEmpty(sUsuari)) {
            usuari.setError("Ha d'introduir un usuari");
            return false;
        }

        if (TextUtils.isEmpty(sPassword)) {
            password.setError("Ha d'introduir una contrasenya");
            return false;
        }

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                id = rs.getInt("id");
                String email = rs.getString("correu");
                String contrasenya = rs.getString("contrasenya");

                if(!(email.equals(sUsuari) && contrasenya.equals(sPassword))){
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // Funció que s'encarrega d'iniciar l'activitat Inici
    private void startIniciActivity() {
        Intent intent = new Intent(IniciSessio.this, Inici.class);
        // Afegim el nom d'usuari com a extra a l'intent
        intent.putExtra("usuari", usuari.getText().toString());
        startActivity(intent);
    }

    // Funció que s'encarrega d'iniciar l'activitat de registre
    private void startRegistreActivity() {
        Intent intent = new Intent(IniciSessio.this, Registre.class);
        startActivity(intent);
    }
}
