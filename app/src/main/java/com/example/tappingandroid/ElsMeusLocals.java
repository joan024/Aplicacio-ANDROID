package com.example.tappingandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ElsMeusLocals extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LocalAdapter adaptador;
    private List<Local> locals;
    private ImageView iv_tornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_els_meus_favorits);

        iv_tornar = findViewById(R.id.iv_tornar);

        iv_tornar.setOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.rv_locals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            locals = getLocals(); // función que obtiene los locales favoritos de la persona
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adaptador = new LocalAdapter((ArrayList<Local>) locals); // adaptador para mostrar los locales favoritos

        adaptador.setOnItemClickListener(position -> {
            // Obtener el objeto Local en la posición seleccionada
            Local localSeleccionado = locals.get(position);

            // Crear un Intent para abrir la actividad LocalDetail y pasar la información del local seleccionado
            Intent intent = new Intent(ElsMeusLocals.this, DetallsLocal.class);
            intent.putExtra("local", localSeleccionado);
            startActivity(intent);
        });

        recyclerView.setAdapter(adaptador);

    }

    private List<Local> getLocals() throws ParseException {
        locals = new ArrayList<>();
        List <Tapa> tapes = obternirTapes();
        List <Opinio> opinions = obternirOpinions();
        locals.add(new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions));
        locals.add(new Local(R.drawable.logotiptapping, "Tercer local", "C/Un altre carrer", "18:00-22:00", 7.8, "659673959","El restaurant \"La Cuina del Mar\" és un lloc acollidor i elegant ubicat al centre de la ciutat. La decoració és d'estil marí, amb parets de rajoles blaves i blanques que recorden l'oceà i els detalls de fusta que evoquen l'ambient d'un vaixell.", tapes, opinions));

        return locals;
    }
    private List<Tapa> obternirTapes() {
        List <Tapa> tapes = new ArrayList<>();
        tapes.add(new Tapa("Braves",4.5));
        tapes.add(new Tapa("Chipirons", 6.7));
        tapes.add(new Tapa("Croquetes", 3));
        tapes.add(new Tapa("Patates fregides", 2.4));
        tapes.add(new Tapa("Truita de patates", 5.8));
        return tapes;
    }
    private List<Opinio> obternirOpinions() throws ParseException {
        List <Opinio> opinions = new ArrayList<>();
        opinions.add(new Opinio("Juan","12/02/2022","Aquest local es top.",7.8));
        opinions.add(new Opinio("Maria","01/03/2022","Tornare a prendre unes braves segur.",9.2));
        opinions.add(new Opinio("Lluc","03/02/2022","No crec que hi torni, personal desagradable.",4.5));
        return opinions;
    }
}
