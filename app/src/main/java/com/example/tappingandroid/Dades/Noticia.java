package com.example.tappingandroid.Dades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Noticia implements Serializable {

    private String imagen;
    private String titol,descripcio,data_inici,data_fi,data_publicacio;

    public Noticia(String imagen, String titol, String descripcio, String data_inici, String data_fi, String data_publicacio) {
        this.imagen = imagen;
        this.titol = titol;
        this.descripcio = descripcio;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.data_publicacio = data_publicacio;
    }

    public String getImagen() {return imagen;}
    public void setImagen(String imagen) {this.imagen = imagen;}

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

    @Override
    public String toString() {
        return "Noticia{" +
                "imagen=" + imagen +
                ", titol='" + titol + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", data_inici='" + data_inici + '\'' +
                ", data_fi='" + data_fi + '\'' +
                ", data_publicacio='" + data_publicacio + '\'' +
                '}';
    }
}
