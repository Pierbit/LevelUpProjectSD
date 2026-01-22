package model.tag;

import model.corso.Corso;

import java.sql.SQLException;
import java.util.List;

public interface TagDao {
    List<Tag> fetchTags(int start, int end) throws SQLException;
    Tag fetchTag(String nome) throws SQLException;
    boolean createTag(Tag tag) throws SQLException;
    boolean updateTag(Tag tag) throws SQLException;
    boolean deleteTag(String nome) throws SQLException;
    int countTags() throws SQLException;
    List<Corso> fetchCorsiAssociati(String tagname) throws SQLException;

}
