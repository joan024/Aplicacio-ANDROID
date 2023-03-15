package Adapter.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tappingandroid.R;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_les_meves_dades);
        ButterKnife.bind(this);

        ivTornar.setOnClickListener(v -> onBackPressed());

    }
    public void mostrarDatePicker(View view) {
        final Calendar calendari = Calendar.getInstance();
        // Obtener la fecha del EditText etNaix y establecerla en el objeto Calendar
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