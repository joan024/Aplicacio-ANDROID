package Adapter.tappingandroid.Dades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Descompte {

    private String codi, descripcio;
    private Date dataCaducitat, dataInici;
    private int localId;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public Descompte(String codi, String descripcio, Date dataCaducitat, Date dataInici, int local) {
        this.codi = codi;
        this.descripcio = descripcio;
        this.dataCaducitat = dataCaducitat;
        this.dataInici = dataInici;
        this.localId = local;
    }

    public Descompte(String codi, String descripcio, String dataCaducitat, String dataInici, int local) throws ParseException {
        this.codi = codi;
        this.descripcio = descripcio;
        this.dataCaducitat = format.parse(dataCaducitat);
        this.dataInici = format.parse(dataInici);
        this.localId = local;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Date getDataCaducitat() {
        return dataCaducitat;
    }

    public void setDataCaducitat(Date dataCaducitat) {
        this.dataCaducitat = dataCaducitat;
    }

    public int getLocal() { return localId; }

    public void setLocal(int local){ this.localId = local; }

    public Date getDataInici() { return dataInici; }

    public void setDataInici(Date dataInici) { this.dataInici = dataInici; }
}

