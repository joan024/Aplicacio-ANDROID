package com.example.tappingandroid;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;

import com.example.tappingandroid.Conexio.ConexioBD;
import com.example.tappingandroid.Dades.Local;
import com.example.tappingandroid.Dades.Opinio;
import com.example.tappingandroid.Dades.Tapa;
import com.example.tappingandroid.GestioDeRegistres.IniciSessio;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Utilitats {
    static ArrayList<String> fotosXML = new ArrayList<String>();
    static String rutaArxiusRemot = "http://tapping.000webhostapp.com/fotos/Locals/";
    static ArrayList<String> compDates = new ArrayList<String>();

    public static String obtindreElDiaSetmanal() {
        // Obté el dia de la setmana actual
        Calendar calendari = Calendar.getInstance();
        int diaSetmana = calendari.get(Calendar.DAY_OF_WEEK);


        /* Obté el nom del dia de la setmana en català*/
        String[] dayNames = new String[]{
                "diumenge",
                "dilluns",
                "dimarts",
                "dimecres",
                "dijous",
                "divendres",
                "dissabte"
        };
        return dayNames[diaSetmana - 1]; // Retorna el nom del dia de la setmana actual
    }

    public static String queryHorari(Statement stmt2, int id_horari) throws SQLException {
        String horari="";
        String sql2 = "SELECT * FROM horari WHERE id=" + id_horari;
        ResultSet rsHorari = stmt2.executeQuery(sql2);
        while (rsHorari.next()) {
            String dia= Utilitats.obtindreElDiaSetmanal(); // Obtenir el dia de la setmana actual
            horari = rsHorari.getString(dia); // Obtenir l'horari corresponent al dia de la setmana actual
        }
        rsHorari.close();
        return horari; // Retorna l'horari corresponent al dia de la setmana actual
    }

    public static List<String> queryFotoPrincipal(int id_local, Context context) throws SQLException, IOException, ParserConfigurationException, SAXException {

        Log.d("juliaaaaaa","Inici de Foto Principal del local " + id_local);
        boolean hiHaFotos = false;
        // Comprova si l'arxiu del local ja s'ha baixat
        hiHaFotos = comprovarSiExisteixArxiuLocal(id_local,hiHaFotos);

        //Descarregem els fitxers necessaris
        descarrega(id_local,hiHaFotos,context);

        Log.d("Juliaaa", String.valueOf(fotosXML));
         ArrayList<String> fotosXML2 = new ArrayList<String>(fotosXML);
        return fotosXML2;
    }

    static void descarrega(int id_local, boolean hiHaFotos, Context context) throws IOException, ParserConfigurationException, SAXException {
        Boolean jaBaixat = false;

        //Amb la comprovació que hem fet abans, si hi ha fotos baixarem la versió 2, si no, la normal
        if(hiHaFotos){
            baixarXml(id_local, "v2.xml");
            jaBaixat = true;
        }else{
            baixarXml(id_local, "v.xml");
        }

        //Definim els dos fitxers que hem de gestionar
        File xmlVersio1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ Constants.rutaArxiusLocal +id_local+"/"), "v.xml");
        File xmlVersio2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ Constants.rutaArxiusLocal +id_local+"/"), "v2.xml");

        //llegim el primer xml
        llegirXml(xmlVersio1);
        //Si ja s'havia baixat la primera versió, llegirem també la versió 2
        if(jaBaixat){
            llegirXml(xmlVersio2);
            //Log.d("juliaaaaaa","Data 1: "+compDates.get(0));
            //Log.d("juliaaaaaa","Data 2: "+compDates.get(1));
            //Si les dates no son iguals, esborrarem totes les imatges i descarregarem nomes les que ens diu a la segona versio del xml
            if(!compDates.get(0).equals(compDates.get(1))){
                Log.d("juliaaaaaa","Les dates son diferents");
                //Esborrem totes les fotografies
                esborrarImatges(id_local);
                //Baixem les que inclou la v2.xml
                for (int i = 0; i < fotosXML.size() && fotosXML.get(i) != null; i++){
                    baixarImatges(context, fotosXML.get(i), id_local);
                }
            }
            //Esborrem la versio antiga el xml i renombrem la nova per a que es digui de nou v.xml
            xmlVersio1.delete();
            xmlVersio2.renameTo(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ Constants.rutaArxiusLocal +id_local+"/v.xml"));
        }else{
            //Si no s'havia baixat la primera versió, baixarem les imatges del v.xml
            for (int i = 0; i < fotosXML.size() && fotosXML.get(i) != null; i++){
                baixarImatges(context, fotosXML.get(i), id_local);
            }
        }
        compDates.clear();
    }

    private static void esborrarImatges(int id_local) {
        //Agafarem només els arxius que siguin png o jpg
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ Constants.rutaArxiusLocal +id_local+"/"), "");
        File[] arxiusACarpeta = carpeta.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg"));

        //Esborrem totes les imatges
        if (arxiusACarpeta != null) {
            for (File arxiu : arxiusACarpeta) {
                if (!arxiu.delete()) {
                    Log.d("juliaaaaaa","No s'ha eliminat l'arxiu: " + arxiu.getName());
                }else{
                    Log.d("juliaaaaaa","ELIMINAT: " + arxiu.getName());

                }
            }
        }
    }

    private static void baixarImatges(Context context, String nom, int id_local) {
        try{
            //Fem la conexió a la ruta indicada
            URL url = new URL(rutaArxiusRemot+nom);
            HttpURLConnection cn= (HttpURLConnection) url.openConnection();
            cn.setConnectTimeout(500);
            cn.connect();

            //Indiquem la ruta de destí
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ Constants.rutaArxiusLocal +id_local+"/"), nom);

            // Asegurarse de que les carpetes de la ruta existeixen, si no, les crea
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fileOutput = new FileOutputStream(file);

            InputStream inputStream = cn.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            //El guardem
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void baixarXml(int id_local, String nomFitxer) throws IOException {
        try{
            //Fem la conexió a la ruta indicada
            URL url = new URL(rutaArxiusRemot+id_local+".xml");
            HttpURLConnection cn= (HttpURLConnection) url.openConnection();
            cn.setConnectTimeout(500);
            cn.connect();

            //Indiquem la ruta de destí
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS+ Constants.rutaArxiusLocal +id_local+"/"), nomFitxer);

            // Asegurarse de que les carpetes de la ruta existeixen, si no, les crea
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fileOutput = new FileOutputStream(file);

            InputStream inputStream = cn.getInputStream();

            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            //El guardem
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
            }
            fileOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean comprovarSiExisteixArxiuLocal(int id_local, boolean hiHaFotos) {
        //Creem un file amb la ruta
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + Constants.rutaArxiusLocal + id_local);
        //Comprovem si la carpeta existeix
        if(carpeta.exists()){
            File arxiuxml= new File(carpeta + "/v.xml");
            //Comprovem si hi ha una versió ja baixada
            if(arxiuxml.exists()){
                hiHaFotos = true;
            }
        }
        return hiHaFotos;
    }

    private static void llegirXml(File xmlFile) throws ParserConfigurationException, IOException, SAXException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        // Obtindre l'element pare del document
        Element root = document.getDocumentElement();

        // Obtindre l'element <data> del document
        NodeList dataLlista = root.getElementsByTagName("data");
        if (dataLlista.getLength() > 0) {
            Node dataNode = dataLlista.item(0);
            String data = dataNode.getTextContent();
            compDates.add(data);
            //Log.d("juliaaaa","Fecha de las fotos: " + data);
        }

        // Obtindre la llista d'elements <foto> del document
        NodeList llistaFotos = root.getElementsByTagName("foto");
        fotosXML.clear();
        for (int i = 0; i < llistaFotos.getLength(); i++) {
            Node fotoNode = llistaFotos.item(i);
            if (fotoNode.getNodeType() == Node.ELEMENT_NODE) {
                Element photoElement = (Element) fotoNode;
                // Obtindre l'element <nom> de cada element <foto>
                NodeList llistaNoms = photoElement.getElementsByTagName("nom");
                if (llistaNoms.getLength() > 0) {
                    Node nomNode = llistaNoms.item(0);
                    String nom = nomNode.getTextContent();
                    fotosXML.add(nom);
                    Log.d("juliaaaa","Nombre de la foto: " + nom);
                }
            }
        }
    }

    // Aquesta funció consulta les tapes que ofereix un local, i les retorna en forma de llista.
    // El paràmetre id_local és l'identificador del local a consultar.
    public static List<Tapa> queryTapes(Statement stmt3, List<Tapa> tapes, int id_local) throws SQLException {
        tapes = new ArrayList<>();
        String sql3 = "SELECT tapa.nom, local_tapa.personalitzacio, local_tapa.preu FROM tapa INNER JOIN local_tapa ON local_tapa.id_tapa=tapa.id WHERE local_tapa.id_local=" + id_local;
        ResultSet rsTapes = stmt3.executeQuery(sql3);
        while (rsTapes.next()) {
            String nom_tapa = rsTapes.getString("nom");
            String personalitzacio_tapa = rsTapes.getString("personalitzacio");
            double preu = rsTapes.getDouble("preu");

            tapes.add(new Tapa(nom_tapa,preu,personalitzacio_tapa));
        }
        rsTapes.close();
        return tapes;
    }

    /* Aquesta funció consulta les opinions dels usuaris sobre un local, i les retorna en forma de llista.
    El paràmetre id_local és l'identificador del local a consultar. */
    public static List<Opinio> queryOpinions(Statement stmt4, List<Opinio> opinions, int id_local) throws SQLException {
        opinions = new ArrayList<>();
        String sql4 = "SELECT * FROM valoracio INNER JOIN usuari ON usuari.id=valoracio.id_usuari WHERE valoracio.id_local=" + id_local;
        ResultSet rsOpinions = stmt4.executeQuery(sql4);
        while (rsOpinions.next()) {
            String nom_valoracio = rsOpinions.getString("nom");
            double puntuacio = rsOpinions.getDouble("puntuacio");
            String comentari = rsOpinions.getString("comentari");
            Date data = rsOpinions.getDate("data");

            //Log.d("juliaaa", "PUNTUACIO: "+String.valueOf(puntuacio));
            opinions.add(new Opinio(nom_valoracio,data,comentari,puntuacio));
        }
        rsOpinions.close();
        return opinions;
    }

    /* Aquesta funció calcula la mitjana de les puntuacions d'una llista d'opinions.
    El paràmetre opinions és la llista d'opinions a processar.*/
    public static Double calcularMitjanaPuntuacio(List<Opinio> opinions) {
        double sumaPuntuacions = 0;
        int numOpinions = opinions.size();

        for (Opinio opinio : opinions) {
            sumaPuntuacions += opinio.getPuntuacio();
        }
        return sumaPuntuacions / numOpinions;
    }

    /* Aquesta funció executa una consulta SQL per obtenir la llista de locals
    que pertanyen a l'usuari amb l'ID especificat i la retorna com una llista d'objectes Local.
    També executa altres consultes SQL per obtenir les opinions, tapes i foto principal
    de cada local, i calcula la puntuació mitjana de les opinions.*/
    public static List<Local> getLocals(List<Local> locals, int id, Context context) throws ParseException, SQLException {
        Connection conexio = ConexioBD.connectar();
        String sql = "SELECT * FROM local WHERE id_usuari="+ id;
        List<Opinio> opinions = null;
        List<Tapa> tapes = null;

        locals = new ArrayList<>();

        Statement stmt;
        ResultSet rs;
        try {
            stmt = conexio.createStatement();
            rs = stmt.executeQuery(sql);
            Statement stmt2 = conexio.createStatement();
            Statement stmt3 = conexio.createStatement();
            Statement stmt4 = conexio.createStatement();

            while (rs.next()) {
                int id_local = rs.getInt("id");
                String nom_local = rs.getString("nom");
                String direccio_local = rs.getString("direccio");
                String telefon_local = rs.getString("telefon");
                int id_horari = rs.getInt("id_horari");
                String descripcio = rs.getString("descripcio");

                String horari = Utilitats.queryHorari(stmt2, id_horari);
                tapes = Utilitats.queryTapes(stmt3, tapes, id_local);
                opinions = Utilitats.queryOpinions(stmt4, opinions, id_local);
                List<String> nomFoto = Utilitats.queryFotoPrincipal(id_local, context);
                Log.d("juliaaaaaaaa","Nom foto abans d'inserir: "+nomFoto);
                Double mitjana= Utilitats.calcularMitjanaPuntuacio(opinions);

                locals.add(new Local(id_local,nomFoto,nom_local,direccio_local,horari,mitjana,telefon_local,descripcio,tapes,opinions));

            }
        } catch (SQLException | IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        conexio.close();
        return locals;
    }


    // Aquesta funció retorna l'ID guardat a shared Preferences
    public static int agafarIdShared(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPref.getInt("id", 0);
    }

    // Aquesta funció controla la visibilitat dels elements del menú per als consumidors
    public static void visibilitatConsumidors(MenuItem menuItemFavorits, MenuItem menuItemDescomptes, MenuItem menuItemLesMevesDades, MenuItem menuItemXat, MenuItem menuItemLocals, MenuItem menuItemTapes, MenuItem menuItemComentaris) {
        menuItemLocals.setVisible(false);
        menuItemTapes.setVisible(false);
        menuItemComentaris.setVisible(false);
        menuItemXat.setVisible(false);
        menuItemLesMevesDades.setVisible(true);
        menuItemDescomptes.setVisible(true);
        menuItemFavorits.setVisible(true);
    }

    // Aquesta funció controla la visibilitat dels elements del menú per als locals
    public static void visibilitatLocals(MenuItem menuItemFavorits, MenuItem menuItemDescomptes, MenuItem menuItemLesMevesDades, MenuItem menuItemXat, MenuItem menuItemLocals, MenuItem menuItemTapes, MenuItem menuItemComentaris) {
        menuItemLocals.setVisible(true);
        menuItemTapes.setVisible(true);
        menuItemComentaris.setVisible(true);
        menuItemXat.setVisible(true);
        menuItemLesMevesDades.setVisible(false);
        menuItemDescomptes.setVisible(false);
        menuItemFavorits.setVisible(false);
    }

    // Aquesta funció retorna el tipus d'usuari a partir d'un id
    public static int getTipusUsuari(int id) throws SQLException {
        Connection conexio = ConexioBD.connectar();
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
            return 2; // Si l'usuari és local, retorna 2
        } else {
            return 1; // Si l'usuari és consumidor, retorna 1
        }
    }


    @SuppressLint("NonConstantResourceId")
    public static Intent gestioDeMenu(MenuItem item, Intent intent, String nom, Context context, String correu) {
        // S'executa una acció segons el botó premut del menú Drawer
        switch (item.getItemId()) {
            case R.id.btn_dades:
                if (nom != null) {
                    intent = new Intent(context, LesMevesDades.class);
                    intent.putExtra("usuari", correu);
                } else {
                    intent = new Intent(context, IniciSessio.class);
                }
                break;
            case R.id.btn_preferits:
                if (nom != null) {
                    intent = new Intent(context, ElsMeusFavorits.class);
                    intent.putExtra("usuari", correu);
                } else {
                    intent = new Intent(context, IniciSessio.class);
                }
                break;
            case R.id.btn_descompte:
                if (nom != null) {
                    intent = new Intent(context, ElsMeusDescomptes.class);
                    intent.putExtra("usuari", correu);
                } else {
                    intent = new Intent(context, IniciSessio.class);
                }
                break;
            case R.id.btn_noticies:
                intent = new Intent(context, Noticies.class);
                break;
            case R.id.btn_preguntes:
                intent = new Intent(context, PreguntesFrequents.class);
                break;
            case R.id.btn_contacte:
                intent = new Intent(context, Contacta.class);
                break;
            case R.id.btn_locals:
                intent = new Intent(context, ElsMeusLocals.class);
                break;
            case R.id.btn_tapes:
                intent = new Intent(context, LesMevesTapes.class);
                break;
            case R.id.btn_comentaris:
                intent = new Intent(context, Comentaris.class);
                break;
            case R.id.btn_xat:
                intent = new Intent(context, Xat.class);
                break;
            case R.id.btn_login:
                intent = new Intent(context, IniciSessio.class);
                break;
            case R.id.btn_close:

                SharedPreferences.Editor editor = context.getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                editor.putBoolean("session_active", false);
                editor.remove("id");
                editor.remove("nom");
                editor.apply();
                intent = new Intent(context, Inici.class);
                break;
        }
        // Retorna l'intent amb l'activitat que s'ha de mostrar
        return intent;
    }
}