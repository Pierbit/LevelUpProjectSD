package model.utente;

import model.carrello.Carrello;
import model.carrello.CarrelloManager;
import model.corso.Corso;
import model.ordine.Ordine;
import model.storage.ConPool;
import model.storage.Manager;
import model.tag.Tag;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtenteManager extends Manager implements UtenteDao {
    private static final UtenteQuery QUERY = new UtenteQuery("utente");

    public UtenteManager(DataSource source) {
        super(source);
    }

    @Override
    public int countUtenti() throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countUtenti())) {
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public int countCorsiPartecipati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countCorsiPartecipati())) {
                ps.setString(1,nickname);
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public int countCorsiPubblicati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countCorsiPubblicati())) {
                ps.setString(1,nickname);
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public int countOrdiniEffettuati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countOrdiniEffettuati())) {
                ps.setString(1,nickname);
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public List<Utente> fetchUtenti(int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectUtenti())) {
                ps.setInt(1, start);
                ps.setInt(2, end);
                ResultSet set = ps.executeQuery();
                List<Utente> utenti = new ArrayList<>();
                while (set.next()) {
                    Utente utente = new Utente();
                    utente.setNickname(set.getString("nickname"));
                    utente.setEmail(set.getString("email"));
                    utente.setPassword(set.getString("password"));
                    utente.setFotoProfilo(set.getString("fotoProfilo"));
                    utente.setBiografia(set.getString("biografia"));
                    utente.setManager(set.getBoolean("manager"));
                    utenti.add(utente);
                }
                set.close();
                return utenti;
            }
        }
    }

    @Override
    public Utente fetchUtente(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectUtente())) {
                ps.setString(1, nickname);
                ResultSet set = ps.executeQuery();
                Utente utente = null;
                if (set.next()) {
                    utente = new Utente();
                    utente.setNickname(set.getString("nickname"));
                    utente.setEmail(set.getString("email"));
                    utente.setPassword(set.getString("password"));
                    utente.setFotoProfilo(set.getString("fotoProfilo"));
                    utente.setBiografia(set.getString("biografia"));
                    utente.setManager(set.getBoolean("manager"));
                }
                return utente;
            }
        }
    }

    @Override
    public boolean createUtente(Utente utente) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertUtente())) {
                ps.setString(1, utente.getNickname());
                ps.setString(2, utente.getEmail());
                ps.setString(3, utente.getPassword());
                ps.setString(4, utente.getFotoProfilo());
                ps.setString(5, utente.getBiografia());
                ps.setBoolean(6, utente.getManager());

                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean updateUtente(Utente utente) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.updateUtente())) {
                ps.setString(1,utente.getNickname());
                ps.setString(2, utente.getEmail());
                ps.setString(3, utente.getPassword());
                ps.setString(4,utente.getFotoProfilo());
                ps.setString(5,utente.getBiografia());
                ps.setBoolean(6, utente.getManager());
                ps.setString(7,utente.getNickname());
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean deleteUtente(String nickname) throws SQLException {
        this.deleteTags(nickname);
        this.deleteCorsiPartecipati(nickname);
        this.deleteCorsiPubblicati(nickname);
        this.deleteOrdini(nickname);
        this.deleteCarrello(nickname);
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.deleteUtente())) {
                ps.setString(1, nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public List<Tag> fetchTags(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getTags())) {
                ps.setString(1, nickname);
                ResultSet set = ps.executeQuery();
                List<Tag> tags = new ArrayList<>();
                while (set.next()) {
                    Tag tag = new Tag();
                    tag.setNome(set.getString("nome"));
                    tags.add(tag);
                }
                set.close();
                return tags;
            }
        }
    }

    public boolean deleteTags(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropTags())) {
                ps.setString(1, nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public List<Corso> fetchCorsiPartecipati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsiPartecipati())) {
                ps.setString(1, nickname);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setCopertina(set.getString("copertina"));
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }
    }

    @Override
    public List<Corso> fetchCorsiPartecipatiLimit(String nickname, int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsiPartecipatiLimit())) {
                ps.setString(1, nickname);
                ps.setInt(2,start);
                ps.setInt(3,end);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setCopertina(set.getString("copertina"));
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }
    }

    public boolean deleteCorsiPartecipati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropCorsiPartecipati())) {
                ps.setString(1, nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public List<Corso> fetchCorsiPubblicati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsiPubblicati())) {
                ps.setString(1, nickname);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                Utente utente = new Utente();
                utente.setNickname(nickname);
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setCopertina(set.getString("copertina"));
                    corso.setUtenteCreatore(utente);
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }
    }

    @Override
    public List<Corso> fetchCorsiPubblicatiLimit(String nickname, int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsiPubblicatiLimit())) {
                ps.setString(1, nickname);
                ps.setInt(2,start);
                ps.setInt(3,end);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setCopertina(set.getString("copertina"));
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }
    }

    public boolean deleteCorsiPubblicati(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropCorsiPubblicati())) {
                ps.setString(1, nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public List<Ordine> fetchOrdini(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getOrdini())) {
                ps.setString(1, nickname);
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
    public List<Ordine> fetchOrdiniLimit(String nickname, int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getOrdiniLimit())) {
                ps.setString(1, nickname);
                ps.setInt(2,start);
                ps.setInt(3,end);
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

    public boolean deleteOrdini(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropOrdini())) {
                ps.setString(1, nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean setCarrello(String nicknameutente, int idcarrello) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertCarrello())) {
                ps.setString(1, nicknameutente);
                ps.setInt(2, idcarrello);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public Carrello fetchCarrello(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCarrello())) {
                ps.setString(1, nickname);
                ResultSet rs = ps.executeQuery();
                Carrello carrello = null;
                if (rs.next()) {
                    carrello = new Carrello();
                    carrello.setId(rs.getInt("id"));
                }
                return carrello;
            }
        }
    }

    public boolean deleteCarrello(String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropCarrello())) {
                ps.setString(1, nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }
}
