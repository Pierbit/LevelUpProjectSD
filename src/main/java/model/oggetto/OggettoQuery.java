package model.oggetto;

import model.storage.TableQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OggettoQuery extends TableQuery {

    public OggettoQuery(String table) {
        super(table);
    }

    public String selectOggetto(){
        return String.format("SELECT * FROM %s WHERE id=?;", this.table);
    }

    public String insertOggetto(){
        return String.format("INSERT INTO %s (id,prezzo) VALUES(?,?);", this.table);
    }

    public String updateOggetto(){
        return String.format("UPDATE %s SET prezzo=? WHERE id=?;", this.table);
    }
    public String deleteOggetto(){
        return String.format("DELETE FROM %s WHERE id=?;", this.table);
    }
    public String getCorso() { return String.format("SELECT corso.* FROM corsoOggetto, corso WHERE corsoOggetto.idOggetto=? AND corsoOggetto.idCorso=corso.id;");}
    public String insertCorsoOggetto(){
        return String.format("INSERT INTO corsoOggetto (idCorso,idOggetto) VALUES(?,?);");
    }
    public String deleteCorsoOggetto(){
        return String.format("DELETE FROM corsoOggetto WHERE idOggetto=?;");
    }
    public String insertOggettoCarrello(){
        return String.format("INSERT INTO oggettoCarrello (idOggetto, idCarrello) VALUES (?, ?);");
    }
    public String deleteOggettoCarrello(){
        return String.format("DELETE FROM oggettoCarrello WHERE idOggetto=?;");
    }

}
