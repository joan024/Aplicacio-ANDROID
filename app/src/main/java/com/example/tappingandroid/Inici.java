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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.example.tappingandroid.GestioDeRegistres.IniciSessio;
import com.google.android.material.navigation.NavigationView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.transform.Result;

public class Inici extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    // Declaració de variables
    private DrawerLayout drawerLayout;
    private ImageView logoImatge;
    private SearchView searchView;
    private TextView textHeader;
    private Toolbar toolbar;
    private int usuari, id;
    private String nom, correu;
    private MenuItem menuItemFavorits, menuItemLesMevesDades,menuItemDescomptes,menuItemXat, menuItemClose, menuItemLogin, menuItemComentaris, menuItemTapes, menuItemLocals;
    private SharedPreferences sharedPreferences;
    String[] opcionesBusqueda;
    Connection conexio;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String a = "";
        opcionesBusqueda = new String[]{"Restaurant", "Ubicació", "Tapa", "Categoria"};
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
        usuari = 2;

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
        menuItemLesMevesDades = menu.findItem(R.id.btn_dades);
        menuItemDescomptes = menu.findItem(R.id.btn_descompte);
        menuItemFavorits = menu.findItem(R.id.btn_preferits);

        textHeader = headerView.findViewById(R.id.header_title);
        if (nom != null) {
            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
            editor.putBoolean("session_active", true);
            editor.apply();
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean sessionActive = sharedPreferences.getBoolean("session_active", false); // false es el valor predeterminado si la clave no existe
        try {
            VisibilitatMenu(sessionActive);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            Intent intent = null;

            // S'executa una acció segons el botó premut del menú Drawer
            switch (item.getItemId()) {
                case R.id.btn_dades:
                    if (nom != null) {
                        intent = new Intent(getApplicationContext(), LesMevesDades.class);
                        intent.putExtra("usuari", correu);
                    } else {
                        intent = new Intent(getApplicationContext(), IniciSessio.class);
                    }
                    break;
                case R.id.btn_preferits:
                    if (nom != null) {
                        intent = new Intent(getApplicationContext(), ElsMeusFavorits.class);
                        intent.putExtra("usuari", correu);
                    } else {
                        intent = new Intent(getApplicationContext(), IniciSessio.class);
                    }
                    break;
                case R.id.btn_descompte:
                    if (nom != null) {
                        intent = new Intent(getApplicationContext(), ElsMeusDescomptes.class);
                        intent.putExtra("usuari", correu);
                    } else {
                        intent = new Intent(getApplicationContext(), IniciSessio.class);
                    }
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
    @SuppressLint("SetTextI18n")
    private void VisibilitatMenu(boolean sessionActive) throws SQLException {
        if (sessionActive) {
            // La sesión está iniciada, realiza alguna acción
            id = sharedPreferences.getInt("id", 0);

            usuari = getTipusUsuari(id);

            nom = sharedPreferences.getString("nom", "");
            correu = sharedPreferences.getString("correu", "");

            textHeader.setText("Hola, " + nom);

            //GENERAL
            menuItemLogin.setVisible(false);
            menuItemClose.setVisible(true);
            if(usuari == 2){
                //LOCALS
                visibilitatLocals();
            }else{
                //CONSUMIDOR
                visibilitatConsumidors();
            }
        } else {
            //Sense iniciar sessió per defecte veura la pantalla de consumidor
            menuItemLogin.setVisible(true);
            menuItemClose.setVisible(false);
            visibilitatConsumidors();
        }
    }

    private void visibilitatConsumidors() {
        menuItemLocals.setVisible(false);
        menuItemTapes.setVisible(false);
        menuItemComentaris.setVisible(false);
        menuItemXat.setVisible(false);
        menuItemLesMevesDades.setVisible(true);
        menuItemDescomptes.setVisible(true);
        menuItemFavorits.setVisible(true);
    }

    private void visibilitatLocals() {
        menuItemLocals.setVisible(true);
        menuItemTapes.setVisible(true);
        menuItemComentaris.setVisible(true);
        menuItemXat.setVisible(true);
        menuItemLesMevesDades.setVisible(false);
        menuItemDescomptes.setVisible(false);
        menuItemFavorits.setVisible(false);
    }

    private int getTipusUsuari(int id) throws SQLException {
        conexio = ConexioBD.CONN();
        String sql = "SELECT * FROM local WHERE id_usuari=" + id;
        Statement stmt = null;
        ResultSet rs = null;
        boolean esLocal = false;

        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                esLocal = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conexio.close();
        if (esLocal) {
            return 2;
        } else {
            return 1;
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

        // Hacer una consulta en la base de datos o en algún otro origen de datos para obtener los resultados relevantes
        List<String> results = null;
        try {
            results = queryResultsFromDatabase(newText);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mostrar los resultados en una vista, como una lista o un recyclerview

        return false;
    }



    private List<String> queryResultsFromDatabase(String newText) throws SQLException {
        List<String> results = new ArrayList<>();
        Connection conexio = ConexioBD.CONN();
        String query = "SELECT * FROM local WHERE nom LIKE '%"+newText+"%'";
        Statement statement = conexio.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String result = resultSet.getString("nom");
            results.add(result);
        }

        resultSet.close();
        statement.close();
        conexio.close();
        return results;
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