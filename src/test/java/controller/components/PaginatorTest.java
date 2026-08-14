package controller.components;

import controller.Paginator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaginatorTest {

    //Testing starting offset
    @Test
    void offsetIsZeroOnFirstPage() {
        Paginator p = new Paginator(1, 10);
        assertEquals(0, p.getOffset());
    }

    //Testing offset calculation
    @Test
    void offsetCalculatedCorrectlyForLaterPage() {
        Paginator p = new Paginator(3, 10);
        assertEquals(20, p.getOffset());
    }

    //Testing limit is equal to itemsperpage
    @Test
    void limitMatchesItemsPerPage() {
        Paginator p = new Paginator(2, 15);
        assertEquals(15, p.getLimit());
    }

    //Testing number of pages (remainder)
    @Test
    void getPagesRoundsUpForRemainder() {
        Paginator p = new Paginator(1, 10);
        assertEquals(3, p.getPages(25));
    }

    //Testing number of pages (no remainder)
    @Test
    void getPagesExactDivisionNoRemainder() {
        Paginator p = new Paginator(1, 10);
        assertEquals(2, p.getPages(20));
    }

    //Testing no items means no pages
    @Test
    void getPagesZeroSizeReturnsZeroPages() {
        Paginator p = new Paginator(1, 10);
        assertEquals(0, p.getPages(0));
    }
}
