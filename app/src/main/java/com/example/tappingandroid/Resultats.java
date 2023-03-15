package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.example.tappingandroid.GestioDeRegistres.IniciSessio;
import com.google.android.material.navigation.NavigationView;

public class Resultats extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageView logoImatge;
    private Toolbar toolbar;
    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // S'estableix la icona d'hamburguesa
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        // S'obté el DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        // Es configura el botó d'hamburguesa de la Toolbar
        configurarDrawerToggle();

        // S'obté la referència al NavigationView i es configura el Listener
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;

            // S'obtenen les llistes de Tapa i Opinió
            List<Tapa> tapes = obternirTapes();
            List <Opinio> opinions = null;
            try {
                opinions = obternirOpinions();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // S'executa una acció segons el botó premut del menú Drawer
            switch (item.getItemId()) {
                case R.id.btn_dades:
                    intent = new Intent(getApplicationContext(), LesMevesDades.class);
                    break;
                case R.id.btn_preferits:
                    intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                    break;
                case R.id.btn_descompte:
                    intent = new Intent(getApplicationContext(), ElsMeusDescomptes.class);
                    break;
                case R.id.btn_noticies:
                    intent = new Intent(getApplicationContext(), Noticies.class);
                    break;
                case R.id.btn_preguntes:
                    intent = new Intent(getApplicationContext(), PreguntesFrequents.class);
                    break;
                case R.id.btn_contacte:
                    intent = new Intent(getApplicationContext(), Contacte.class);
                    break;
                case R.id.btn_locals:
                    intent = new Intent(getApplicationContext(), ElsMeusLocals.class);
                    break;
                case R.id.btn_tapes:
                    // Es passa la informació del local a mostrar a l'activitat LesMevesTapes
                    intent = new Intent(getApplicationContext(), LesMevesTapes.class);
                    intent.putExtra("local",new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions ));
                    break;
                case R.id.btn_comentaris:
                    intent = new Intent(getApplicationContext(), Comentaris.class);
                    break;
                case R.id.btn_xat:
                    intent = new Intent(getApplicationContext(), Chat.class);
                    break;
                case R.id.btn_login:
                    intent = new Intent(getApplicationContext(), IniciSessio.class);
                    break;
                case R.id.btn_close:
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                    editor.putBoolean("session_active", false);
                    editor.apply();
                    intent = new Intent(getApplicationContext(), Inici.class);
                    break;
            }
            startActivity(intent);
            return true;
        });

        Intent intent = getIntent();
        String query = intent.getStringExtra("filtre");
        //SEARCH VIEW
        SearchView searchView = findViewById(R.id.sv_busqueda);
        // Establir el text de cerca desat a la variable searchText
        searchView.setQuery(query, false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String filtre) {
                // Aquí s'executa la cerca quan es prem Enter
                // Enviem els resultats a la següent activitat
                Intent intent = new Intent(Resultats.this, Resultats.class);
                intent.putExtra("filtre", filtre);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aquí s'executa la cerca en temps real mentre es va escrivint
                return false;
            }
            });

        // Obtenir referència a la RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_locals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Crear una llista d'objectes local
        List<Local> llistaLocals = null;
        try {
            llistaLocals = obtenirLlistaLocals();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Crear una instància de l'adaptador i assignar-lo a la RecyclerView
        LocalAdapter adaptador = new LocalAdapter((ArrayList<Local>) llistaLocals);
        recyclerView.setAdapter(adaptador);

        List<Local> finalLlistaLocals = llistaLocals;
        adaptador.setOnItemClickListener(position -> {
            // Obtenir l'objecte local a la posició seleccionada
            Local localSeleccionado = finalLlistaLocals.get(position);

            // Crear un Intent per obrir l'activitat LocalDetail i passar la informació del local seleccionat
            Intent intent1 = new Intent(Resultats.this, DetallsLocal.class);
            intent1.putExtra("local", localSeleccionado);
            startActivity(intent1);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    private void configurarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    public List<Local> obtenirLlistaLocals() throws ParseException {
        List<Local> locals = new ArrayList<>();
        List <Tapa> tapes = obternirTapes();
        List <Opinio> opinions = obternirOpinions();
        locals.add(new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions ));
        locals.add(new Local(R.drawable.logotiptapping, "Segon local", "C/Un carrer", "14:00-20:00", 9.2,"696456789", "Local inovador.", tapes, opinions));
        locals.add(new Local(R.drawable.logotiptapping, "Tercer local", "C/Un altre carrer", "18:00-22:00", 7.8, "659673959","El restaurant \"La Cuina del Mar\" és un lloc acollidor i elegant ubicat al centre de la ciutat. La decoració és d'estil marí, amb parets de rajoles blaves i blanques que recorden l'oceà i els detalls de fusta que evoquen l'ambient d'un vaixell.", tapes, opinions));
        return locals;
    }

    private List<Opinio> obternirOpinions() throws ParseException {
        List <Opinio> opinions = new ArrayList<>();
        opinions.add(new Opinio("Juan","12/02/2022","Aquest local es top.",7.8));
        opinions.add(new Opinio("Maria","01/03/2022","Tornare a prendre unes braves segur.",9.2));
        opinions.add(new Opinio("Lluc","03/02/2022","No crec que hi torni, personal desagradable.",4.5));
        return opinions;
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