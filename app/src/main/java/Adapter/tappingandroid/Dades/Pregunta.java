package Adapter.tappingandroid.Dades;

public class Pregunta {

    private int id;
    private String pregunta;
    private String resposta;

    public Pregunta(int id, String pregunta, String resposta) {
        this.id = id;
        this.pregunta = pregunta;
        this.resposta = resposta;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getPregunta() {return pregunta;}

    public void setPregunta(String pregunta) {this.pregunta = pregunta;}

    public String getResposta() {return resposta;}

    public void setResposta(String resposta) {this.resposta = resposta;}
    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", pregunta='" + pregunta + '\'' +
                ", resposta='" + resposta + '\'' +
                '}';
    }
}
