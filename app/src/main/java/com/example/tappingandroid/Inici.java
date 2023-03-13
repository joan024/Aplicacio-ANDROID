package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
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

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Inici extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    private DrawerLayout drawerLayout;
    private ImageView logoImatge;
    private SearchView searchView;
    private Toolbar toolbar;
    private int usuari;
    //btnDades, btnPreferits, btnDescompte, btnNoticies, btnLocals, btnTapes, btnComentaris, btnXat;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Aquí establecemos el icono de hamburguesa
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        // Obtenemos el botón de hamburguesa de la Toolbar
        configurarDrawerToggle();
        usuari=2;

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

            switch (item.getItemId()) {
                case R.id.btn_dades:
                    intent = new Intent(getApplicationContext(), LesMevesDades.class);
                    startActivity(intent);
                    break;
                case R.id.btn_preferits:
                    intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                    break;
                case R.id.btn_descompte:
                    intent = new Intent(getApplicationContext(), ElsMeusDescomptes.class);
                    break;
                case R.id.btn_noticies:
                    intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                    break;
                case R.id.btn_preguntes:
                    intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                    break;
                case R.id.btn_contacte:
                    intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                    break;
                case R.id.btn_locals:
                    intent = new Intent(getApplicationContext(), ElsMeusLocals.class);
                    break;
                case R.id.btn_tapes:
                    intent = new Intent(getApplicationContext(), ElsMeusLocals.class);
                    break;
                case R.id.btn_comentaris:
                    intent = new Intent(getApplicationContext(), Comentaris.class);
                    break;
                case R.id.btn_xat:
                    intent = new Intent(getApplicationContext(), Chat.class);
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
        // Aquí se ejecuta la búsqueda cuando se pulsa Enter
        // Enviamos los resultados a la siguiente actividad
        Intent intent = new Intent(Inici.this, Resultats.class);
        intent.putExtra("filtre", filtre);
        startActivity(intent);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Aquí se ejecuta la búsqueda en tiempo real mientras se va escribiendo
        return false;
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

    @Override
    public void onClick(View v) {

    }

    private void configurarDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

}
