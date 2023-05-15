package com.example.tappingandroid.Conexio;
import android.os.StrictMode;
import android.util.Log;

import com.example.tappingandroid.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexioBD {

    //Definim els parametres de connexio
    static String user = "'tapping'";
    static String password = "JuMaJoJo!!25231";
    static Connection conn = null;

    // Mètode per obtenir una connexió a la base de dades
    public static Connection connectar() {

        // Permetem la connexió en el thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        try {
            // Carreguem el driver de la base de dades
            Class.forName("com.mysql.jdbc.Driver");

            // Obtenim la connexió a la base de dades remota o local
            conn = DriverManager.getConnection(Constants.URL_DB_REMOT, user, password);

        } catch (Exception e) {
            Log.d("marc9", e.getMessage());
        }
        return conn;
    }

    // Mètode per tancar la connexió a la base de dades
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }
}
