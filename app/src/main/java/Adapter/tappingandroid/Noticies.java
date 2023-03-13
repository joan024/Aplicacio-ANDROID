package Adapter.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.tappingandroid.Adapter.LocalAdapter;
import Adapter.tappingandroid.Adapter.NoticiaAdapter;
import Adapter.tappingandroid.Dades.Local;
import Adapter.tappingandroid.Dades.Noticia;

public class Noticies extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Noticia adaptador;
    private ArrayList<Noticia> noticias;
    private ImageView iv_tornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_noticia);

        Intent intent = new Intent(this, DetallsNoticia.class);

        startActivity(intent);

    }

    public void llenarNoticia(){
        adaptador = new Noticia(R.drawable.logotiptapping,"","","","","",noticias);
    }


}
