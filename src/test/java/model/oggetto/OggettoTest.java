package model.oggetto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OggettoTest {

    //Testing setting course price
    @Test
    void setPrezzoAcceptsValidPrice() {
        Oggetto o = new Oggetto();
        o.setPrezzo(29.99);
        assertEquals(29.99, o.getPrezzo());
    }

    //Testing free course edge-case
    @Test
    void setPrezzoAcceptsZero() {
        Oggetto o = new Oggetto();
        o.setPrezzo(0);
        assertEquals(0, o.getPrezzo());
    }
}