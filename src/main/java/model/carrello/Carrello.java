package model.carrello;

import model.oggetto.Oggetto;

import java.util.List;

public class Carrello {
    //Attributi
    private int id;

    //Relazioni
    private List<Oggetto> oggetti;

    public List<Oggetto> getOggetti() {
        return oggetti;
    }

    public void setOggetti(List<Oggetto> oggetti) {
        this.oggetti = oggetti;
    }

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
