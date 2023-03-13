package Dades;

public class Noticia {

    private int id ;
    private String titulo;
    private String descripcion;
    private int imagenId;

    public Noticia(String titulo, String descripcion, int imagenId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenId = imagenId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagenId() {
        return imagenId;
    }

}
