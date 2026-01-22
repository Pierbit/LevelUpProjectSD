package model.utente;

import model.storage.TableQuery;

public class UtenteQuery extends TableQuery {
    public UtenteQuery(String table) {
        super(table);
    }

    public String countUtenti() {
        return String.format("SELECT COUNT(*) FROM %s;", this.table);
    }

    public String selectUtenti() {
        return String.format("SELECT * FROM %s LIMIT ?, ?", this.table);
    }

    public String selectUtente() {
        return String.format("SELECT * FROM %s WHERE nickname=?;", this.table);
    }

    public String insertUtente() {
        return String.format("INSERT INTO %s (nickname, email, password, fotoprofilo, biografia, manager) VALUES (?,?,?,?,?,?);", this.table);
    }

    public String updateUtente() {
        return String.format("UPDATE %s SET nickname=?,email=?,password=?,fotoProfilo=?,biografia=?,manager=? WHERE nickname=?;", this.table);
    }

    public String deleteUtente() {
        return String.format("DELETE FROM %s WHERE nickname=?;", this.table);
    }

    public String getTags() {
        return "SELECT tag.* FROM utenteTag, tag WHERE utenteTag.nicknameUtente=? AND utenteTag.nomeTag = tag.nome;";
    }

    public String dropTags() {
        return "DELETE FROM utenteTag WHERE nicknameUtente=?;";
    }

    public String getCorsiPartecipati() {
        return "SELECT corso.* FROM utentePartecipaCorso, corso WHERE utentePartecipaCorso.nicknameUtente=? AND utentePartecipaCorso.idCorso=corso.id;";
    }

    public String getCorsiPartecipatiLimit(){
        return "SELECT corso.* FROM utentePartecipaCorso, corso WHERE utentePartecipaCorso.nicknameUtente=? AND utentePartecipaCorso.idCorso=corso.id LIMIT ?, ?;";
    }

    public String countCorsiPartecipati(){
        return String.format("SELECT COUNT(*) FROM utentePartecipaCorso, corso WHERE utentePartecipaCorso.nicknameUtente=? AND utentePartecipaCorso.idCorso=corso.id");
    }

    public String dropCorsiPartecipati() {
        return "DELETE FROM utentePartecipaCorso WHERE nicknameUtente=?;";
    }

    public String getCorsiPubblicati() {
        return "SELECT corso.* FROM utenteCreaCorso, corso WHERE utenteCreaCorso.nicknameUtente=? AND utenteCreaCorso.idCorso=corso.id;";
    }

    public String getCorsiPubblicatiLimit(){
        return "SELECT corso.* FROM utenteCreaCorso, corso WHERE utenteCreaCorso.nicknameUtente=? AND utenteCreaCorso.idCorso=corso.id LIMIT ?, ?;";
    }

    public String countCorsiPubblicati(){
        return String.format("SELECT COUNT(*) FROM utenteCreaCorso, corso WHERE utenteCreaCorso.nicknameUtente=? AND utenteCreaCorso.idCorso=corso.id");
    }

    public String dropCorsiPubblicati() {
        return "DELETE FROM utenteCreaCorso WHERE nicknameUtente=?;";
    }

    public String getOrdini() {
        return "SELECT ordine.* FROM utenteOrdine, ordine WHERE utenteOrdine.nicknameUtente=? AND utenteOrdine.idOrdine=ordine.id;";
    }

    public String getOrdiniLimit(){
        return "SELECT ordine.* FROM utenteOrdine, ordine WHERE utenteOrdine.nicknameUtente=? AND utenteOrdine.idOrdine=ordine.id LIMIT ?, ?;";
    }

    public String countOrdiniEffettuati(){
        return String.format("SELECT COUNT(*) FROM utenteOrdine, ordine WHERE utenteOrdine.nicknameUtente=? AND utenteOrdine.idOrdine=ordine.id;");
    }
    public String dropOrdini() {
        return "DELETE FROM utenteOrdine WHERE nicknameUtente=?;";
    }

    public String insertCarrello() {
        return "INSERT INTO utenteCarrello VALUES(?,?);";
    }

    public String getCarrello() {
        return "SELECT carrello.* FROM utenteCarrello, carrello WHERE utenteCarrello.nicknameUtente=? AND utenteCarrello.idCarrello=carrello.id;";
    }

    public String dropCarrello() {
        return "DELETE FROM utenteCarrello WHERE nicknameUtente=?;";
    }

}
