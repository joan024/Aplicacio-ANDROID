package com.example.tappingandroid.Dades;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Classe Opinio que representa les opinions dels usuaris que han valorat un local
public class Opinio implements Serializable {
    private String nomUsuari;
    private Date data;
    private String comentari;
    private double puntuacio;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public Opinio(String nomUsuari, Date data, String comentari, double puntuacio) {
        this.nomUsuari = nomUsuari;
        this.data = data;
        this.comentari = comentari;
        this.puntuacio = puntuacio;
    }

    public Opinio(String nomUsuari, Date data, double puntuacio) {
        this.nomUsuari = nomUsuari;
        this.data = data;
        this.puntuacio = puntuacio;
    }

    public Opinio(String nomUsuari, String data, String comentari, double puntuacio) throws ParseException {
        this.nomUsuari = nomUsuari;
        this.data = format.parse(data);
        this.comentari = comentari;
        this.puntuacio = puntuacio;
    }
    public Opinio(String nomUsuari, String data, int puntuacio) throws ParseException {
        this.nomUsuari = nomUsuari;
        this.data = format.parse(data);
        this.puntuacio = puntuacio;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public Date getData() {
        return data;
    }

    public String getComentari() {
        return comentari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    public void setPuntuacio(double puntuacio) {
        this.puntuacio = puntuacio;
    }

    public double getPuntuacio() {
        return puntuacio;
    }
}

