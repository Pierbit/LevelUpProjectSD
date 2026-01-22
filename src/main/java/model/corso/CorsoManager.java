package model.corso;

import controller.search.Condition;
import controller.validators.RegisterValidator;
import model.categoria.Categoria;
import model.ordine.OrdineDao;
import model.storage.Manager;
import model.tag.Tag;
import model.utente.Utente;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CorsoManager extends Manager implements CorsoDao {
    private CorsoQuery QUERY = new CorsoQuery("corso");

    public CorsoManager(DataSource source) {
        super(source);
    }

    @Override
    public List<Corso> fetchCorsi(int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectCorsi())) {
                ps.setInt(1, start);
                ps.setInt(2, end);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setTesto(set.getString("testo"));
                    corso.setCopertina(set.getString("copertina"));
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }
    }

    @Override
    public Corso fetchCorso(int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectCorso())) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                Corso corso = null;
                if (set.next()) {
                    corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setTesto(set.getString("testo"));
                    corso.setCopertina(set.getString("copertina"));
                }
                set.close();
                return corso;
            }
        }
    }

    @Override
    public boolean createCorso(Corso c) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertCorso())) {
                ps.setString(1, c.getNome());
                ps.setDouble(2, c.getPrezzoBase());
                ps.setString(3, c.getTesto());
                ps.setString(4, c.getCopertina());
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean updateCorso(Corso c) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.updateCorso())) {
                ps.setString(1, c.getNome());
                ps.setDouble(2, c.getPrezzoBase());
                ps.setString(3, c.getTesto());
                ps.setString(4, c.getCopertina());
                ps.setInt(5, c.getId());
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean deleteCorso(int id) throws SQLException {
        this.deleteTagAssociati(id);
        this.deleteUtentiPartecipanti(id);
        this.deleteUtenteCreatore(id);
        this.deleteCategoria(id);
        this.deleteOggetto(id);
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.deleteCorso())) {
                ps.setInt(1, id);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public int countCorsi() throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countCorsi())) {
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public List<Tag> fetchTagAssociati(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getTagAssociati())) {
                ps.setInt(1, idCorso);
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

    public boolean insertCorsoCategoria(Corso corso,String categoria) throws SQLException{
        this.deleteCategoria(corso.getId());
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertCorsoCategoria())) {
                ps.setInt(1, corso.getId());
                ps.setString(2, categoria);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean insertCorsoTag(Corso corso,String tag) throws SQLException{
        this.deleteTagAssociati(corso.getId());
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertCorsoTag())) {
                ps.setInt(1, corso.getId());
                ps.setString(2, tag);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteTagAssociati(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropTagAssociati())) {
                ps.setInt(1, idCorso);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public List<Utente> fetchUtentiPartecipanti(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getUtentiPartecipanti())) {
                ps.setInt(1, idCorso);
                ResultSet set = ps.executeQuery();
                List<Utente> utenti = new ArrayList<>();
                while (set.next()) {
                    Utente utente = new Utente();
                    utente.setNickname(set.getString("nickname"));
                    utente.setEmail(set.getString("email"));
                    utente.setPassword(set.getString("password"));
                    utenti.add(utente);
                }
                set.close();
                return utenti;
            }
        }
    }

    public boolean deleteUtentiPartecipanti(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropUtentiPartecipanti())) {
                ps.setInt(1, idCorso);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public Utente fetchUtenteCreatore(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getUtenteCreatore())) {
                ps.setInt(1, idCorso);
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

    public boolean deleteUtenteCreatore(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropUtenteCreatore())) {
                ps.setInt(1, idCorso);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public Categoria fetchCategoria(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCategoria())) {
                ps.setInt(1, idCorso);
                ResultSet set = ps.executeQuery();
                Categoria categoria = new Categoria();
                while (set.next()) {
                    categoria.setNome(set.getString("nome"));
                }
                set.close();
                return categoria;
            }
        }
    }

    @Override
    public int fetchCorsoId(String nome) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsoId())) {
                ps.setString(1,nome);
                ResultSet set = ps.executeQuery();
                Integer corsoid = 0;
                while (set.next()) {
                    corsoid = set.getInt("id");
                }
                set.close();
                return corsoid;
            }
        }
    }

    @Override
    public boolean insertCreatore(String nickname, int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertCreatore())) {
                ps.setString(1,nickname);
                ps.setInt(2,id);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean dropUtentePartecipante(int idCorso, String nickname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropUtentePartecipante())) {
                ps.setInt(1, idCorso);
                ps.setString(2,nickname);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteCategoria(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropCategoria())) {
                ps.setInt(1, idCorso);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteOggetto(int idCorso) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.dropOggetto())) {
                ps.setInt(1, idCorso);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public List<Corso> filterCorsi(String nomecategoria) throws SQLException{
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.filterCorsi())) {
                ps.setString(1,nomecategoria);
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                while (set.next()) {
                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setTesto(set.getString("testo"));
                    corso.setCopertina(set.getString("copertina"));
                    corsi.add(corso);
                }
                set.close();
                return corsi;
            }
        }

    }

    public List<Corso> search(List<Condition> conditions) throws  SQLException {
        try(Connection conn = source.getConnection()) {
            String query = QUERY.search(conditions);
            try(PreparedStatement ps = conn.prepareStatement(query)){
                ResultSet set = ps.executeQuery();
                List<Corso> corsi = new ArrayList<>();
                int i = 0;
                while(set.next()){

                    Corso corso = new Corso();
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setTesto(set.getString("testo"));
                    corso.setCopertina(set.getString("copertina"));
                    i++;
                    corsi.add(corso);

                }
                System.out.println(i);
                return corsi;
            }
        }

    }

    public boolean insertPartecipante(String nickname,int corsoid) throws SQLException{
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertPartecipante())) {
                ps.setString(1, nickname);
                ps.setInt(2,corsoid);
                return ps.executeUpdate() > 0;
            }
        }

    }
}
