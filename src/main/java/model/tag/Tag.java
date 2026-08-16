package model.tag;

import java.io.Serial;

public class Tag implements java.io.Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    //Attributi
    //@ nullable
    public String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
