package model.oggetto;

import model.corso.Corso;

import java.sql.SQLException;

public interface OggettoDao {
    Oggetto fetchOggetto(int idOggetto) throws SQLException;
    boolean createOggetto(Oggetto oggetto) throws SQLException;
    boolean deleteOggetto(int id) throws SQLException;
    boolean updateOggetto(Oggetto oggetto) throws SQLException;
    Corso fetchCorso(int id) throws SQLException;
}
