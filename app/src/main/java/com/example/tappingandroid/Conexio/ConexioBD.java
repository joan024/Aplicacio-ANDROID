package com.example.tappingandroid.Conexio;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexioBD {

    static String url = "jdbc:mysql://192.168.56.1:3307/tapping";
    static String user = "'root'";
    static String password = "";
    static Connection conn = null;
    public static Connection CONN() {
        Log.d("marc4","marc4");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        Log.d("marc5","marc5");
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        //String ConnURL = null;
        try {
            Log.d("marc3","marc3");
            Class.forName("com.mysql.jdbc.Driver");
            Log.d("marc7","marc7");
            conn = DriverManager.getConnection(url, user, password);

            Log.d("marc8","marc3");
           // conn = DriverManager.getConnection(ConnURL);
            Log.d("marc6","marc3");
        } catch (SQLException se) {
            Log.d("marc9", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d("marc9", e.getMessage());
        } catch (Exception e) {
            Log.d("marc9", e.getMessage());
        }
        Log.d("marc00","marc3");
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
