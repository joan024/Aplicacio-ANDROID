package Adapter.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.ArrayList;

import Adapter.tappingandroid.Adapter.NoticiaAdapter;
import Adapter.tappingandroid.Dades.Noticia;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetallsNoticia extends AppCompatActivity {
    @BindView(R.id.iv_tornar)
    ImageView ivTornar;
    @BindView(R.id.iv_noticia_foto) ImageView ivImatge;
    @BindView(R.id.tv_titol_noticia)
    TextView tvTitol;
    @BindView(R.id.tv_descripcio) TextView tvDescripcio;
    @BindView(R.id.tv_data_inici) TextView tvDataIni;
    @BindView(R.id.tv_data_fi) TextView tvDataFi;
    @BindView(R.id.tv_data_publicacio) TextView tvDataPublicacio;
    @BindView(R.id.recyclerview_noticia)
    RecyclerView recyclerViewNoticia;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);
        ButterKnife.bind(this);

        View ivTornar = findViewById(R.id.iv_tornar);
        ivTornar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        String miValor = extra.getString("noticia");


        recyclerViewNoticia.setLayoutManager(new LinearLayoutManager(this));


        //recyclerViewNoticia.setAdapter(new NoticiaAdapter((ArrayList<Noticia>) noticia.getNoticies()));
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

}
