package model.corso;

import model.categoria.Categoria;
import model.tag.Tag;
import model.utente.Utente;

import java.util.List;

public class Corso {
    //Attributi
    private int id;
    private String nome;
    private double prezzoBase;
    private String testo;
    private String copertina;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(double prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getCopertina() {
        return copertina;
    }

    public void setCopertina(String copertina) {
        this.copertina = copertina;
    }

    //Relazioni
    private Categoria categoria;
    private List<Tag> tags;
    private Utente utenteCreatore;
    private List<Utente> utentiPartecipanti;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Utente getUtenteCreatore() {
        return utenteCreatore;
    }

    public void setUtenteCreatore(Utente utenteCreatore) {
        this.utenteCreatore = utenteCreatore;
    }

    public List<Utente> getUtentiPartecipanti() {
        return utentiPartecipanti;
    }

    public void setUtentiPartecipanti(List<Utente> utentiPartecipanti) {
        this.utentiPartecipanti = utentiPartecipanti;
    }
}
