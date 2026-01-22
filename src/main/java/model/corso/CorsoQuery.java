package model.corso;

import controller.search.Condition;
import controller.search.Operator;
import model.storage.TableQuery;

import java.util.List;
import java.util.StringJoiner;

public class CorsoQuery extends TableQuery {
    public CorsoQuery(String table) {
        super(table);
    }

    public String selectCorsi() {
        return String.format("SELECT * FROM %s LIMIT ?, ?;", this.table);
    }

    public String selectCorso() {
        return String.format("SELECT * FROM %s WHERE id=?;", this.table);
    }

    public String insertCorso() {
        return String.format("INSERT INTO %s (nome, prezzoBase, testo, copertina) VALUES (N?,?,N?,?);", this.table);
    }

    public String insertCreatore() {
        return String.format("INSERT INTO utenteCreaCorso (nicknameUtente, idCorso) VALUES (?,?);");
    }

    public String insertPartecipante(){
        return String.format("INSERT INTO utentePartecipaCorso (nicknameUtente,idCorso) VALUES(?,?);");
    }

    public String insertCorsoCategoria() {
        return String.format("INSERT INTO corsoCategoria (idCorso,nomeCategoria) VALUES (?,?);");
    }

    public String filterCorsi() {
        return String.format("SELECT corso.* FROM corso, corsoCategoria WHERE corsoCategoria.nomeCategoria=? AND corsoCategoria.idCorso=corso.id");
    }

    public String search(List<Condition> conditions){

        String query = "SELECT DISTINCT corso.* FROM corso, utenteCreaCorso, corsoTag, corsoCategoria";
        if(!conditions.isEmpty()){
            query = query + " WHERE ";
            StringJoiner searchJoiner = new StringJoiner(" AND ");
            StringJoiner orjoiner = new StringJoiner(" OR ");
            for(Condition c: conditions){
                if(c.getOperator() == Operator.MATCH){
                    String temp = c.getTable()+"."+c.toString()+ " " + "'%"+c.getValue()+"%'";
                    searchJoiner.add(temp);
                } else if(c.getOperator() == Operator.EQ){
                    String temp = c.getTable()+"."+c.toString()+ " " + "'"+c.getValue()+"'";
                    orjoiner.add(temp);
                    //String temp = c.getTable()+"."+c.toString()+ " " + "'"+c.getValue()+"'";
                    //searchJoiner.add(temp);
                } else {
                    String temp = c.getTable()+"."+c.toString() + " " + c.getValue();
                    searchJoiner.add(temp);
                }
            }

            if(!(orjoiner.toString().isBlank())){
                String tmp = "("+orjoiner.toString()+")";
                searchJoiner.add(tmp);
            }
            String tmp = " corso.id=utenteCreaCorso.idCorso AND corso.id=corsoTag.idCorso AND corso.id=corsoCategoria.idCorso;";
            searchJoiner.add(tmp);
            query = query + searchJoiner.toString();
            return query;
        }
        return query;
    }

    public String insertCorsoTag() {
        return String.format("INSERT INTO corsoTag (idCorso,nomeTag) VALUES (?,?);");
    }

    public String updateCorso() {
        return String.format("UPDATE %s SET nome=?, prezzoBase=?, testo=?, copertina=? WHERE id=?;", this.table);
    }

    public String deleteCorso() {
        return String.format("DELETE FROM %s WHERE id=?;", this.table);
    }

    public String countCorsi() {
        return String.format("SELECT COUNT(*) FROM %s;", this.table);
    }

    public String getUtentiPartecipanti(){
        return String.format("SELECT utente.* FROM utentePartecipaCorso, utente WHERE idCorso=? AND utentePartecipaCorso.nicknameUtente=utente.nickname;");
    }

    public String getCorsoId(){
        return String.format("SELECT corso.id FROM corso WHERE corso.nome=?");
    }

    public String dropUtentiPartecipanti() {
        return "DELETE FROM utentePartecipaCorso WHERE idCorso=?";
    }

    public String dropUtentePartecipante() { return String.format("DELETE FROM utentePartecipaCorso WHERE idCorso=? AND nicknameUtente=?;");}
    public String getUtenteCreatore(){
        return String.format("SELECT utente.* FROM utenteCreaCorso, utente WHERE idCorso=? AND utenteCreacorso.nicknameUtente=utente.nickname;");
    }

    public String dropUtenteCreatore() {
        return "DELETE FROM utenteCreaCorso WHERE idCorso=?";
    }

    public String getTagAssociati(){
        return String.format("SELECT tag.* FROM corsoTag, tag WHERE idCorso=? AND corsoTag.nomeTag=tag.nome;");
    }

    public String dropTagAssociati() {
        return "DELETE FROM corsoTag WHERE idCorso=?";
    }

    public String getCategoria(){
        return String.format("SELECT categoria.* FROM corsoCategoria, categoria WHERE corsoCategoria.idCorso=? AND corsoCategoria.nomeCategoria=categoria.nome;");
    }

    public String dropCategoria() {
        return "DELETE FROM corsoCategoria WHERE idCorso=?";
    }

    public String dropOggetto() {
        return "DELETE FROM corsoOggetto WHERE idCorso=?";
    }
}
