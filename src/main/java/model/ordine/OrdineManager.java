package model.ordine;

import model.carrello.Carrello;
import model.storage.Manager;
import model.utente.Utente;

import javax.sql.DataSource;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdineManager extends Manager implements OrdineDao {
    private static final OrdineQuery QUERY = new OrdineQuery("ordine");

    public OrdineManager(DataSource source) {
        super(source);
    }

    public int countOrdini() throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countOrdini())) {
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public List<Ordine> fetchOrdini(int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectOrdini())) {
                ps.setInt(1, start);
                ps.setInt(2, end);
                ResultSet set = ps.executeQuery();
                List<Ordine> ordini = new ArrayList<>();
                while (set.next()) {
                    Ordine ordine = new Ordine();
                    ordine.setId(set.getInt("id"));
                    ordine.setData(set.getDate("dataOrdine").toLocalDate());
                    ordini.add(ordine);
                }
                set.close();
                return ordini;
            }
        }
    }

    @Override
    public Ordine fetchOrdine(int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectOrdine())) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                Ordine ordine = null;
                if (set.next()) {
                    ordine = new Ordine();
                    ordine.setId(set.getInt("id"));
                    ordine.setData(set.getDate("dataOrdine").toLocalDate());
                }
                set.close();
                return ordine;
            }
        }
    }

    @Override
    public boolean createOrdine(Ordine ordine) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertOrdine())) {
                ps.setInt(1,ordine.getId());
                ps.setDate(2, Date.valueOf(ordine.getData()));
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean updateOrdine(Ordine ordine) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.updateOrdine())) {
                ps.setInt(1, ordine.getId());
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean deleteOrdine(int id) throws SQLException {
        this.dropUtenteOrdine(id);
        this.dropCarrelloOrdine(id);
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.deleteOrdine())) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public Utente fetchUtente(int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getUtente())) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                Utente utente = new Utente();
                while (set.next()) {
                    utente.setNickname(set.getString("nickname"));
                    utente.setEmail(set.getString("email"));
                    utente.setPassword(set.getString("password"));
                }
                set.close();
                return utente;
            }
        }
    }

    @Override
    public Carrello fetchCarrello(int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCarrello())) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                Carrello carrello = new Carrello();
                while (set.next()) {
                    carrello.setId(set.getInt("id"));
                }
                set.close();
                return carrello;
            }
        }
    }

    public boolean dropUtenteOrdine(int idOrdine) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropUtenteOrdine())) {
                ps.setInt(1, idOrdine);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean dropCarrelloOrdine(int idOrdine) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropCarrelloOrdine())) {
                ps.setInt(1, idOrdine);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean insertUtenteOrdine(String username,int ordineid) throws SQLException{
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertUtenteOrdine())) {
                ps.setString(1,username);
                ps.setInt(2,ordineid);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean insertCarrelloOrdine(int carrelloid,int ordineid) throws SQLException{
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertCarrelloOrdine())) {
                ps.setInt(1,carrelloid);
                ps.setInt(2,ordineid);
                return ps.executeUpdate() > 0;
            }
        }
    }
}