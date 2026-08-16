package model.categoria;

import controller.JsonSerializable;
import org.json.JSONObject;

import java.io.Serial;

public class Categoria implements JsonSerializable, java.io.Serializable {
    //Attributi
    //@ nullable
    private String nome;

    @Serial
    private static final long serialVersionUID = 1L;

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
