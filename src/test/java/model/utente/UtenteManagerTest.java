package model.utente;

import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UtenteManagerTest {

    private final UtenteManager manager = new UtenteManager(ConPool.getDataSource());
    private static final String TEST_NICKNAME = "test_user_junit";
    private static final String TEST_NICKNAME1 = "test_user_junit_1";

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteUtente(TEST_NICKNAME);
    }

    //Testing create and fetch
    @Test
    void createAndFetchUser() throws SQLException {
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("testjunit@test.com");
        utente.setPassword("Password123");
        utente.setManager(false);

        boolean created = manager.createUtente(utente);
        assertTrue(created);

        Utente fetched = manager.fetchUtente(TEST_NICKNAME);
        assertNotNull(fetched);
        assertEquals(TEST_NICKNAME, fetched.getNickname());
        assertEquals("testjunit@test.com", fetched.getEmail());
    }

    //Testing fetch for null user
    @Test
    void fetchNonExistentUserReturnsNull() throws SQLException {
        Utente fetched1 = manager.fetchUtente(TEST_NICKNAME1);
        assertNull(fetched1);
    }

    //Testing update works
    @Test
    void updateUserChangesFields() throws SQLException {
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("original@test.com");
        utente.setPassword("Password123");
        utente.setManager(false);
        manager.createUtente(utente);

        utente.setEmail("updated@test.com");
        boolean updated = manager.updateUtente(utente);
        assertTrue(updated);

        Utente fetched = manager.fetchUtente(TEST_NICKNAME);
        assertEquals("updated@test.com", fetched.getEmail());
    }

    //Testing delete works
    @Test
    void deleteUserRemovesThem() throws SQLException {
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("temp@test.com");
        utente.setPassword("Password123");
        utente.setManager(false);
        manager.createUtente(utente);

        boolean deleted = manager.deleteUtente(TEST_NICKNAME);
        assertTrue(deleted);

        Utente fetched = manager.fetchUtente(TEST_NICKNAME);
        assertNull(fetched);
    }

    //Testing SQLException is thrown on duplicate insert
    @Test
    void duplicateNicknameThrowsException() throws SQLException {
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("first@test.com");
        utente.setPassword("Password123");
        utente.setManager(false);
        manager.createUtente(utente);

        Utente duplicate = new Utente();
        duplicate.setNickname(TEST_NICKNAME);
        duplicate.setEmail("second@test.com");
        duplicate.setPassword("Password456");
        duplicate.setManager(false);

        assertThrows(SQLException.class, () -> manager.createUtente(duplicate));
    }

    //Testing counting works
    @Test
    void countIncreasesAfterCreate() throws SQLException {
        int before = manager.countUtenti();
        Utente utente = new Utente();
        utente.setNickname(TEST_NICKNAME);
        utente.setEmail("count@test.com");
        utente.setPassword("Password123");
        utente.setManager(false);
        manager.createUtente(utente);

        int after = manager.countUtenti();
        assertEquals(before + 1, after);
    }
}
