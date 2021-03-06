package com.gu.example.axel.baldr;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Axel on 01-Dec-16.
 */

public class MessageHandler {

    public MessageHandler(){

    }

    // Json message to change state of Light or Room
    public JSONObject changeState(LightObject light){
        String state = light.getState();

        if(state.equals("on"))
            state = "off";
        else
            state = "on";

        JSONObject jsonOuter = new JSONObject();
        JSONObject json = new JSONObject();

        try {
            jsonOuter.put("version", 1);
            jsonOuter.put("protocolName", "baldr");
            jsonOuter.put("lightCommand", json.put("state", state));

        }catch(JSONException e){
            System.out.println(e);
        }

        light.setState(state);

        return jsonOuter;
    }



    // Json message to change color of Light or Room
    public JSONObject changeColor(LightObject light){
         String color = light.getColor();

        JSONObject jsonOuter = new JSONObject();
        JSONObject json = new JSONObject();

        try {
            jsonOuter.put("version", 1);
            jsonOuter.put("protocolName", "baldr");
            jsonOuter.put("lightCommand", json.put("color", color));
        }catch(JSONException e){
            System.out.println(e);
        }

        light.setColor(color);

        return jsonOuter;
    }

    public JSONObject changeName(LightObject light){
        String name = light.getName();

        JSONObject jsonOuter = new JSONObject();
        JSONObject json = new JSONObject();

        try {
            jsonOuter.put("version", 1);
            jsonOuter.put("protocolName", "baldr");
            jsonOuter.put("lightCommand", json.put("name", name));
        }catch(JSONException e){
            System.out.println(e);
        }

        light.setName(name);

        return jsonOuter;
    }

    public JSONObject changeRoom(LightObject light){
        String room = light.getRoom();

        JSONObject jsonOuter = new JSONObject();
        JSONObject json = new JSONObject();

        try {
            jsonOuter.put("version", 1);
            jsonOuter.put("protocolName", "baldr");
            jsonOuter.put("lightCommand", json.put("room", room));
        }catch(JSONException e){
            System.out.println(e);
        }

        light.setRoom(room);

        return jsonOuter;
    }

}
