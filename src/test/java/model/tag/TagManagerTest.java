package model.tag;

import model.corso.Corso;
import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagManagerTest {

    private final TagManager manager = new TagManager(ConPool.getDataSource());
    private static final String TEST_NOME = "test_tag_junit";

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteTag(TEST_NOME);
    }

    //Testing a tag can be created and fetched back
    @Test
    void createAndFetchTag() throws SQLException {
        Tag tag = new Tag();
        tag.setNome(TEST_NOME);
        boolean created = manager.createTag(tag);
        assertTrue(created);

        Tag fetched = manager.fetchTag(TEST_NOME);
        assertNotNull(fetched);
        assertEquals(TEST_NOME, fetched.getNome());
    }

    //Testing fetching a non-existent tag returns null
    @Test
    void fetchNonExistentTagReturnsNull() throws SQLException {
        Tag fetched = manager.fetchTag("does_not_exist_junit");
        assertNull(fetched);
    }

    //Testing a tag can be deleted successfully
    @Test
    void deleteTagRemovesIt() throws SQLException {
        Tag tag = new Tag();
        tag.setNome(TEST_NOME);
        manager.createTag(tag);

        boolean deleted = manager.deleteTag(TEST_NOME);
        assertTrue(deleted);

        Tag fetched = manager.fetchTag(TEST_NOME);
        assertNull(fetched);
    }

    //Testing countTags works
    @Test
    void countIncreasesAfterCreate() throws SQLException {
        int before = manager.countTags();
        Tag tag = new Tag();
        tag.setNome(TEST_NOME);
        manager.createTag(tag);
        int after = manager.countTags();
        assertEquals(before + 1, after);
    }

    //Testing fetchTags works (non-null non-throwing) list
    @Test
    void fetchTagsReturnsListWithinRange() throws SQLException {
        Tag tag = new Tag();
        tag.setNome(TEST_NOME);
        manager.createTag(tag);

        List<Tag> tags = manager.fetchTags(0, manager.countTags());
        assertNotNull(tags);
        assertTrue(tags.stream().anyMatch(t -> t.getNome().equals(TEST_NOME)));
    }

    //Testing fetchCorsiAssociati returns an empty list for a tag with no associated courses
    @Test
    void fetchCorsiAssociatiReturnsEmptyForUnassociatedTag() throws SQLException {
        Tag tag = new Tag();
        tag.setNome(TEST_NOME);
        manager.createTag(tag);

        List<Corso> corsi = manager.fetchCorsiAssociati(TEST_NOME);
        assertNotNull(corsi);
        assertTrue(corsi.isEmpty());
    }

}
