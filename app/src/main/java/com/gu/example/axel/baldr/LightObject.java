package com.gu.example.axel.baldr;

/**
 * Created by Axel on 03-Oct-16.
 */

public class LightObject {

    private String name;
    private String room;
    private boolean state;
    private int id;

    public LightObject(String name, String room, boolean state, int id){
        this.name = name;
        this.room = room;
        this.state = state;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getStateText(){
        String s = null;
        if (this.state)
            s = "On";
        else
            s="Off";
        return s;
    }
}
