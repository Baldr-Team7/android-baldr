package com.gu.example.axel.baldr;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Axel on 04-Nov-16.
 */

public class MqttConnection implements MqttCallback {

    MqttAndroidClient client;
    Context c;
    JSONObject json;
    private int counter = 0;
    CustomListener cl;

    int homeID, lightID;

    private LightObject[] lightList = new LightObject[0];


    public MqttConnection(Context context, CustomListener cl) {
        c = context;
        this.cl = cl;
    }

    //  MqttConnectOptions options = new MqttConnectOptions();
    //options.setUserName("USERNAME");
    // options.setPassword("PASSWORD".toCharArray());

    public void initiateConnection() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(c, "tcp://tann.si:8883", clientId);
    }

    public void connect() {
        initiateConnection();
        try {
            IMqttToken token = client.connect();
            //IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    System.out.println("Connected");
                    subscribe(homeID, lightID);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    System.out.print("Failed to connect");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish() {
        try {
            client.publish("test", "Hello".getBytes(), 0, false);
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }

    // lightcontrol/home/{homeID}/light/{lightUUID}/commands
    public void subscribe(int homeID, int lightID) {
        try {
            client.setCallback(this);
            client.subscribe("lightcontrol/home/asdf/light/+/info", 0);
            System.out.println("Subscribed");
        } catch (MqttException e) {
            e.printStackTrace();

        }

    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(message);
        json = new JSONObject(message.toString()).getJSONObject("lightInfo");

        LightObject light = new LightObject(json.getString("id"), json.getString("state")
                , json.getString("color"), json.getString("room"));
        counter++;
        setLightArray(light);
        pingFragment();

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }
    // {"protocolName":"baldr","version":1,"lightInfo":{"state":"off","color":"#FFFFFF","room":"undefined"}}

    public void setLightArray(LightObject light) {
        LightObject[] temp;
        boolean check = false;
        int pos = 0;

        if (lightList.length == 0) {
            lightList = new LightObject[counter];
            lightList[counter-1] = light;
        } else {
            for (int i = 0; i < lightList.length; i++) {
                if (lightList[i].getId() == light.getId()) {
                    pos = i;
                    check = true;
                    break;
                }
            }
        }

        if (check) {
            lightList[pos] = light;
        } else {
            temp = lightList;
            lightList = new LightObject[counter];
            for (int i = 0; i < temp.length; i++) {
                lightList[i] = temp[i];
            }
            lightList[counter-1] = light;
        }


        for (int i = 0; i < lightList.length; i++) {
            System.out.println("LightList["+ i + "] = " + lightList[i].getId() + " " + lightList[i].getState() + " "
                    + lightList[i].getColor() + " " + lightList[i].getRoom());
        }

    }

    public LightObject[] getLightArray() {
        return lightList;
    }

    public void pingFragment(){
        cl.callback("success");
    }
}