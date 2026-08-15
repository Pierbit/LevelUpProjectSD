package model.categoria;

import model.storage.ConPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaManagerTest {

    private final CategoriaManager manager = new CategoriaManager(ConPool.getDataSource());
    private static final String TEST_NOME = "test_categoria_junit";

    @AfterEach
    void cleanup() throws SQLException {
        manager.deleteCategoria(TEST_NOME);
    }

    //Testing a category create and fetch
    @Test
    void createAndFetchCategoria() throws SQLException {
        boolean created = manager.createCategoria(TEST_NOME);
        assertTrue(created);

        Categoria fetched = manager.fetchCategoria(TEST_NOME);
        assertNotNull(fetched);
        assertEquals(TEST_NOME, fetched.getNome());
    }

    //Testing fetching a non-existent category returns null
    @Test
    void fetchNonExistentCategoriaReturnsNull() throws SQLException {
        Categoria fetched = manager.fetchCategoria("does_not_exist_junit");
        assertNull(fetched);
    }

    //Testing category delete
    @Test
    void deleteCategoriaRemovesIt() throws SQLException {
        manager.createCategoria(TEST_NOME);
        boolean deleted = manager.deleteCategoria(TEST_NOME);
        assertTrue(deleted);

        Categoria fetched = manager.fetchCategoria(TEST_NOME);
        assertNull(fetched);
    }

    //Testing count works
    @Test
    void countIncreasesAfterCreate() throws SQLException {
        int before = manager.countCategorie();
        manager.createCategoria(TEST_NOME);
        int after = manager.countCategorie();
        assertEquals(before + 1, after);
    }

    //Testing fetchCategorie returns non-null non-throwing list within a range
    @Test
    void fetchCategorieReturnsListWithinRange() throws SQLException {
        manager.createCategoria(TEST_NOME);

        List<Categoria> categorie = manager.fetchCategorie(0, 100);
        assertNotNull(categorie);
        assertTrue(categorie.stream().anyMatch(categoria -> categoria.getNome().equals(TEST_NOME)));
    }

}
