package GestioDeRegistres;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.R;

import java.util.Calendar;

public class Registre extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText editTextDataNaixement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        Bundle bundle = this.getIntent().getExtras();
        editTextDataNaixement = findViewById(R.id.et_data_naixement);
        editTextDataNaixement.setOnClickListener(v -> {
            Calendar calendari = Calendar.getInstance();
            int any = calendari.get(Calendar.YEAR);
            int mes = calendari.get(Calendar.MONTH);
            int dia = calendari.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialogData = new DatePickerDialog(
                    this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    this,
                    any, mes, dia);
            dialogData.show();
        });
    }

    @Override
    public void onDateSet(@NonNull DatePicker view, int any, int mes, int dia) {
        String dataSeleccionada = dia + "/" + (mes + 1) + "/" + any;
        editTextDataNaixement.setText(dataSeleccionada);
    }
    public void onClick(View v) {
        finish();
    }
}
