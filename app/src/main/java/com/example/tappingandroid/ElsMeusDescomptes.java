package com.example.tappingandroid;

import static com.example.tappingandroid.Conexio.ConexioBD.closeConnection;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tappingandroid.Adapter.DescomptesAdapter;
import com.example.tappingandroid.Adapter.LocalAdapter;
import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Descompte;
import com.example.tappingandroid.Dades.Local;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        id = Utilitats.agafarIdShared(this);

        Log.d("juliaaaa", "Afegint id: " + id);

        iv_tornar = findViewById(R.id.iv_tornar);
        iv_tornar.setOnClickListener(v -> onBackPressed());
        Intent intentUsuari = getIntent();

        // Obtiene el valor del String con la clave "usuari"
        String correu = intentUsuari.getStringExtra("usuari");
        // Inicializar la lista de descomptes
        descomptes = new ArrayList<>();
        AgafarDescomptes();
        Log.d("juliaaaa", "Añadiendo descuento: " + descomptes);

        // Configurar el RecyclerView i l'adaptador
        recyclerView = findViewById(R.id.recycler_descuentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new DescomptesAdapter(descomptes);

        adaptador.setOnItemClickListener(position -> {
                    // Obtenir l'objecte  a la posició seleccionada
                    Descompte descompteSeleccionat = descomptes.get(position);

                    // Obtener el valor que deseas codificar
                    String codi = descompteSeleccionat.getCodi();

                    // Generar el código QR a partir del valor
                    BitMatrix bitMatrix = generarQRCode(codi);

                    // Convertir el código QR en una imagen Bitmap
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }

                    // Mostrar el código QR en una ImageView
                    ImageView imageView = new ImageView(ElsMeusDescomptes.this);
                    imageView.setImageBitmap(bitmap);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ElsMeusDescomptes.this);
                    builder.setView(imageView);
                    builder.show();
                });


        recyclerView.setAdapter(adaptador);

    }


    private void AgafarDescomptes() {
        String sql = "SELECT * FROM descompte INNER JOIN consumidor_descompte ON descompte.id=id_descompte WHERE id_usuari="+ id;
        conexio = ConexioBD.CONN();
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);
            Statement stmt2 = conexio.createStatement();

            while (rs.next()) {
                id_local = rs.getInt("id_local");
                codi = rs.getString("codi");
                text = rs.getString("text");
                data_inici = rs.getDate("inici");
                data_final = rs.getDate("final");
                String sql2 = "SELECT * FROM local WHERE id=" + id_local;
                rs2 = stmt2.executeQuery(sql2);
                if (rs2.next()) {
                    nom_local = rs2.getString("nom");
                }
                rs2.close();

                descomptes.add(new Descompte(codi,text, data_final,data_inici,nom_local));
                Log.d("juliaaaa", "Añadiendo descuento: " + codi);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection(conexio);

    }


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
