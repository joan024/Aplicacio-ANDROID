package com.example.tappingandroid.Dades;
public class Missatge {

    private String usuari;
    private String missatge;
    private String hora;

    public Missatge(String name, String message, String time) {
        this.usuari = name;
        this.missatge = message;
        this.hora = time;
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
}

