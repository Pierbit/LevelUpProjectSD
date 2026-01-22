package controller.components;

import java.util.ArrayList;

public class Alert {

    private final ArrayList<String> messages;
    private final String type;

    public Alert(ArrayList<String> messages,String type){
        this.messages = messages;
        this.type = type;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public String getType(){
        return this.type;
    }
}
