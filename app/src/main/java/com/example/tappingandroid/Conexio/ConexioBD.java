package com.example.tappingandroid.Conexio;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexioBD {

    static String url = "jdbc:mysql://192.168.56.1:3007/tapping";
    static String user = "root";
    static String password = "";
    static Connection conn = null;

    public static Connection conectar() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            Log.d("juliaaa","Error al carregar el driver " + e.getMessage());
        } catch (SQLException e) {
            Log.d("juliaaa","Error al establir la conexió " + e.getMessage());
        }

        return conn;
    }

    public static void tencarConexio() {
        try {
            conn.close();
            System.out.println("Conexió tencada");
        } catch (SQLException ex) {
            System.out.println("Error al tancar la conexió: " + ex.getMessage());
        }

    }
}
