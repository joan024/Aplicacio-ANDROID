package com.example.tappingandroid.Conexio;
import android.os.StrictMode;
import android.util.Log;

import com.example.tappingandroid.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexioBD {

    //static String url = "jdbc:mysql://192.168.1.150:25230/tappingDB";
    static String user = "'tapping'";
    static String password = "JuMaJoJo!!25231";
    static Connection conn = null;
    public static Connection CONN() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(Constants.URL_DB_LOCAL, user, password);


           // conn = DriverManager.getConnection(ConnURL);

        } catch (Exception e) {
            Log.d("marc9", e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            // handle the exception
        }
    }
}
