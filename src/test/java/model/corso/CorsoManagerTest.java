package model.corso;

import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import controller.search.Condition;
import controller.search.Operator;
import java.util.List;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CorsoManagerTest {

    private final CorsoManager manager = new CorsoManager(ConPool.getDataSource());
    private static final String TEST_NOME = "Test Corso";

    @AfterEach
    void cleanup() throws SQLException {
        int id = manager.fetchCorsoId(TEST_NOME);
        if (id != 0) {
            manager.deleteCorso(id);
        }
    }

    //Testing create and fetch
    @Test
    void createAndFetchCorso() throws SQLException {
        Corso corso = new Corso();
        corso.setNome(TEST_NOME);
        corso.setPrezzoBase(29.99);
        corso.setTesto("test text");
        corso.setCopertina(null);

        boolean created = manager.createCorso(corso);
        assertTrue(created);

        int id = manager.fetchCorsoId(TEST_NOME);
        assertNotEquals(0, id);

        Corso fetched = manager.fetchCorso(id);
        assertNotNull(fetched);
        assertEquals(TEST_NOME, fetched.getNome());
        assertEquals(29.99, fetched.getPrezzoBase());
    }

    //Testing update works
    @Test
    void updateCorsoChangesPrice() throws SQLException {
        Corso corso = new Corso();
        corso.setNome(TEST_NOME);
        corso.setPrezzoBase(20.00);
        corso.setTesto("original text");
        corso.setCopertina(null);
        manager.createCorso(corso);

        int id = manager.fetchCorsoId(TEST_NOME);
        corso.setId(id);
        corso.setPrezzoBase(35.50);

        boolean updated = manager.updateCorso(corso);
        assertTrue(updated);

        Corso fetched = manager.fetchCorso(id);
        assertEquals(35.50, fetched.getPrezzoBase());
    }

    //Testing delete works
    @Test
    void deleteCorsoRemovesIt() throws SQLException {
        Corso corso = new Corso();
        corso.setNome(TEST_NOME);
        corso.setPrezzoBase(15.00);
        corso.setTesto("To remove");
        corso.setCopertina(null);
        manager.createCorso(corso);

        int id = manager.fetchCorsoId(TEST_NOME);
        boolean deleted = manager.deleteCorso(id);
        assertTrue(deleted);

        Corso fetched = manager.fetchCorso(id);
        assertNull(fetched);
    }

    //Testing counts works
    @Test
    void countIncreasesAfterCreate() throws SQLException {
        int before = manager.countCorsi();

        Corso corso = new Corso();
        corso.setNome(TEST_NOME);
        corso.setPrezzoBase(10.00);
        corso.setTesto("Count");
        corso.setCopertina(null);
        manager.createCorso(corso);

        int after = manager.countCorsi();
        assertEquals(before + 1, after);
    }

    //Testing if fetch nonexistent course returns null
    @Test
    void fetchNonExistentCorsoReturnsNull() throws SQLException {
        Corso fetched = manager.fetchCorso(9999999);
        assertNull(fetched);
    }

    //Testing course search + added tags and category
    @Test
    void searchFindsCourseWhenFullyAssociated() throws SQLException {
        Corso corso = new Corso();
        corso.setNome(TEST_NOME);
        corso.setPrezzoBase(25.00);
        corso.setTesto("search test");
        corso.setCopertina(null);
        manager.createCorso(corso);
        int id = manager.fetchCorsoId(TEST_NOME);
        corso.setId(id);

        manager.insertCreatore("marcorossi", id);
        manager.insertCorsoTag(corso, "principiante");
        manager.insertCorsoCategoria(corso, "tecnologia");

        List<Condition> conditions = List.of(
                new Condition("nome", Operator.MATCH, "Test Corso", "corso")
        );

        List<Corso> results = manager.search(conditions);

        assertTrue(results.stream().anyMatch(c -> c.getId() == id));
    }

    //Testing course search without added tags or category
    @Test
    void courseCreatedByUserFlowIsUnsearchable() throws SQLException {
        Corso corso = new Corso();
        corso.setNome(TEST_NOME);
        corso.setPrezzoBase(25.00);
        corso.setTesto("search test");
        corso.setCopertina(null);
        manager.createCorso(corso);
        int id = manager.fetchCorsoId(TEST_NOME);

        List<Condition> conditions = List.of(
                new Condition("nome", Operator.MATCH, "Test Corso", "corso")
        );

        List<Corso> results = manager.search(conditions);

        assertTrue(results.stream().noneMatch(c -> c.getId() == id));
    }
}