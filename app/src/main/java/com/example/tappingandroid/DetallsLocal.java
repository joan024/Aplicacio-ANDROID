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

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class DetallsLocal extends AppCompatActivity {

    //Definim les vistes que farem servir en aquesta classe
    @BindView(R.id.iv_tornar)
    ImageView ivTornar;
    @BindView(R.id.iv_favorits)
    ImageView ivFavorits;
    @BindView(R.id.iv_imatge_local)
    ImageView ivImatge;
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
        Picasso.get().load(local.getFoto()).into(ivImatge);
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

    private void comprovarFavorit() throws SQLException {
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean sessionActive = sharedPreferences.getBoolean("session_active", false); // false es el valor predeterminado si la clave no existe
        id = sharedPreferences.getInt("id", 0);

        if(sessionActive && id != 0){
            conexio = ConexioBD.CONN();

            String sql = "SELECT * FROM local " +
                    "INNER JOIN guarda ON guarda.id_local=local.id "+
                    "WHERE guarda.id_usuari = "+ id +
                    " AND guarda.data_fi IS NULL"+
                    " AND guarda.id_local = "+idLocal;

            try {
                stmt = conexio.createStatement();
                rs = stmt.executeQuery(sql);

                if(rs.next()){
                    esFavorit = true;
                    ivFavorits.setImageResource(R.drawable.favoritomarcado);
                }else{
                    esFavorit = false;
                    ivFavorits.setImageResource(R.drawable.favorito);
                }


            }catch (SQLException e) {
                e.printStackTrace();
            }
            conexio.close();

            ivFavorits.setOnClickListener(v -> {
                if(esFavorit){
                    actualizarLocal(1);
                } else {
                    actualizarLocal(2);
                }
            });

        }
    }

    private void actualizarLocal(int accio) {
        try {
            Connection conexion = ConexioBD.CONN();
            if(accio == 1){
                String sql = "UPDATE guarda SET data_fi = ? WHERE id_usuari = ? AND id_local = ?";
                PreparedStatement ps = conexion.prepareStatement(sql);
                ps.setDate(1, java.sql.Date.valueOf(String.valueOf(LocalDate.now())));
                ps.setInt(2, sharedPreferences.getInt("id", 0));
                Log.d("JULIAAAA", String.valueOf(idLocal));
                ps.setInt(3, idLocal);
                int filasActualitzadas = ps.executeUpdate();
                if (filasActualitzadas > 0) {
                    // Se ha actualizado el registro correctamente
                    esFavorit = false;
                    ivFavorits.setImageResource(R.drawable.favorito);
                }
                ps.close();
            } else if(accio == 2){
                PreparedStatement ps2 = conexion.prepareStatement("INSERT INTO guarda (id_usuari, id_local, data_inici) VALUES (?, ?, ?)");
                ps2.setInt(1, id);
                ps2.setInt(2, idLocal);
                ps2.setDate(3, java.sql.Date.valueOf(String.valueOf(LocalDate.now())));
                int filasActualitzadas = ps2.executeUpdate();
                if (filasActualitzadas > 0) {
                    // Se ha inserit el registro correctamente
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