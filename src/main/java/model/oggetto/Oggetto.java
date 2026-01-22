package model.oggetto;

import model.corso.Corso;

public class Oggetto {
    //Attributi
    private int id;
    private double prezzo;

    //Relazioni
    private Corso corso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
