package com.example.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tappingandroid.Dades.Pregunta;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetallsPregunta extends AppCompatActivity {
    @BindView(R.id.tv_pregunta) TextView tv_pregunta;
    @BindView(R.id.tv_resposta) TextView tv_resposta;
    @BindView(R.id.iv_tornar) ImageView ivTornar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_item);
        ButterKnife.bind(this);

        ivTornar.setOnClickListener(v -> onBackPressed());
        Intent intent = getIntent();
        Pregunta pregunta = (Pregunta) intent.getSerializableExtra("pregunta");

        tv_pregunta.setText(pregunta.getPregunta());
        tv_resposta.setText(pregunta.getResposta());
    }
}
