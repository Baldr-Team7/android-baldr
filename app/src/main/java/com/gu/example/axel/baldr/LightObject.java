package com.gu.example.axel.baldr;

/**
 * Created by Axel on 03-Oct-16.
 */

public class LightObject {

    private String name;
    private int state;
    private int id;

    public LightObject(String n, int s, int i){
        name = n;
        state = s;
        id = i;
    }

    public String getName(){
        return name;
    }

    public String setName(String n){
        name = n;
        return name;
    }

    public int getState(){
        return state;
    }

    public int setState(int s){
        state = s;
        return s;
    }

    public int getId(){
        return id;
    }

    public int setId(int i){
        id = i;
        return id;
    }

}
