package controller.search;

public class Condition {

    private final String name;
    private final Operator operator;
    private final Object value;
    private final String table;

    public Condition(String name, Operator operator, Object value, String table){
        this.name = name;
        this.operator = operator;
        this.value = value;
        this.table = table;
    }

    public String toString(){
        return name + " " + operator;
    }

    public String getName(){
        return name;
    }

    public Operator getOperator(){
        return operator;
    }

    public Object getValue(){
        return value;
    }

    public String getTable(){
        return table;
    }
}
