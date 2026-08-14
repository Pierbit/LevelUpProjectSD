package model.ordine;

import model.carrello.Carrello;
import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrdineManagerTest {

    private final OrdineManager manager = new OrdineManager(ConPool.getDataSource());
    private static final int TEST_ID = 555555;

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteOrdine(TEST_ID);
    }

    //Testing order create and fetch
    @Test
    void createAndFetchOrdine() throws SQLException {
        Ordine ordine = new Ordine();
        ordine.setId(TEST_ID);
        ordine.setData(LocalDate.now());

        boolean created = manager.createOrdine(ordine);
        assertTrue(created);

        Ordine fetched = manager.fetchOrdine(TEST_ID);
        assertNotNull(fetched);
        assertEquals(TEST_ID, fetched.getId());
    }

    //Testing order delete
    @Test
    void deleteOrdineRemovesIt() throws SQLException {
        Ordine ordine = new Ordine();
        ordine.setId(TEST_ID);
        ordine.setData(LocalDate.now());
        manager.createOrdine(ordine);

        boolean deleted = manager.deleteOrdine(TEST_ID);
        assertTrue(deleted);

        Ordine fetched = manager.fetchOrdine(TEST_ID);
        assertNull(fetched);
    }

    //Testing fetchOrdini returns a non-null non-throwing list within a range
    @Test
    void fetchOrdiniReturnsListWithinRange() throws SQLException {
        Ordine ordine = new Ordine();
        ordine.setId(TEST_ID);
        ordine.setData(LocalDate.now());
        manager.createOrdine(ordine);

        List<Ordine> ordini = manager.fetchOrdini(0, 100);
        assertNotNull(ordini);
        assertTrue(ordini.stream().anyMatch(o -> o.getId() == TEST_ID));
    }
}
