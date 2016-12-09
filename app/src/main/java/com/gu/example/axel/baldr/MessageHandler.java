package com.gu.example.axel.baldr;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Axel on 01-Dec-16.
 */

public class MessageHandler {

    MqttConnection connection;
    JSONObject json;

    public MessageHandler(){

    }

    public JSONObject changeState(LightObject light){
        String state = light.getState();

        String color = light.getColor();
        if(state.equals("on"))
            state = "off";
        else
            state = "on";

        JSONObject jsonOuter = new JSONObject();
        json = new JSONObject();

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



    public JSONObject changeColor(LightObject light){
         String color = light.getColor();


        //if



        JSONObject jsonOuter = new JSONObject();
        json = new JSONObject();

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

}
