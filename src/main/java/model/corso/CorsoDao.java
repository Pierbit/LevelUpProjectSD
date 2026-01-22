package model.corso;

import controller.search.Condition;
import model.categoria.Categoria;
import model.tag.Tag;
import model.utente.Utente;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CorsoDao {
    List<Corso> fetchCorsi(int start, int end) throws SQLException;
    Corso fetchCorso(int id) throws SQLException;
    boolean createCorso(Corso c) throws SQLException;
    boolean updateCorso(Corso c) throws SQLException;
    boolean deleteCorso(int id) throws SQLException;
    int countCorsi() throws SQLException;
    List<Tag> fetchTagAssociati(int idCorso) throws SQLException;
    List<Utente> fetchUtentiPartecipanti(int idCorso) throws SQLException;
    Utente fetchUtenteCreatore(int idCorso) throws SQLException;
    Categoria fetchCategoria(int idCorso) throws SQLException;
    int fetchCorsoId(String nome) throws SQLException;
    boolean insertCreatore(String nickname,int id) throws SQLException;
    boolean dropUtentePartecipante(int idCorso,String nickname) throws SQLException;
    List<Corso> search(List<Condition> conditions) throws SQLException;
}