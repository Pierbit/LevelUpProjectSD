package model.utente;

import model.corso.Corso;
import model.ordine.Ordine;
import model.tag.Tag;

import java.sql.SQLException;
import java.util.List;

public interface UtenteDao {
    int countUtenti() throws SQLException;
    int countCorsiPartecipati(String nickname) throws SQLException;
    int countCorsiPubblicati(String nickname) throws  SQLException;
    int countOrdiniEffettuati(String nickname) throws SQLException;
    List<Utente> fetchUtenti(int start, int end) throws SQLException;
    Utente fetchUtente(String nickname) throws SQLException;
    boolean createUtente(Utente utente) throws SQLException;
    boolean updateUtente(Utente utente) throws SQLException;
    boolean deleteUtente(String nickname) throws SQLException;
    List<Tag> fetchTags(String nickname) throws SQLException;
    List<Corso> fetchCorsiPartecipati(String nickname) throws SQLException;
    List<Corso> fetchCorsiPartecipatiLimit(String nickname,int start,int end) throws SQLException;
    List<Corso> fetchCorsiPubblicati(String nickname) throws SQLException;
    List<Corso> fetchCorsiPubblicatiLimit(String nickname,int start,int end) throws SQLException;
    List<Ordine> fetchOrdini(String nickname) throws SQLException;
    List<Ordine> fetchOrdiniLimit(String nickname,int start,int end) throws SQLException;
}
