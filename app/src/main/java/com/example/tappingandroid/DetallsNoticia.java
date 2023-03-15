package com.example.tappingandroid;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.tappingandroid.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetallsNoticia extends AppCompatActivity {
    @BindView(R.id.iv_noticia_foto) ImageView ivImatge;
    @BindView(R.id.tv_titol_noticia) TextView tvTitol;
    @BindView(R.id.tv_descripcio) TextView tvDescripcio;
    @BindView(R.id.tv_data_publicacio) TextView tvDataPublicacio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
    }

}


