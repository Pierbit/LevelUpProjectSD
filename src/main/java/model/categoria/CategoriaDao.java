package model.categoria;

import model.corso.Corso;
import model.utente.Utente;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoriaDao {
    List<Categoria> fetchCategorie(int start, int end) throws SQLException;
    Categoria fetchCategoria(String nomeCategoria) throws SQLException;
    boolean createCategoria(String nomeCategoria) throws SQLException;
    boolean updateCategoria(String nomeCategoria) throws SQLException;
    boolean deleteCategoria(String nomeCategoria) throws SQLException;
    int countCategorie() throws SQLException;
    List<Corso> fetchCorsiAssociati(String nomeCategoria) throws SQLException;
}
