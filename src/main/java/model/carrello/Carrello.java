package model.carrello;

import model.oggetto.Oggetto;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class Carrello implements java.io.Serializable {
    //Attributi
    private int id;

    @Serial
    private static final long serialVersionUID = 1L;

    //Relazioni
    private /*@ spec_public @*/ List<Oggetto> oggetti;

    //@ ensures oggetti != null && oggetti.isEmpty();
    public Carrello() {
        this.oggetti = new ArrayList<>();
    }

    public List<Oggetto> getOggetti() {
        return oggetti;
    }

    public void setOggetti(List<Oggetto> oggetti) {
        this.oggetti = oggetti;
    }

    //@ requires oggetti != null;
    //@ requires oggetto != null;
    //@ ensures oggetti.size() == \old(oggetti.size()) + 1;
    public void addOggetto(Oggetto oggetto) {
        this.oggetti.add(oggetto);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
