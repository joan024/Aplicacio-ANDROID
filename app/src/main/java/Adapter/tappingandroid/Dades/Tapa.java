package Adapter.tappingandroid.Dades;

import java.io.Serializable;

public class Tapa implements Serializable {
    private String nom;
    private double preu;

    public Tapa(String nom, double preu) {
        this.nom = nom;
        this.preu = preu;
    }

    public String getNom() {return nom;}

    public double getPreu() {return preu;}

    public void setNom(String nom) {this.nom = nom;}

    public void setPreu(double preu) {this.preu = preu;}


}
