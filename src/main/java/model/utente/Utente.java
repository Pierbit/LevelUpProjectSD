package model.utente;

import model.carrello.Carrello;
import model.corso.Corso;
import model.ordine.Ordine;
import model.tag.Tag;

import java.io.Serial;
import java.util.List;

public class Utente implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //Attributi
    //@ nullable
    private String nickname, email, password, fotoProfilo, biografia;
    //@ nullable
    private Boolean manager;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordHashed(String password) {
        this.password = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getManager(){
        return this.manager;
    }

    public void setManager(boolean manager){
        this.manager = manager;
    }

    public String getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(String fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    //Relazioni
    //@ nullable
    private List<Tag> tags;
    //@ nullable
    private List<Corso> corsiPartecipati, corsiCreati;
    //@ nullable
    private Carrello carrello;
    //@ nullable
    private List<Ordine> ordini;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Corso> getCorsiPartecipati() {
        return corsiPartecipati;
    }

    public void setCorsiPartecipati(List<Corso> corsiPartecipati) {
        this.corsiPartecipati = corsiPartecipati;
    }

    public List<Corso> getCorsiCreati() {
        return corsiCreati;
    }

    public void setCorsiCreati(List<Corso> corsiCreati) {
        this.corsiCreati = corsiCreati;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(List<Ordine> ordini) {
        this.ordini = ordini;
    }
}
