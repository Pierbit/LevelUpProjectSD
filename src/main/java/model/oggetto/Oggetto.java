package model.oggetto;

import model.corso.Corso;

import java.io.Serial;

public class Oggetto implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //Attributi
    private int id;
    //@ public invariant prezzo >= 0;
    private /*@ spec_public @*/ double prezzo;

    //Relazioni
    //@ nullable
    private Corso corso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public /*@ nullable @*/ Corso getCorso() {
        return corso;
    }

    public void setCorso(Corso corso) {
        this.corso = corso;
    }

    public /*@ pure @*/ double getPrezzo() {
        return prezzo;
    }

    //@ requires prezzo >= 0;
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
