package model.ordine;

import model.carrello.Carrello;
import model.oggetto.Oggetto;
import model.utente.Utente;

import java.time.LocalDate;
import java.util.Date;

public class Ordine {
    //Attributi
    private int id;
    //@ nullable
    private LocalDate data;

    //Relazioni
    //@ nullable
    private Utente utente;
    //@ nullable
    private Carrello carrello;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public double getImporto() {
        double totale = 0;
        for (Oggetto o: carrello.getOggetti())
            totale += o.getPrezzo();
        return totale;
    }
}
