package com.example.tappingandroid.Dades;

import java.io.Serializable;

public class Tapa implements Serializable {
    private String nom,personalitzacio;
    private double preu;

    public Tapa(String nom, double preu, String personalitzacio) {
        this.nom = nom;
        this.preu = preu;
        this.personalitzacio = personalitzacio;
    }

    public String getNom() {return nom;}

    public double getPreu() {return preu;}

    public void setNom(String nom) {this.nom = nom;}

    public void setPreu(double preu) {this.preu = preu;}

    public String getPersonalitzacio() { return personalitzacio; }

    public void setPersonalitzacio(String personalitzacio) { this.personalitzacio = personalitzacio; }
}
