package model.oggetto;

import model.carrello.Carrello;
import model.corso.Corso;
import model.storage.Manager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OggettoManager extends Manager implements OggettoDao{

    public static final OggettoQuery QUERY = new OggettoQuery("oggetto");

    public OggettoManager(DataSource source) {
        super(source);
    }

    public Oggetto fetchOggetto(int idOggetto) throws SQLException {

        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.selectOggetto())){
                ps.setInt(1,idOggetto);
                ResultSet rs = ps.executeQuery();
                Oggetto o = null;
                if(rs.next()){
                    o = new Oggetto();
                    o.setId(rs.getInt(1));
                    o.setPrezzo(rs.getDouble(2));
                }
                return o;
            }
        }

    }

    public boolean createOggetto(Oggetto oggetto) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.insertOggetto())){
                ps.setInt(1,oggetto.getId());
                ps.setDouble(2, oggetto.getPrezzo());
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteOggetto(int id) throws SQLException {
        try(Connection conn = source.getConnection()){
            deleteCorsoOggetto(id);
            deleteOggetoCarrello(id);
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteOggetto())){
                ps.setInt(1,id);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean updateOggetto(Oggetto oggetto) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.updateOggetto())){
                ps.setDouble(1, oggetto.getPrezzo());
                ps.setInt(2, oggetto.getId());
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public Corso fetchCorso(int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorso())) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                Corso corso = new Corso();
                while (set.next()) {
                    corso.setId(set.getInt("id"));
                    corso.setNome(set.getString("nome"));
                    corso.setPrezzoBase(set.getDouble("prezzoBase"));
                    corso.setCopertina(set.getString("copertina"));
                    corso.setTesto(set.getString("testo"));
                }
                set.close();
                return corso;
            }
        }
    }

    public boolean insertCorsoOggetto(int idCorso,int idOggetto) throws SQLException{
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.insertCorsoOggetto())){
                ps.setInt(1, idCorso);
                ps.setInt(2, idOggetto);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteCorsoOggetto(int idOggetto) throws SQLException{
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteCorsoOggetto())){
                ps.setInt(1, idOggetto);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean insertOggettoCarrello(int idoggetto,int idcarrello) throws SQLException{
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.insertOggettoCarrello())){
                ps.setInt(1, idoggetto);
                ps.setInt(2, idcarrello);
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteOggetoCarrello(int oggettoid) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteOggettoCarrello())){
                ps.setInt(1,oggettoid);
                return ps.executeUpdate() > 0;
            }
        }
    }
}
