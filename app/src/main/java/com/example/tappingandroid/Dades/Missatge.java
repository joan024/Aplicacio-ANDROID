package com.example.tappingandroid.Dades;

import androidx.annotation.NonNull;

//Classe que representa un missatge, amb l'usuari, el missatge i l'hora en què es va enviar.
public class Missatge {

    private String usuari;
    private String missatge;
    private String hora;

    public Missatge(String name, String message, String time) {
        this.usuari = name;
        this.missatge = message;
        this.hora = time;
    }

    public Missatge() {

    }

    public String getUsuari() {
        return usuari;
    }

    public String getMissatge() {
        return missatge;
    }

    public String getHora() {
        return hora;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
    }

    public void setMissatge(String missatge) {
        this.missatge = missatge;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @NonNull
    @Override
    public String toString() {
        return "Nom: "+usuari+", Missatge: "+missatge+", Hora: "+hora;
    }
}

