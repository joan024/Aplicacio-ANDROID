package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.Menu;

import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.example.tappingandroid.GestioDeRegistres.IniciSessio;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Inici extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    // Declaració de variables
    private DrawerLayout drawerLayout;
    private ImageView logoImatge;
    private SearchView searchView;
    private Toolbar toolbar;
    private int usuari;
    //btnDades, btnPreferits, btnDescompte, btnNoticies, btnLocals, btnTapes, btnComentaris, btnXat;


    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);

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
        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;

            /*if (usuari==1){
                btnDades.setVisibility(View.VISIBLE);
                btnPreferits.setVisibility(View.VISIBLE);
                btnDescompte.setVisibility(View.VISIBLE);
                btnLocals.setVisibility(View.GONE);
                btnTapes.setVisibility(View.GONE);
                btnXat.setVisibility(View.GONE);
                btnComentaris.setVisibility(View.GONE);
            }else if(usuari == 2){
                btnDades.setVisibility(View.GONE);
                btnPreferits.setVisibility(View.GONE);
                btnDescompte.setVisibility(View.GONE);
                btnLocals.setVisibility(View.VISIBLE);
                btnTapes.setVisibility(View.VISIBLE);
                btnXat.setVisibility(View.VISIBLE);
                btnComentaris.setVisibility(View.VISIBLE);
            }*/

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
                    intent = new Intent(getApplicationContext(), Contacta.class);
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
        String usuari = intent.getStringExtra("usuari");


        searchView = findViewById(R.id.sv_busqueda);
        searchView.setOnQueryTextListener(this);
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

    private void configurarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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
