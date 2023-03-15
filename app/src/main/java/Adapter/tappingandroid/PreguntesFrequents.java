package Adapter.tappingandroid;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tappingandroid.R;

public class PreguntesFrequents extends AppCompatActivity {

    private TextView pregunta;
    private TextView resposta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_item);


    }
}
