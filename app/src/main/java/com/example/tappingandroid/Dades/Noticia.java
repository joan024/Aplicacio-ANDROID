package com.example.tappingandroid.Dades;

import java.util.ArrayList;
import java.util.List;

public class Noticia {

    private int imagen;
    private String titol,descripcio,data_inici,data_fi,data_publicacio;
    private ArrayList<Noticia> noticies;

    public Noticia(int imagen, String titol, String descripcio, String data_inici, String data_fi, String data_publicacio, ArrayList<Noticia> noticies) {
        this.imagen = imagen;
        this.titol = titol;
        this.descripcio = descripcio;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.data_publicacio = data_publicacio;
        this.noticies = noticies;
    }

    public int getImagen() {return imagen;}
    public void setImagen(int imagen) {this.imagen = imagen;}

    public String getTitol() {return titol;}

    public void setTitol(String titol) {this.titol = titol;}

    public String getDescripcio() {return descripcio;}
    public void setDescripcio(String descripcio) {this.descripcio = descripcio;}

    public String getData_inici() {return data_inici;}

    public void setData_inici(String data_inici) {this.data_inici = data_inici;}
    public String getData_fi() {return data_fi;}

    public void setData_fi(String data_fi) {this.data_fi = data_fi;}

    public String getData_publicacio() {return data_publicacio;}

    public void setData_publicacio(String data_publicacio) {this.data_publicacio = data_publicacio;}

    public List<Noticia> getNoticies() {return noticies;}

    public void setNoticies(ArrayList<Noticia> noticies) {this.noticies = noticies;}

    @Override
    public String toString() {
        return "Noticia{" +
                "imagen=" + imagen +
                ", titol='" + titol + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", data_inici='" + data_inici + '\'' +
                ", data_fi='" + data_fi + '\'' +
                ", data_publicacio='" + data_publicacio + '\'' +
                ", noticies=" + noticies +
                '}';
    }
}
