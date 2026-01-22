package model.tag;

import model.storage.TableQuery;

public class TagQuery extends TableQuery {
    public TagQuery(String table) {
        super(table);
    }

    public String selectTags() {
        return String.format("SELECT * FROM %s LIMIT ?, ?;", this.table);
    }

    public String selectTag() {
        return String.format("SELECT * FROM %s WHERE nome=?;", this.table);
    }

    public String insertTag() {
        return String.format("INSERT INTO %s (nome) VALUES (?);", this.table);
    }

    public String updateTag() {
        return String.format("UPDATE %s SET nome=? WHERE nome=?;", this.table);
    }

    public String deleteCorsoTag() {
        return String.format("DELETE FROM corsoTag WHERE nomeTag=?;");
    }

    public String deleteTag() {
        return String.format("DELETE FROM %s WHERE nome=?;", this.table);
    }

    public String countTags() {
        return String.format("SELECT COUNT(*) FROM %s;", this.table);
    }

    public String getCorsiAssociati(){ return String.format("SELECT corso.* FROM corsoTag, corso WHERE corsoTag.nomeTag=? AND corsoTag.idCorso=corso.id;");}
}
