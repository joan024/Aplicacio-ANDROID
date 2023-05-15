package com.example.tappingandroid.Dades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Classe que representa un descompte amb les següents propietats: codi, descripció, data d'inici,
//data de caducitat i nom del local on es pot fer efectiu el descompte.
public class Descompte {

    private String codi, descripcio, local;
    private Date dataCaducitat, dataInici;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public Descompte(String codi, String descripcio, Date dataCaducitat, Date dataInici, String local) {
        this.codi = codi;
        this.descripcio = descripcio;
        this.dataCaducitat = dataCaducitat;
        this.dataInici = dataInici;
        this.local = local;
    }

    public Descompte(String codi, String descripcio, String dataCaducitat, String dataInici, String local) throws ParseException {
        this.codi = codi;
        this.descripcio = descripcio;
        this.dataCaducitat = format.parse(dataCaducitat);
        this.dataInici = format.parse(dataInici);
        this.local = local;
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

    public String getLocal() { return local; }

    public void setLocal(String local){ this.local = local; }

    public Date getDataInici() { return dataInici; }

    public void setDataInici(Date dataInici) { this.dataInici = dataInici; }
}

