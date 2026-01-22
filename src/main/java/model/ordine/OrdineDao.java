package model.ordine;

import model.carrello.Carrello;
import model.utente.Utente;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface OrdineDao {
    List<Ordine> fetchOrdini(int start, int end) throws SQLException;
    Ordine fetchOrdine(int id) throws SQLException;
    boolean createOrdine(Ordine ordine) throws SQLException;
    boolean updateOrdine(Ordine ordine) throws SQLException;
    boolean deleteOrdine(int id) throws SQLException;
    int countOrdini() throws SQLException;
    Utente fetchUtente(int id) throws SQLException;
    Carrello fetchCarrello(int id) throws SQLException;
}
