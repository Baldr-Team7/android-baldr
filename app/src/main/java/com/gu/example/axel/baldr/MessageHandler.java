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
        if(state == "on") {
            state = "off";
        }else{
            state = "on";
        }

        json = new JSONObject();
        try {
            json.put("state", state);
        }catch (JSONException e){
            System.out.println(e);
        }

        return json;
    }

}
