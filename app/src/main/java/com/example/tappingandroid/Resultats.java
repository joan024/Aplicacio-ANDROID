package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.google.android.material.navigation.NavigationView;

public class Resultats extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    List<Local> locals = null;
    List<Tapa> tapes = null;
    List<Opinio> opinions = null;
    Connection conexio;
    private TextView textHeader;
    private String nom,correu;
    private int id,usuari;
    private MenuItem menuItemFavorits, menuItemLesMevesDades,menuItemDescomptes,menuItemXat, menuItemClose, menuItemLogin, menuItemComentaris, menuItemTapes, menuItemLocals;
    private SharedPreferences sharedPreferences;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

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

        Intent intent = getIntent();
        String query = intent.getStringExtra("filtre");

        //SEARCH VIEW
        SearchView searchView = findViewById(R.id.sv_busqueda);
        // Establir el text de cerca desat a la variable searchText
        searchView.setQuery(query, false);
        searchView.setQueryHint("Locals, ubicacions, tapes, categories");
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

        locals = new ArrayList<>();

        // Obtenir referència a la RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_locals);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            obtenirLlistaLocals(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Crear una instància de l'adaptador i assignar-lo a la RecyclerView
        LocalAdapter adaptador = new LocalAdapter((ArrayList<Local>) locals);
        recyclerView.setAdapter(adaptador);

        List<Local> finalLlistaLocals = locals;
        adaptador.setOnItemClickListener(position -> {
            // Obtenir l'objecte local a la posició seleccionada
            Local localSeleccionado = finalLlistaLocals.get(position);

            // Crear un Intent per obrir l'activitat LocalDetail i passar la informació del local seleccionat
            Intent intent1 = new Intent(Resultats.this, DetallsLocal.class);
            intent1.putExtra("local", localSeleccionado);
            startActivity(intent1);
        });
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

    private void obtenirLlistaLocals(String filtreDeCerca) throws SQLException {
        conexio = ConexioBD.CONN();

        String sql = "SELECT * FROM local WHERE nom LIKE '%" + filtreDeCerca + "%' OR direccio LIKE '%" + filtreDeCerca + "%' OR descripcio LIKE '%" + filtreDeCerca + "%'";

        Statement stmt = null;
        Statement stmt6 = null;
        ResultSet rs = null;
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);
            Statement stmt2 = conexio.createStatement();
            Statement stmt3 = conexio.createStatement();
            Statement stmt4 = conexio.createStatement();
            Statement stmt5 = conexio.createStatement();
            stmt6 = conexio.createStatement();

            while(rs.next()){
                int id_local = rs.getInt("id");
                int id_horari = rs.getInt("id_horari");

                String horari = Utilitats.queryHorari(stmt2, id_horari);
                tapes = Utilitats.queryTapes(stmt3, tapes, id_local);
                opinions = Utilitats.queryOpinions(stmt4, opinions, id_local);
                String link_foto = Utilitats.queryFotoPrincipal(stmt5, id_local);

                Double mitjana= Utilitats.calcularMitjanaPuntuacio(opinions);

                locals.add(new Local(id_local,link_foto,rs.getString("nom"),rs.getString("direccio"),horari,mitjana,rs.getString("telefon"),rs.getString("descripcio"),tapes,opinions));
            }

            buscarPerTapes(locals,stmt6,stmt3,stmt2, stmt4, stmt5, filtreDeCerca);

            buscarPerCategories(locals,stmt6,stmt3,stmt2, stmt4, stmt5, filtreDeCerca);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conexio.close();
    }

    private void comprovarFiles(ResultSet rs, Statement stmt2, Statement stmt3,Statement stmt4, Statement stmt5) throws SQLException {
        // Creem un objecte Local per cada fila del resultat i l'afegim a la llista locals si no hi és ja
        while (rs.next()) {
            int id = rs.getInt("id");
            boolean localJaAfegit = false;
            for (Local local : locals) {
                if (local.getId() == id) {
                    localJaAfegit = true;
                    break;
                }
            }
            if (!localJaAfegit) {
                String link_foto = Utilitats.queryFotoPrincipal(stmt5, id);
                tapes = Utilitats.queryTapes(stmt3, tapes, id);
                opinions = Utilitats.queryOpinions(stmt4, opinions, id);
                String horari = Utilitats.queryHorari(stmt2, id);

                Double mitjana= Utilitats.calcularMitjanaPuntuacio(opinions);
                Local local = new Local(id, link_foto,rs.getString("nom"), rs.getString("direccio"),horari, mitjana,rs.getString("telefon"),rs.getString("descripcio"), tapes, opinions);
                locals.add(local);
            }
        }
    }

    private void buscarPerCategories(List<Local> locals, Statement stmt6, Statement stmt3, Statement stmt2, Statement stmt4, Statement stmt5, String query) throws SQLException {

        // Consulta SQL per obtenir els locals que tenen tapes que pertanyen a categories que contenen la query
        String consultaLocalsAmbTapes = "SELECT l.* FROM local l, tapa t, local_tapa tl, categoria c, categoria_tapa ct WHERE c.nom LIKE '%" + query + "%' AND t.id = tl.id_tapa AND l.id = tl.id_local AND c.id= ct.id_categoria AND t.id = ct.id_tapa";
        ResultSet rs6 = stmt6.executeQuery(consultaLocalsAmbTapes);

        comprovarFiles(rs6, stmt2, stmt3, stmt4, stmt5);
    }


        private void buscarPerTapes(List<Local> locals, Statement stmt6, Statement stmt3, Statement stmt2, Statement stmt4, Statement stmt5, String query) throws SQLException {

        // Consulta SQL per obtenir els locals que tenen tapes que contenen la query
        String consultaLocalsAmbTapes = "SELECT l.* FROM local l, tapa t, local_tapa tl WHERE t.nom LIKE '%" + query + "%' AND t.id = tl.id_tapa AND l.id = tl.id_local";
        ResultSet rs6 = stmt6.executeQuery(consultaLocalsAmbTapes);

        comprovarFiles(rs6, stmt2, stmt3, stmt4, stmt5);
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
}