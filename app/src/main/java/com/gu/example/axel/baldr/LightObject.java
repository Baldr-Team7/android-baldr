package com.gu.example.axel.baldr;

/**
 * Created by Axel on 03-Oct-16.
 */

public class LightObject {

    private String color;
    private String room;
    private String state;
    private String id;
    private String name;

    public LightObject(String id, String state, String color, String room, String name){

        this.id = id;
        this.color = color;
        this.room = room;
        this.state = state;
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
