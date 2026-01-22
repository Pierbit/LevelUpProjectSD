package model.categoria;

import model.storage.TableQuery;

public class CategoriaQuery extends TableQuery {


    public CategoriaQuery(String table) {
        super(table);
    }

    public String selectCategorie() {
        return String.format("SELECT * FROM %s LIMIT ?, ?;", this.table);
    }

    public String selectCategoria(){
        return String.format("SELECT * FROM %s WHERE nome=?;", this.table);
    }

    public String insertCategoria(){
        return String.format("INSERT INTO %s (nome) VALUES (?);", this.table);
    }

    public String updateCategoria(){
        return String.format("UPDATE %s SET nome=? WHERE nome=?;", this.table);
    }

    public String deleteCategoria(){
        return String.format("DELETE FROM %s WHERE nome=?;", this.table);
    }

    public String countCategorie() {
        return String.format("SELECT COUNT(*) FROM %s;", this.table);
    }

    public String deleteCorsoCategoria() {
        return String.format("DELETE FROM corsoCategoria WHERE nomeCategoria=?;");
    }

    public String getCorsiAssociati(){
        return String.format("SELECT corso.* FROM corsoCategoria, corso WHERE nomeCategoria=? AND corsoCategoria.idCorso=corso.id;");
    }
}
