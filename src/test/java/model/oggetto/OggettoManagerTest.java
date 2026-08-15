package model.oggetto;

import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import model.corso.Corso;
import model.corso.CorsoManager;
import model.carrello.Carrello;
import model.carrello.CarrelloManager;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OggettoManagerTest {

    private final OggettoManager manager = new OggettoManager(ConPool.getDataSource());
    private static final int TEST_ID = 444444;

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteOggetto(TEST_ID);
    }

    //Testing create and fetch
    @Test
    void createAndFetchOggetto() throws SQLException {
        Oggetto oggetto = new Oggetto();
        oggetto.setId(TEST_ID);
        oggetto.setPrezzo(19.99);

        boolean created = manager.createOggetto(oggetto);
        assertTrue(created);

        Oggetto fetched = manager.fetchOggetto(TEST_ID);
        assertNotNull(fetched);
        assertEquals(19.99, fetched.getPrezzo());
    }

    //Testing update
    @Test
    void updateOggettoChangesPrice() throws SQLException {
        Oggetto oggetto = new Oggetto();
        oggetto.setId(TEST_ID);
        oggetto.setPrezzo(10.00);
        manager.createOggetto(oggetto);

        oggetto.setPrezzo(25.50);
        boolean updated = manager.updateOggetto(oggetto);
        assertTrue(updated);

        Oggetto fetched = manager.fetchOggetto(TEST_ID);
        assertEquals(25.50, fetched.getPrezzo());
    }

    //Testing an oggetto can be deleted successfully
    @Test
    void deleteOggettoRemovesIt() throws SQLException {
        Oggetto oggetto = new Oggetto();
        oggetto.setId(TEST_ID);
        oggetto.setPrezzo(5.00);
        manager.createOggetto(oggetto);

        boolean deleted = manager.deleteOggetto(TEST_ID);
        assertTrue(deleted);

        Oggetto fetched = manager.fetchOggetto(TEST_ID);
        assertNull(fetched);
    }

    //Testing fetching a non-existent oggetto returns null
    @Test
    void fetchNonExistentOggettoReturnsNull() throws SQLException {
        Oggetto fetched = manager.fetchOggetto(9999999);
        assertNull(fetched);
    }

    //Testing insertOggettoCarrello correctly links an oggetto to a cart
    @Test
    void insertOggettoCarrelloLinksSuccessfully() throws SQLException {
        CarrelloManager carrelloManager = new CarrelloManager(ConPool.getDataSource());
        Carrello carrello = new Carrello();
        carrello.setId(333333);
        carrelloManager.createCarrello(carrello);

        Oggetto oggetto = new Oggetto();
        oggetto.setId(TEST_ID);
        oggetto.setPrezzo(10.00);
        manager.createOggetto(oggetto);

        boolean linked = manager.insertOggettoCarrello(TEST_ID, 333333);
        assertTrue(linked);

        manager.deleteOggetoCarrello(TEST_ID);
        carrelloManager.deleteCarrello(333333);
    }

    //Testing insertCorsoOggetto correctly links an oggetto to a course
    @Test
    void insertCorsoOggettoLinksSuccessfully() throws SQLException {
        CorsoManager corsoManager = new CorsoManager(ConPool.getDataSource());
        Corso corso = new Corso();
        corso.setNome("test_corso_for_oggetto");
        corso.setPrezzoBase(10.00);
        corso.setTesto("temp");
        corso.setCopertina(null);
        corsoManager.createCorso(corso);
        int corsoId = corsoManager.fetchCorsoId("test_corso_for_oggetto");

        Oggetto oggetto = new Oggetto();
        oggetto.setId(TEST_ID);
        oggetto.setPrezzo(10.00);
        manager.createOggetto(oggetto);

        boolean linked = manager.insertCorsoOggetto(corsoId, TEST_ID);
        assertTrue(linked);

        corsoManager.deleteCorso(corsoId);
    }
}
