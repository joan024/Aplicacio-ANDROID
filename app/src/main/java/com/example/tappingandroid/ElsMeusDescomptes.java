package com.example.tappingandroid;

import static com.example.tappingandroid.Conexio.ConexioBD.closeConnection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Adapter.DescomptesAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Descompte;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



@SuppressLint("NonConstantResourceId")
public class ElsMeusDescomptes extends AppCompatActivity {

    private ArrayList<Descompte> descomptes;
    private RecyclerView recyclerView;
    DescomptesAdapter adaptador;
    private ImageView iv_tornar;
    Statement stmt = null;
    ResultSet rs = null, rs2 = null;
    Connection conexio;

    String codi,text, nom_local;
    Date data_inici, data_final;
    int id, id_local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_els_meus_descomptes);

        // Obtenir l'ID de l'usuari des de SharedPreferences
        id = Utilitats.agafarIdShared(this);

        // Configurar el botó de tornar
        iv_tornar = findViewById(R.id.iv_tornar);
        iv_tornar.setOnClickListener(v -> onBackPressed());

        // Obtenir l'intent que va cridar l'activitat
        Intent intentUsuari = getIntent();
        // Obtenir el valor del String amb la clau "usuari"
        String correu = intentUsuari.getStringExtra("usuari");

        // Inicialitzar la llista de descomptes
        descomptes = new ArrayList<>();
        // Agafar els descomptes de la BD
        AgafarDescomptes();

        // Configurar el RecyclerView i l'adaptador
        recyclerView = findViewById(R.id.recycler_descuentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new DescomptesAdapter(descomptes);

        // Configurar el clic en un element del RecyclerView
        adaptador.setOnItemClickListener(position -> {
                    // Obtenir l'objecte  a la posició seleccionada
                    Descompte descompteSeleccionat = descomptes.get(position);

                    // Obtenir el valor que volem codificar
                    String codi = descompteSeleccionat.getCodi();

                    // Generar el codi QR a partir del valor
                    BitMatrix bitMatrix = generarQRCode(codi);

                    // Convertir el codi QR en una imatge Bitmap
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }

                    // Mostrar el codi QR en una ImageView
                    ImageView imageView = new ImageView(ElsMeusDescomptes.this);
                    imageView.setImageBitmap(bitmap);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ElsMeusDescomptes.this);
                    builder.setView(imageView);
                    builder.show();
                });


        recyclerView.setAdapter(adaptador);

    }

    // Mètode que agafa els descomptes del consumidor actual
    private void AgafarDescomptes() {
        // Obtenir la data actual en format ISO
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataActual = new Date();
        String dataActualString = dateFormat.format(dataActual);

        // Consulta SQL per obtenir els descomptes actius del consumidor actual
        String sql = "SELECT * FROM descompte INNER JOIN consumidor_descompte ON descompte.id=id_descompte WHERE id_usuari="+ id +" AND descompte.inici <='"+dataActualString+"' AND descompte.final >= '"+dataActualString+"'";
        conexio = ConexioBD.connectar();
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);
            Statement stmt2 = conexio.createStatement();

            // Recorrer els resultats de la consulta i afegir-los a la llista de descomptes
            while (rs.next()) {
                id_local = rs.getInt("id_local");
                codi = rs.getString("codi");
                text = rs.getString("text");
                data_inici = rs.getDate("inici");
                data_final = rs.getDate("final");
                String sql2 = "SELECT l.nom FROM local as l INNER JOIN usuari as u ON u.id=l.id_usuari WHERE l.id=" + id_local+" AND u.actiu IS TRUE";
                rs2 = stmt2.executeQuery(sql2);
                if (rs2.next()) {
                    nom_local = rs2.getString("nom");
                    descomptes.add(new Descompte(codi,text, data_final,data_inici,nom_local));
                }
                rs2.close();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection(conexio);

    }

    // Mètode que genera un codi QR a partir d'un codi
    private BitMatrix generarQRCode(String codi) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(codi, BarcodeFormat.QR_CODE, 512, 512);
        return bitMatrix;
    }



    // Configurar el botó d'entrada a la barra d'acció
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
