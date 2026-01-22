package model.carrello;


import model.oggetto.Oggetto;

import java.sql.SQLException;
import java.util.List;

public interface CarrelloDao {
    Carrello fetchCarrello(int id) throws SQLException;
    boolean createCarrello(Carrello carrello) throws SQLException;
    boolean deleteCarrello(int id) throws SQLException;
    List<Oggetto> fetchOggetti(int id) throws SQLException;
}
