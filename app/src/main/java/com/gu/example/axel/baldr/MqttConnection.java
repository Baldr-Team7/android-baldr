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

import java.io.IOException;
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
    private LightObject[] roomArray = new LightObject[0];


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
    //lightcontrol/home/asdf/light/LIGHTID/commands <- publish to that topic.
    public void publish(LightObject light) {
        MessageHandler messageHandler = new MessageHandler();
        try {
            String message = messageHandler.changeState(light).toString();
            client.publish("lightcontrol/home/asdf/light/"+light.getId()+"/commands"
                    , message.getBytes(), 0, false);

            System.out.println("Sending: " + message + "to topic: lightcontrol/home/asdf/" +
                    "light/"+light.getId()+"/commands");
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }


    public void publishRoom(LightObject light) {
        MessageHandler messageHandler = new MessageHandler();


        try {
            String message = messageHandler.changeState(light).toString();
            client.publish("lightcontrol/home/asdf/light/"+light.getId()+"/commands"
                    , message.getBytes(), 0, false);

            System.out.println("Sending: " + message + "to topic: lightcontrol/home/asdf/" +
                    "light/"+light.getId()+"/commands");
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
        setRoomArray(light);
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


    public void setRoomArray(LightObject light) throws IOException {
        LightObject[] lights;

        if (lightList.length == 0) {
            System.out.println("no rooms");
            lightList = new LightObject[counter];
            lightList[counter-1] = light;
        } else {
            for (int i = 0; i < lightList.length; i++) {
                // for (int k = i+1; k < lightList.length; k++){
                if (lightList[i].getRoom().equals(light.getRoom())) {

                    System.out.println("ROOMS IN MQTT " + light.getRoom());

                } else {
                    System.out.println("DIFF ROOMS IN MQTT " + light.getRoom());
                }
            }
        }
        /*for (int i = 0; i < roomArray.length; i++) {
            System.out.println("in roomfragment callback : RoomList["+ i + "] = " + roomArray[i].getRoom());
        }*/

    }

    public void setLightArray(LightObject light) {
        LightObject[] temp;
        boolean check = false;
        int pos = 0;

        if (lightList.length == 0) {
            lightList = new LightObject[counter];
            lightList[counter-1] = light;
        } else {
            for (int i = 0; i < lightList.length; i++) {
                if (lightList[i].getId().equals(light.getId())) {
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


    public LightObject[] getRoomArray() {
        for (int i = 0; i < lightList.length; i++) {
            System.out.println("In mqttconnection roomArray : RoomList["+ i + "] = " + lightList[i].getRoom());
        }
        return lightList;
    }


    public LightObject[] getLightArray() {
        for (int i = 0; i < lightList.length; i++) {
            System.out.println("In mqttconnection getLightArray : LightList["+ i + "] = " + lightList[i].getId() + " " + lightList[i].getState() + " "
                    + lightList[i].getColor() + " " + lightList[i].getRoom());
        }
        return lightList;
    }

    public void pingFragment(){
        cl.callback("success");
    }
}