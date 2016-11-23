package com.gu.example.axel.baldr;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by arasb on 2016-11-20.
 */

public class JSONParser {

    Context c;
int homeID, lightID;
     MqttConnection mqttConnection = new MqttConnection(c);


    public MqttConnection getMessage(MqttConnection message) throws IOException {

        message.subscribe(6, 6);
return null;
    }




    public static String[] getLightInfo() {
        String json = null;
        try {
           // json = mqttConnection.subscribe(4,4);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        Json jsonClass = gson.fromJson(json, Json.class);


        return new String[] {
        "color " + jsonClass.getLightInfo().getColor(),
        "state " + jsonClass.getLightInfo().getState(),
        "room " + jsonClass.getLightInfo().getRoom()
         };

    }



}
