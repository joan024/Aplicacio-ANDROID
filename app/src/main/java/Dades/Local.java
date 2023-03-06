package Dades;

import java.io.Serializable;
import java.util.List;

public class Local implements Serializable {
    private int foto;
    private String ubicacio, horari, nom, telefon, descripcio;
    private double puntuacio;
    private List<Tapa> tapes;

    public Local(int foto, String nom, String ubicacio, String horari , double puntuacio, String telefon, String descripcio, List <Tapa> tapes) {
        this.foto = foto;
        this.ubicacio = ubicacio;
        this.horari = horari;
        this.nom = nom;
        this.telefon = telefon;
        this.descripcio = descripcio;
        this.puntuacio = puntuacio;
        this.tapes=tapes;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
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
}

