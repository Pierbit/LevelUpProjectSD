package model.utente;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    //Testing that setPasswordHashed actually stores a bcrypt hash, not plaintext
    @Test
    void setPasswordHashedStoresHashNotPlaintext() {
        Utente utente = new Utente();
        utente.setPasswordHashed("MyPassword123");

        assertNotEquals("MyPassword123", utente.getPassword());
        assertTrue(utente.getPassword().startsWith("$2a$") || utente.getPassword().startsWith("$2b$"));
    }

    //Testing that the correct password verifies successfully against the stored hash
    @Test
    void correctPasswordVerifiesAgainstHash() {
        Utente utente = new Utente();
        utente.setPasswordHashed("MyPassword123");

        assertTrue(BCrypt.checkpw("MyPassword123", utente.getPassword()));
    }

    //Testing that an incorrect password fails verification against the stored hash
    @Test
    void wrongPasswordFailsVerification() {
        Utente utente = new Utente();
        utente.setPasswordHashed("MyPassword123");

        assertFalse(BCrypt.checkpw("WrongPassword", utente.getPassword()));
    }

    //Testing that hashing the same password twice produces two different hashes,
    @Test
    void sameHashedTwiceProducesDifferentHashes() {
        Utente utente1 = new Utente();
        utente1.setPasswordHashed("MyPassword123");

        Utente utente2 = new Utente();
        utente2.setPasswordHashed("MyPassword123");

        assertNotEquals(utente1.getPassword(), utente2.getPassword());
        assertTrue(BCrypt.checkpw("MyPassword123", utente1.getPassword()));
        assertTrue(BCrypt.checkpw("MyPassword123", utente2.getPassword()));
    }
}
