package Adapter.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import java.util.List;

import Adapter.tappingandroid.Adapter.LocalAdapter;
import Adapter.tappingandroid.Dades.Local;
import Adapter.tappingandroid.Dades.Tapa;

import com.example.tappingandroid.R;

public class Resultats extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView logoImatge;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));
        this.setTitle("");

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String query = intent.getStringExtra("filtre");
        //SEARCH VIEW
        SearchView searchView = findViewById(R.id.sv_busqueda);
        // Establecer el texto de búsqueda guardado en la variable searchText
        searchView.setQuery(query, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String filtre) {
                // Aquí se ejecuta la búsqueda cuando se pulsa Enter
                // Enviamos los resultados a la siguiente actividad
                Intent intent = new Intent(Resultats.this, Resultats.class);
                intent.putExtra("filtre", filtre);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aquí se ejecuta la búsqueda en tiempo real mientras se va escribiendo
                return false;
            }
            });

        // Obtener referencia a la RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_locals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Crear una lista de objetos Local (asumiendo que tienes una lista con los datos)
        List<Local> llistaLocals = obtenirLlistaLocals() ;

        // Crear una instancia del adaptador y asignarlo a la RecyclerView
        LocalAdapter adaptador = new LocalAdapter((ArrayList<Local>) llistaLocals);
        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new LocalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Obtener el objeto Local en la posición seleccionada
                Local localSeleccionado = llistaLocals.get(position);

                // Crear un Intent para abrir la actividad LocalDetail y pasar la información del local seleccionado
                Intent intent = new Intent(Resultats.this, DetallsLocal.class);
                intent.putExtra("local", localSeleccionado);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public List<Local> obtenirLlistaLocals() {
        List<Local> locals = new ArrayList<>();
        List <Tapa> tapes = obternirTapes();
        locals.add(new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes ));
        locals.add(new Local(R.drawable.logotiptapping, "Segon local", "C/Un carrer", "14:00-20:00", 9.2,"696456789", "Local inovador.", tapes));
        locals.add(new Local(R.drawable.logotiptapping, "Tercer local", "C/Un altre carrer", "18:00-22:00", 7.8, "659673959","El restaurant \"La Cuina del Mar\" és un lloc acollidor i elegant ubicat al centre de la ciutat. La decoració és d'estil marí, amb parets de rajoles blaves i blanques que recorden l'oceà i els detalls de fusta que evoquen l'ambient d'un vaixell.", tapes));
        // Agrega más objetos Local según sea necesario
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

}