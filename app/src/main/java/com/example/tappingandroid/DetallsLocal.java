package com.example.tappingandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tappingandroid.Adapter.ImatgesAdapter;
import com.example.tappingandroid.Adapter.OpinioAdapter;
import com.example.tappingandroid.Adapter.TapasAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetallsLocal extends AppCompatActivity {

    //Definim les vistes que farem servir en aquesta classe
    @BindView(R.id.iv_tornar)
    ImageView ivTornar;
    @BindView(R.id.iv_favorits)
    ImageView ivFavorits;
    @BindView(R.id.viewpager_imagenes)
    ViewPager2 vpImatge;
    @BindView(R.id.tv_local_nom)
    TextView tvNomLocal;
    @BindView(R.id.tv_ubicacio)
    TextView tvUbicacio;
    @BindView(R.id.tv_telefon)
    TextView tvTelefon;
    @BindView(R.id.tv_horari)
    TextView tvHorari;
    @BindView(R.id.tv_puntuacio)
    TextView tvPuntuacio;
    @BindView(R.id.tv_descripcio)
    TextView tvDescripcio;
    @BindView(R.id.recyclerview_tapes)
    RecyclerView recyclerViewTapes;
    @BindView(R.id.rv_opinions)
    RecyclerView recyclerViewOpinions;
    @BindView(R.id.btn_afegir_comentari)
    Button btn_comentari;

    Connection conexio;
    Statement stmt = null;
    ResultSet rs = null;
    int idLocal,id;
    boolean esFavorit = false;
    SharedPreferences sharedPreferences;

    @Override
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalls_local);
        ButterKnife.bind(this);

        //Acció en fer clic al botó "tornar"
        ivTornar.setOnClickListener(v -> onBackPressed());

        //Rebem les dades del local
        Intent intent = getIntent();
        Local local = (Local) intent.getSerializableExtra("local");
        idLocal = local.getId();

        //Assignem les dades del local a les vistes corresponents
        tvNomLocal.setText(local.getNom());
        vpImatge.setAdapter(new ImatgesAdapter(local.getFoto(),local.getId()));
        tvUbicacio.setText(local.getUbicacio());
        tvTelefon.setText(local.getTelefon());
        tvHorari.setText(local.getHorari());
        DecimalFormat df = new DecimalFormat("#.#");
        tvPuntuacio.setText(df.format(local.getPuntuacio()));
        tvDescripcio.setText(local.getDescripcio());

        //Establim el disseny del RecyclerView per a les tapes i les opinions
        recyclerViewTapes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOpinions.setLayoutManager(new LinearLayoutManager(this));

        //Establim l'adaptador per als RecyclerView de tapes i opinions
        recyclerViewTapes.setAdapter(new TapasAdapter(local.getTapes()));
        recyclerViewOpinions.setAdapter((new OpinioAdapter(local.getOpinions())));

        //Acció en fer clic al botó "afegir comentari"
        btn_comentari.setOnClickListener(v -> {
            Intent intent1 = new Intent(DetallsLocal.this, AfegirOpinio.class);
            intent1.putExtra("local", idLocal);
            startActivity(intent1);
        });

        //Comprovarem si l'usuari ha iniciat sessió, i en aquest cas si por guardar o ja te guardat el local
        try {
            comprovarFavorit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Quan fagi click al telefon, li donarà l'opció de trucar
        tvTelefon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = tvTelefon.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
    }

    // Aquest mètode comprova si el local actual és un favorit de l'usuari que l'esta consultant
    private void comprovarFavorit() throws SQLException {
        // Obtenim les dades d'inici de sessió de l'usuari
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean sessionActive = sharedPreferences.getBoolean("session_active", false); // false es el valor predeterminado si la clau no existeix
        id = sharedPreferences.getInt("id", 0);

        // Si l'usuari ha iniciat sessió i existeix un id vàlid, procedim a comprovar si el local actual és un favorit
        if(sessionActive && id != 0){
            // Establim connexió amb la base de dades
            conexio = ConexioBD.connectar();

            // Construïm la consulta SQL per obtenir si l'usuari té el local actual com a favorit
            String sql = "SELECT * FROM local " +
                    "INNER JOIN guarda ON guarda.id_local=local.id "+
                    "WHERE guarda.id_usuari = "+ id +
                    " AND guarda.data_fi IS NULL"+
                    " AND guarda.id_local = "+idLocal;

            try {
                // Executem la consulta SQL
                stmt = conexio.createStatement();
                rs = stmt.executeQuery(sql);

                // Si la consulta retorna un registre, significa que l'usuari té el local actual com a favorit
                if(rs.next()){
                    esFavorit = true;
                    ivFavorits.setImageResource(R.drawable.favoritomarcado);
                }else{
                    // Si la consulta no retorna cap registre, significa que l'usuari no té el local actual com a favorit
                    esFavorit = false;
                    ivFavorits.setImageResource(R.drawable.favorito);
                }


            }catch (SQLException e) {
                e.printStackTrace();
            }
            conexio.close();

            // Afegim un listener per actualitzar l'estat de favorit del local quan l'usuari premi el botó de favorits
            ivFavorits.setOnClickListener(v -> {
                if(esFavorit){
                    actualizarLocal(1);
                } else {
                    actualizarLocal(2);
                }
            });

        }
    }

    // Aquest mètode actualitza l'estat de favorit del local per l'usuari actual
    private void actualizarLocal(int accio) {
        // En funció de l'acció, construïm la consulta SQL per actualitzar o inserir un registre a la taula guarda
        try {
            Connection conexion = ConexioBD.connectar();
            if(accio == 1){
                String sql = "UPDATE guarda SET data_fi = ? WHERE id_usuari = ? AND id_local = ?";
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setDate(1, java.sql.Date.valueOf(String.valueOf(LocalDate.now())));
                ps.setInt(2, sharedPreferences.getInt("id", 0));
                Log.d("JULIAAAA", String.valueOf(idLocal));
                ps.setInt(3, idLocal);
                int filasActualitzades = ps.executeUpdate();
                if (filasActualitzades > 0) {
                    // Si s'ha actualitzat el registre correctament, actualitzem l'estat de favorit del local
                    esFavorit = false;
                    ivFavorits.setImageResource(R.drawable.favorito);
                }
                ps.close();
            } else if(accio == 2){
                PreparedStatement ps2 = conexion.prepareStatement("INSERT INTO guarda (id_usuari, id_local, data_inici) VALUES (?, ?, ?)");
                ps2.setInt(1, id);
                ps2.setInt(2, idLocal);
                ps2.setDate(3, java.sql.Date.valueOf(String.valueOf(LocalDate.now())));
                int filasInserides = ps2.executeUpdate();
                if (filasInserides > 0) {
                    // Si s'ha inserit el registre correctament, actualitzem l'estat de favorit del local
                    ivFavorits.setImageResource(R.drawable.favoritomarcado);
                    esFavorit = true;
                }
                ps2.close();
            }

            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Mètode per tornar enrere
    public void onBackPressed() {
        super.onBackPressed();
    }

}