package model.categoria;

import controller.JsonSerializable;
import org.json.JSONObject;

public class Categoria implements JsonSerializable {
    //Attributi
    //@ nullable
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("nome",nome);
        return object;
    }

}
