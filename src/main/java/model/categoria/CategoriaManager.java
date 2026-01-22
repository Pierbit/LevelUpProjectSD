package model.categoria;

import model.corso.Corso;
import model.storage.Manager;
import model.utente.Utente;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaManager extends Manager implements CategoriaDao {

    private static final CategoriaQuery QUERY = new CategoriaQuery("categoria");

    public CategoriaManager(DataSource source) {
        super(source);
    }

    @Override
    public List<Categoria> fetchCategorie(int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectCategorie())) {
                ps.setInt(1, start);
                ps.setInt(2, end);
                ResultSet set = ps.executeQuery();
                List<Categoria> categorie = new ArrayList<>();
                while (set.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setNome(set.getString("nome"));
                    categorie.add(categoria);
                }
                set.close();
                return categorie;
            }
        }
    }

    public Categoria fetchCategoria(String nomeCategoria) throws SQLException {

        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.selectCategoria())){
                ps.setString(1,nomeCategoria);
                ResultSet rs = ps.executeQuery();
                Categoria c = null;
                if(rs.next()){
                    c = new Categoria();
                    c.setNome(rs.getString("nome"));
                }
                return c;
            }
        }
    }

    public boolean createCategoria(String nomeCategoria) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.insertCategoria())){
                ps.setString(1,nomeCategoria);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean updateCategoria(String nomeCategoria) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.updateCategoria())){
                ps.setString(1,nomeCategoria);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteCategoria(String nomeCategoria) throws SQLException {
        this.dropCorsoCategoria(nomeCategoria);
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteCategoria())){
                ps.setString(1,nomeCategoria);
                return ps.executeUpdate() > 0;
            }
        }

    }

    @Override
    public int countCategorie() throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countCategorie())) {
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public List<Corso> fetchCorsiAssociati(String nomeCategoria) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsiAssociati())) {
                ps.setString(1,nomeCategoria);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }
    }

    public boolean dropCorsoCategoria(String nomeCategoria) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteCorsoCategoria())){
                ps.setString(1,nomeCategoria);

                return ps.executeUpdate() > 0;
            }
        }
    }
}
