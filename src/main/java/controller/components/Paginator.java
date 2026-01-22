package controller.components;

public class Paginator {

    private final int limit;
    private final int offset;

    public Paginator(int page, int itemsPerPage) {
        this.limit = itemsPerPage;
        this.offset = (page == 1) ? 0 : ((page - 1) * itemsPerPage);
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    //Per sapere quante pagine creare (quanti record presenti)
    //Se c'è resto serve una pagina aggiuntiva
    public int getPages(int size){
        int additionalPage = (size % limit==0) ? 0 : 1;
        return (size/limit) + additionalPage;
    }
}
