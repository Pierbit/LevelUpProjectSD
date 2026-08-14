package model.carrello;

import model.oggetto.Oggetto;
import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarrelloManagerTest {

    private final CarrelloManager manager = new CarrelloManager(ConPool.getDataSource());

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteCarrello(888888);
        manager.deleteCarrello(777777);
        manager.deleteCarrello(666666);
    }

    //Testing cart creation
    @Test
    void createCarrelloSucceeds() throws SQLException {
        Carrello carrello = new Carrello();
        carrello.setId(888888);
        boolean created = manager.createCarrello(carrello);
        assertTrue(created);
    }

    //Testing empty cart
    @Test
    void fetchOggettiReturnsEmptyForNewCarrello() throws SQLException {
        Carrello carrello = new Carrello();
        carrello.setId(777777);
        manager.createCarrello(carrello);

        List<Oggetto> oggetti = manager.fetchOggetti(777777);
        assertNotNull(oggetti);
        assertTrue(oggetti.isEmpty());

    }

    //Testing delete cart
    @Test
    void deleteCarrelloRemovesIt() throws SQLException {
        Carrello carrello = new Carrello();
        carrello.setId(666666);
        manager.createCarrello(carrello);

        boolean deleted = manager.deleteCarrello(666666);
        assertTrue(deleted);
    }
}