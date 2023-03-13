package com.example.tappingandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.material.navigation.NavigationView;

import Adapter.NoticiaAdapter;
import GestioDeRegistres.IniciSessio;
import GestioDeRegistres.Registre;

public class Inici extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private ImageView logoImatge;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inici);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(findViewById(R.id.toolbar));
        this.setTitle("");

        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String usuari = intent.getStringExtra("usuari");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //SEARCH VIEW
        SearchView searchView = findViewById(R.id.sv_busqueda);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    private void setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        drawerLayout.addDrawerListener((DrawerLayout.DrawerListener) this);

        setupNavigationView();
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

        setupHeader();
    }

    private void setupHeader() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int titleId = getTitle(menuItem);
        showFragment(titleId);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private int getTitle(@NonNull MenuItem menuItem) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft=  fm.beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.btn_dades:
                ft.replace(R.id.navigation_view,new Fragment()).commit();
            case R.id.btn_preferits:
                ft.replace(R.id.navigation_view,new Fragment()).commit();
            case R.id.btn_descompte:
                ft.replace(R.id.navigation_view,new Fragment()).commit();
            case R.id.btn_preguntes:
                ft.replace(R.id.navigation_view,new Fragment()).commit();
            case R.id.btn_contacte:
                ft.replace(R.id.navigation_view,new Fragment()).commit();
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
    }

    private void showFragment(@StringRes int titleId) {
        Fragment fragment = HomeFragment.newInstance(titleId);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(androidx.navigation.ui.R.anim.nav_default_enter_anim, androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .replace(R.id.navigation_view, fragment)
                .commit();

        setTitle(getString(titleId));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = null;

        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
        }
}
