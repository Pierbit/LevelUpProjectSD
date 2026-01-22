package model.storage;

public abstract class TableQuery {
    protected final String table;

    public TableQuery(String table) {
        this.table = table;
    }
}
