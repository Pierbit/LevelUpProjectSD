package controller;

public class Paginator {
    //@ invariant limit > 0;
    //@ invariant offset >= 0;

    private /*@ spec_public @*/ final int limit;
    private /*@ spec_public @*/ final int offset;

    //@ requires page >= 1 && page <= 1000000;
    //@ requires itemsPerPage > 0 && itemsPerPage <= 1000;
    //@ ensures limit == itemsPerPage;
    //@ ensures offset == (page - 1) * itemsPerPage;
    public Paginator(int page, int itemsPerPage) {
        this.limit = itemsPerPage;
        this.offset = (page == 1) ? 0 : ((page - 1) * itemsPerPage);
    }

    //@ ensures \result == limit;
    public int getLimit() {
        return limit;
    }

    //@ ensures \result == offset;
    public int getOffset() {
        return offset;
    }

    //Per sapere quante pagine creare (quanti record presenti)
    //Se c'è resto serve una pagina aggiuntiva
    //@ requires size >= 0;
    //@ ensures \result == (size / limit) + (size % limit == 0 ? 0 : 1);
    public int getPages(int size){
        int additionalPage = (size % limit==0) ? 0 : 1;
        return (size/limit) + additionalPage;
    }
}
