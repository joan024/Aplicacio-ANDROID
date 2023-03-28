package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.example.tappingandroid.GestioDeRegistres.IniciSessio;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inici extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    // Declaració de variables
    private DrawerLayout drawerLayout;
    private ImageView logoImatge;
    private SearchView searchView;
    private TextView textHeader;
    private Toolbar toolbar;
    private int usuari,id;
    private String nom,correu;
    private MenuItem menuItemXat, menuItemClose,menuItemLogin,menuItemComentaris, menuItemTapes, menuItemLocals;
    private SharedPreferences sharedPreferences;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String a="";
        setContentView(R.layout.activity_inici);

        // Recibe el intent
        Intent intentUsuari = getIntent();

        // Obtiene el valor del String con la clave "usuari"
        correu = intentUsuari.getStringExtra("usuari");
        nom = intentUsuari.getStringExtra("nom");


        // S'obtenen les referències als elements del layout
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
        // S'estableix l'identificador de l'usuari
        usuari=2;

        // S'obté la referència al NavigationView i es configura el Listener
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        menuItemLogin = menu.findItem(R.id.btn_login);
        menuItemClose = menu.findItem(R.id.btn_close);
        menuItemLocals = menu.findItem(R.id.btn_locals);
        menuItemTapes = menu.findItem(R.id.btn_tapes);
        menuItemComentaris = menu.findItem(R.id.btn_comentaris);
        menuItemXat = menu.findItem(R.id.btn_xat);

        textHeader = headerView.findViewById(R.id.header_title);
        if(nom != null){
            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
            editor.putBoolean("session_active", true);
            editor.apply();
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean sessionActive = sharedPreferences.getBoolean("session_active", false); // false es el valor predeterminado si la clave no existe
        VisibilitatMenu(sessionActive);


        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;

            // S'executa una acció segons el botó premut del menú Drawer
            switch (item.getItemId()) {
                case R.id.btn_dades:
                    if(nom!=null){
                        intent = new Intent(getApplicationContext(), LesMevesDades.class);
                        intent.putExtra("usuari", correu);
                    }else{
                        intent = new Intent(getApplicationContext(), IniciSessio.class);
                    }
                    break;
                case R.id.btn_preferits:
                    if(nom!=null){
                        intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                        intent.putExtra("usuari", correu);
                    }else{
                        intent = new Intent(getApplicationContext(), IniciSessio.class);
                    }                    break;
                case R.id.btn_descompte:
                    if(nom!=null){
                        intent = new Intent(getApplicationContext(), ElsMeusDescomptes.class);
                        intent.putExtra("usuari", correu);
                    }else{
                        intent = new Intent(getApplicationContext(), IniciSessio.class);
                    }   break;
                case R.id.btn_noticies:
                    intent = new Intent(getApplicationContext(), Noticies.class);
                    break;
                case R.id.btn_preguntes:
                    intent = new Intent(getApplicationContext(), PreguntesFrequents.class);
                    break;
                case R.id.btn_contacte:
                    intent = new Intent(getApplicationContext(), Contacta.class);
                    break;
                case R.id.btn_locals:
                    intent = new Intent(getApplicationContext(), ElsMeusLocals.class);
                    break;
                case R.id.btn_tapes:
                    // Es passa la informació del local a mostrar a l'activitat LesMevesTapes
                    intent = new Intent(getApplicationContext(), LesMevesTapes.class);
                    //intent.putExtra("local",new Local(R.drawable.logotiptapping, "Primer local", "C/pepito","12:00-15:00", 8.4, "616638823", "Local on oferim pastes i entrepans fets a casa.",tapes, opinions ));
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
                    editor.remove("id");
                    editor.remove("nom");
                    editor.apply();
                    intent = new Intent(getApplicationContext(), Inici.class);
                    break;
            }
            startActivity(intent);
            return true;
        });

        searchView = findViewById(R.id.sv_busqueda);
        searchView.setOnQueryTextListener(this);
    }

    // Mètode per canviar la visibilitat del menú segons si hi ha una sessió iniciada o no
    private void VisibilitatMenu(boolean sessionActive) {
        if(sessionActive){
            // La sesión está iniciada, realiza alguna acción
            id=sharedPreferences.getInt("id",0);

            nom = sharedPreferences.getString("nom","");
            correu = sharedPreferences.getString("correu","");

            textHeader.setText("Hola, "+nom);
            menuItemLogin.setVisible(false);
            menuItemClose.setVisible(true);
            //LOCALS
            menuItemLocals.setVisible(true);
            menuItemTapes.setVisible(true);
            menuItemComentaris.setVisible(true);
            menuItemXat.setVisible(true);
        }else{
            menuItemLogin.setVisible(true);
            menuItemClose.setVisible(false);
            menuItemLocals.setVisible(false);
            menuItemTapes.setVisible(false);
            menuItemComentaris.setVisible(false);
            menuItemXat.setVisible(false);
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
        // Aquí s'executa la cerca en temps real mentre es va escrivint
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
