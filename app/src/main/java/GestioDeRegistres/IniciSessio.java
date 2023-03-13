package GestioDeRegistres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.tappingandroid.Inici;
import com.example.tappingandroid.R;
import com.example.tappingandroid.Resultats;

public class IniciSessio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici_sessio);


        EditText usuari = findViewById(R.id.et_usuari);
        EditText password = findViewById(R.id.et_password);
        Button login = findViewById(R.id.btn_login);
        Button registre = findViewById(R.id.btn_registre);

        Intent intent = getIntent();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acciones a realizar cuando se hace clic en el botón
                String sUsuari = usuari.getText().toString();
                String sPassword = password.getText().toString();

                // Validar el email
                if (TextUtils.isEmpty(sUsuari)) {
                    usuari.setError("Ha d'introduir un usuari");
                    return;
                }
                // Validar la contraseña
                if (TextUtils.isEmpty(sPassword)) {
                    password.setError("Ha d'introduir una contrasenya");
                    return;
                }

                Intent intent = new Intent(IniciSessio.this, Inici.class);
                intent.putExtra("usuari", sUsuari);
                startActivity(intent);
            }
        });
        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciSessio.this, Registre.class);
                startActivity(intent);
            }
        });


    }

    public void onClick(View v) {
        finish();
    }




}