package Dades;

public class Local {
    private int foto;
    private String nom;
    private String ubicacio;
    private String horari;
    private double puntuacio;

    public Local(int foto, String nom, String ubicacio, String horari, double puntuacio) {
        this.foto = foto;
        this.nom = nom;
        this.ubicacio = ubicacio;
        this.horari = horari;
        this.puntuacio = puntuacio;
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
}

