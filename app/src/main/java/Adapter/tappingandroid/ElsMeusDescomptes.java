package Adapter.tappingandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Adapter.tappingandroid.Adapter.DescomptesAdapter;
import Adapter.tappingandroid.Dades.Descompte;

import com.example.tappingandroid.R;

import java.text.ParseException;
import java.util.ArrayList;

public class ElsMeusDescomptes extends AppCompatActivity {

    private ArrayList<Descompte> descomptes;
    private RecyclerView recyclerView;
    private ImageView iv_tornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_els_meus_descomptes);

        iv_tornar = findViewById(R.id.iv_tornar);
        iv_tornar.setOnClickListener(v -> onBackPressed());

        // Inicializar la lista de descomptes
        descomptes = new ArrayList<>();
        try {
            //Agregar nuevos descomptes a la lista
            descomptes.add(new Descompte("FG4221FF", "10% en totes les tapes i begudes alcoholiques amb gel i llimona", "10/10/2023", "10/03/2023", 1));
            descomptes.add(new Descompte("FG4211SR", "20% en totes les begudes sense alcohol", "15/06/2023", "31/03/2023", 2));
            descomptes.add(new Descompte("FE8271GH", "2x1 en plats principals", "14/05/2023", "30/04/2023", 3));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Configurar el RecyclerView i l'adaptador
        recyclerView = findViewById(R.id.recycler_descuentos);
        configurarRecyclerView();
    }

    private void configurarRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DescomptesAdapter(descomptes) {
            @Override
            public void onDescompteClick(Descompte descompte) {
                // Acció a realitzar en fer clic en un descompte
                Toast.makeText(ElsMeusDescomptes.this, descompte.getCodi(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Configurar el botó d'entrada a la barra d'acció
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
