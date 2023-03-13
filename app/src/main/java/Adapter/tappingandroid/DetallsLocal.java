package Adapter.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.tappingandroid.Adapter.TapasAdapter;
import Adapter.tappingandroid.Dades.Local;

import com.example.tappingandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.tappingandroid.Dades.Tapa;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetallsLocal extends AppCompatActivity {

    @BindView(R.id.iv_tornar) ImageView ivTornar;
    @BindView(R.id.iv_imatge_local) ImageView ivImatge;
    @BindView(R.id.tv_local_nom) TextView tvNomLocal;
    @BindView(R.id.tv_ubicacio) TextView tvUbicacio;
    @BindView(R.id.tv_telefon) TextView tvTelefon;
    @BindView(R.id.tv_horari) TextView tvHorari;
    @BindView(R.id.tv_puntuacio) TextView tvPuntuacio;
    @BindView(R.id.tv_descripcio) TextView tvDescripcio;
    @BindView(R.id.recyclerview_tapes) RecyclerView recyclerViewTapes;

    @Override
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalls_local);
        ButterKnife.bind(this);

        ivTornar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Rebem les dades del local
        Intent intent = getIntent();
        Local local = (Local) intent.getSerializableExtra("local");

        tvNomLocal.setText(local.getNom());
        ivImatge.setImageResource(local.getFoto());
        tvUbicacio.setText(local.getUbicacio());
        tvTelefon.setText(local.getTelefon());
        tvHorari.setText(local.getHorari());
        tvPuntuacio.setText(local.getPuntuacio() + "");
        tvDescripcio.setText(local.getDescripcio());

        // Establecer el diseño del RecyclerView
        recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));

        // Establecer el adaptador del RecyclerView
        recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}
