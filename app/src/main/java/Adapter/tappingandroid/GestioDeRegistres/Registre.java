package Adapter.tappingandroid.GestioDeRegistres;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Adapter.tappingandroid.Inici;
import com.example.tappingandroid.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Registre extends AppCompatActivity  {

    @BindView(R.id.et_correu)
    EditText etCorreu;

    @BindView(R.id.et_data_naixement)
    EditText etDataNaixement;

    @BindView(R.id.btn_registre)
    EditText btnRegistre;

    private Calendar calendari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        ButterKnife.bind(this);

        calendari = Calendar.getInstance();

        btnRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegistreActivity();
            }
        });
    }

    public void mostrarDatePicker(View view) {
        int dia = calendari.get(Calendar.DAY_OF_MONTH);
        int mes = calendari.get(Calendar.MONTH);
        int any = calendari.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(Registre.this, (view1, any1, mes1, dia1) -> {
            //Aquest mètode executa l'acció quan l'usuari selecciona una data
            calendari.set(any1, mes1, dia1);
            etDataNaixement.setText(getFormattedDate());
        }, any, mes, dia);
        dialog.show();
    }

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

    private void startRegistreActivity() {
        Intent intent = new Intent(Registre.this, Inici.class);
        intent.putExtra("usuari", etCorreu.getText().toString());
        startActivity(intent);
    }
}
