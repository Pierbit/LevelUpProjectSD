package model.ordine;

import model.storage.TableQuery;

public class OrdineQuery extends TableQuery {
    public OrdineQuery(String table) {
        super(table);
    }

    public String selectOrdini() {
        return String.format("SELECT * FROM %s LIMIT ?, ?;", this.table);
    }

    public String selectOrdine() {
        return String.format("SELECT * FROM %s WHERE id=?;", this.table);
    }

    public String insertOrdine() {
        return String.format("INSERT INTO %s (id,dataOrdine) VALUES (?,?);", this.table);
    }

    public String updateOrdine() {
        return String.format("UPDATE %s SET dataOrdine=? WHERE id=?;", this.table);
    }

    public String deleteOrdine() {
        return String.format("DELETE FROM %s WHERE id=?;", this.table);
    }

    public String countOrdini() {
        return String.format("SELECT COUNT(*) FROM %s;", this.table);
    }

    public String getUtente() { return String.format("SELECT utente.* FROM utenteOrdine, utente WHERE utenteOrdine.idOrdine=? AND utenteOrdine.nicknameUtente=utente.nickname;"); }

    public String getCarrello() { return String.format("SELECT carrello.* FROM carrelloOrdine, carrello WHERE carrelloOrdine.idOrdine=? AND carrelloOrdine.idCarrello=carrello.id;");}

    public String dropUtenteOrdine() { return String.format("DELETE FROM utenteOrdine WHERE idOrdine=?;");}

    public String dropCarrelloOrdine() { return String.format("DELETE FROM carrelloOrdine WHERE idOrdine=?;");}

    public String insertUtenteOrdine(){
        return String.format("INSERT INTO utenteOrdine (nicknameUtente,idOrdine) VALUES (?,?);");
    }

    public String insertCarrelloOrdine() {
        return String.format("INSERT INTO carrelloOrdine (idCarrello,idOrdine) VALUES (?,?);");
    }

}
