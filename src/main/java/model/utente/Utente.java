package model.utente;

import model.carrello.Carrello;
import model.corso.Corso;
import model.ordine.Ordine;
import model.tag.Tag;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Utente {
    //Attributi
    private String nickname, email, password, fotoProfilo, biografia;
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
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.password = String.format("%040x", new
                    BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
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
    private List<Tag> tags;
    private List<Corso> corsiPartecipati, corsiCreati;
    private Carrello carrello;
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
