package model.carrello;
import model.oggetto.Oggetto;
import model.storage.Manager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrelloManager extends Manager implements CarrelloDao{

    private static final CarrelloQuery QUERY = new CarrelloQuery("carrello");

    public CarrelloManager(DataSource source) {
        super(source);
    }

    public Carrello fetchCarrello(int id) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.selectCarrello())){
                ps.setInt(1,id);
                ResultSet rs = ps.getResultSet();
                Carrello c = null;
                if(rs.next()){
                    c = new Carrello();
                    c.setId(rs.getInt(id));
                }
                return c;
            }
        }
    }

    public boolean createCarrello(Carrello carrello) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.insertCarrello())){
                ps.setInt(1, carrello.getId());
                return ps.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteCarrello(int id) throws SQLException {
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteCarrello())){
                ps.setInt(1,id);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public List<Oggetto> fetchOggetti(int id) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getOggetti())) {
                ps.setInt(1, id);
                ResultSet set = ps.executeQuery();
                ArrayList<Oggetto> oggetti = new ArrayList<>();
                while (set.next()) {
                    Oggetto oggetto = new Oggetto();
                    oggetto.setId(set.getInt("id"));
                    oggetto.setPrezzo(set.getDouble("prezzo"));
                    oggetti.add(oggetto);
                }
                set.close();
                return oggetti;
            }
        }
    }

    public boolean deleteUtenteCarrello(String nickname) throws SQLException{
        try(Connection conn = source.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteUtenteCarrello())){
                ps.setString(1,nickname);
                return ps.executeUpdate() > 0;
            }
        }

    }

}
