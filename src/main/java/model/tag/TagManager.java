package model.tag;

import model.corso.Corso;
import model.ordine.Ordine;
import model.storage.Manager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagManager extends Manager implements TagDao {
    private TagQuery QUERY = new TagQuery("tag");

    public TagManager(DataSource source) {
        super(source);
    }

    @Override
    public List<Tag> fetchTags(int start, int end) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectTags())) {
                ps.setInt(1, start);
                ps.setInt(2, end);
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

    @Override
    public Tag fetchTag(String nome) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.selectTag())) {
                ps.setString(1, nome);
                ResultSet set = ps.executeQuery();
                Tag tag = null;
                if (set.next()) {
                    tag = new Tag();
                    tag.setNome(set.getString("nome"));
                }
                set.close();
                return tag;
            }
        }
    }

    @Override
    public boolean createTag(Tag tag) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.insertTag())) {
                ps.setString(1, tag.getNome());
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public boolean updateTag(Tag tag) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteTag(String nome) throws SQLException {
        this.dropCorsoTag(nome);
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.deleteTag())) {
                ps.setString(1, nome);
                return ps.executeUpdate() > 0;
            }
        }
    }

    @Override
    public int countTags() throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.countTags())) {
                ResultSet set = ps.executeQuery();
                int count = -1;
                if (set.next())
                    count = set.getInt("COUNT(*)");
                return count;
            }
        }
    }

    @Override
    public List<Corso> fetchCorsiAssociati(String tagname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(QUERY.getCorsiAssociati())) {
                ps.setString(1, tagname);
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

    public boolean dropCorsoTag(String tagname) throws SQLException {
        try (Connection conn = source.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement(QUERY.deleteCorsoTag())){
                ps.setString(1,tagname);

                return ps.executeUpdate() > 0;
            }
        }
    }
}
