package Adapter.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.R;

import Adapter.tappingandroid.GestioDeRegistres.Mensaje;
import Adapter.tappingandroid.GestioDeRegistres.Registre;

public class Contacta extends AppCompatActivity {

    Button mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_contacta);

        mensaje= findViewById(R.id.btn_mensaje);

        mensaje.setOnClickListener(v -> {
            Intent intent = new Intent(this, Mensaje.class);
            intent.putExtra("usuari", mensaje.getText().toString());
            startActivity(intent);
        });



    }
}
