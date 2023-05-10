package com.example.tappingandroid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.sql.SQLException;
import java.util.Objects;

public class Inici extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    // Declaració de variables
    private DrawerLayout drawerLayout;
    private SearchView searchView;
    private TextView textHeader;
    private Toolbar toolbar;
    private int usuari, id;
    private String nom, correu;
    private MenuItem menuItemFavorits, menuItemLesMevesDades,menuItemDescomptes,menuItemXat, menuItemClose, menuItemLogin, menuItemComentaris, menuItemTapes, menuItemLocals;
    private SharedPreferences sharedPreferences;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);

        final int REQUEST_CODE = 1;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        // S'obtenen les referències als elements del layout
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // S'estableix la icona d'hamburguesa
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        configurarDrawerToggle();

        // S'obté la referència al NavigationView i del ViewHeader
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        textHeader = headerView.findViewById(R.id.header_title);

        //S'obté la referencia del menu
        Menu menu = navigationView.getMenu();
        findItemsMenu(menu);

        Intent intentUsuari = getIntent();
        nom = intentUsuari.getStringExtra("nom");

        if (nom != null) {
            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
            editor.putBoolean("session_active", true);
            editor.apply();
        }

        //Mirem si hi ha una sessió activa
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean sessionActive = sharedPreferences.getBoolean("session_active", false); // false es el valor predeterminat si la clau no existeix

        //Gestionem la visibilitat del menu
        try {
            VisibilitatMenu(sessionActive);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Gestionem el menu
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;
            Context context = getApplicationContext();
            intent= Utilitats.gestioDeMenu(item,intent, nom, context, correu);

            startActivity(intent);
            return true;
        });

        //Gestionem el SearchView
        searchView = findViewById(R.id.sv_busqueda);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Locals, ubicacions, tapes, categories");
    }

    //Funcio per agafar els items del menu
    private void findItemsMenu(Menu menu) {
        menuItemLogin = menu.findItem(R.id.btn_login);
        menuItemClose = menu.findItem(R.id.btn_close);
        menuItemLocals = menu.findItem(R.id.btn_locals);
        menuItemTapes = menu.findItem(R.id.btn_tapes);
        menuItemComentaris = menu.findItem(R.id.btn_comentaris);
        menuItemXat = menu.findItem(R.id.btn_xat);
        menuItemLesMevesDades = menu.findItem(R.id.btn_dades);
        menuItemDescomptes = menu.findItem(R.id.btn_descompte);
        menuItemFavorits = menu.findItem(R.id.btn_preferits);
    }

    // Mètode per canviar la visibilitat del menú segons si hi ha una sessió iniciada o no
    @SuppressLint("SetTextI18n")
    private void VisibilitatMenu(boolean sessionActive) throws SQLException {
        if (sessionActive) {
            // La sesión está iniciada, realiza alguna acción
            id = sharedPreferences.getInt("id", 0);

            usuari = Utilitats.getTipusUsuari(id);

            nom = sharedPreferences.getString("nom", "");
            correu = sharedPreferences.getString("correu", "");

            textHeader.setText("Hola, " + nom);

            //GENERAL
            menuItemLogin.setVisible(false);
            menuItemClose.setVisible(true);
            if(usuari == 2){
                //LOCALS
                Utilitats.visibilitatLocals(menuItemFavorits, menuItemDescomptes, menuItemLesMevesDades, menuItemXat, menuItemLocals, menuItemTapes, menuItemComentaris);
            }else{
                //CONSUMIDOR
                Utilitats.visibilitatConsumidors(menuItemFavorits, menuItemDescomptes, menuItemLesMevesDades, menuItemXat, menuItemLocals, menuItemTapes, menuItemComentaris);
            }
        } else {
            //Sense iniciar sessió per defecte veura la pantalla de consumidor
            menuItemLogin.setVisible(true);
            menuItemClose.setVisible(false);
            Utilitats.visibilitatConsumidors(menuItemFavorits, menuItemDescomptes, menuItemLesMevesDades, menuItemXat, menuItemLocals, menuItemTapes, menuItemComentaris);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String filtre) {
        // Aquí s'executa la cerca quan es prem Enter
        // Enviem els resultats a la següent activitat
        Intent intent = new Intent(Inici.this, Resultats.class);
        intent.putExtra("filtre", filtre);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        //Primer verifica si el menú lateral és obert, i si és així, el tanca. Si no, truca al mètode de la superclasse per manejar l'esdeveniment d'entrada.
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

    @Override
    public void onClick(View v) {
    }

    // Mètode per configurar el botó d'hamburguesa
    private void configurarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}