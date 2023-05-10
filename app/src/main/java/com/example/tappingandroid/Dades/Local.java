package com.example.tappingandroid.Dades;

import java.io.Serializable;
import java.util.List;

public class Local implements Serializable {
    private List<String> imatges;
    private String ubicacio, horari, nom, telefon, descripcio, foto;
    private double puntuacio;
    private int id;
    private List<Tapa> tapes;
    private List<Opinio> opinions;

    public Local(int id, List<String> imatges, String nom, String ubicacio, String horari , double puntuacio, String telefon, String descripcio, List <Tapa> tapes, List <Opinio> opinions) {
        this.id = id;
        this.imatges = imatges;
        this.ubicacio = ubicacio;
        this.horari = horari;
        this.nom = nom;
        this.telefon = telefon;
        this.descripcio = descripcio;
        this.puntuacio = puntuacio;
        this.tapes=tapes;
        this.opinions=opinions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id;}

    public List<String> getFoto() {
        return imatges;
    }

    public void setFoto(List<String> imatges) {
        this.imatges = imatges;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    public String getHorari() {
        return horari;
    }

    public void setHorari(String horari) {
        this.horari = horari;
    }

    public double getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(double puntuacio) {
        this.puntuacio = puntuacio;
    }

    public String getTelefon() {return telefon;}

    public String getDescripcio() {return descripcio;}

    public void setTelefon(String telefon) {this.telefon = telefon;}

    public void setDescripcio(String descripcio) {this.descripcio = descripcio;}

    public List<Tapa> getTapes() {return tapes;}

    public void setTapes(List<Tapa> tapes) {this.tapes = tapes;}

    public void setOpinions(List<Opinio> opinions) { this.opinions = opinions;}

    public List<Opinio> getOpinions() { return opinions; }

    @Override
    public String toString() {
        return "Local{" +
                "foto=" + foto +
                ", ubicacio='" + ubicacio + '\'' +
                ", horari='" + horari + '\'' +
                ", nom='" + nom + '\'' +
                ", telefon='" + telefon + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", puntuacio=" + puntuacio +
                ", tapes=" + tapes +
                '}';
    }
}

