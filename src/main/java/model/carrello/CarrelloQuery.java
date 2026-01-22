package model.carrello;

import model.storage.TableQuery;

public class CarrelloQuery extends TableQuery {

    public CarrelloQuery(String table) {
        super(table);
    }

    public String selectCarrello(){
        return String.format("SELECT * FROM %s WHERE id=?;", this.table);
    }

    public String insertCarrello(){
        return String.format("INSERT INTO %s VALUES(?);", this.table);
    }

    public String deleteCarrello(){
        return String.format("DELETE FROM %s WHERE id=?;", this.table);
    }

    public String getOggetti() {return String.format("SELECT oggetto.* FROM oggettoCarrello, oggetto WHERE oggettoCarrello.idCarrello=? AND oggettoCarrello.idOggetto=oggetto.id;");}

    public String deleteUtenteCarrello(){
        return String.format("DELETE FROM utenteCarrello WHERE nicknameUtente=?;");
    }

}
