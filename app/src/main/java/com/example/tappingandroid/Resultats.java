package com.example.tappingandroid;

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

import Adapter.LocalAdapter;
import Dades.Local;

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

        //TextView textView = findViewById(R.id.tv_resultatProva);
        //textView.setText(query);

        // Obtener referencia a la RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_locals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Crear una lista de objetos Local (asumiendo que tienes una lista con los datos)
        List<Local> llistaLocals = obtenirLlistaLocals() ;

        // Crear una instancia del adaptador y asignarlo a la RecyclerView
        LocalAdapter adaptador = new LocalAdapter((ArrayList<Local>) llistaLocals);
        recyclerView.setAdapter(adaptador);

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
        locals.add(new Local(R.drawable.logotiptapping, "El mos", "C/Pepito", "12:00-15:00", 8.4));
        locals.add(new Local(R.drawable.logotiptapping, "Otro local", "C/Otra dirección", "14:00-20:00", 9.2));
        locals.add(new Local(R.drawable.logotiptapping, "Tercer local", "C/Una calle", "18:00-22:00", 7.8));
        // Agrega más objetos Local según sea necesario
        return locals;
    }

}