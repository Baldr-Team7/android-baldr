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
import java.util.Arrays;
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

    String homeID;

    private LightObject[] lightArray = new LightObject[0];
    private LightObject[] roomArray = new LightObject[0];


    public MqttConnection(Context context, CustomListener cl) {
        c = context;
        this.cl = cl;
        MainActivity ma = (MainActivity) context;
        homeID = ma.homeID;
    }

    //  MqttConnectOptions options = new MqttConnectOptions();
    //options.setUserName("USERNAME");
    // options.setPassword("PASSWORD".toCharArray());

    private void initiateConnection() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(c, "tcp://tann.si:8883", clientId);
    }

    public void connect() {
        initiateConnection();
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    System.out.println("Connected");

                    subscribe();
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


    //Subcribe to broker
    public void subscribe() {

        try {
            client.setCallback(this);
            client.subscribe("lightcontrol/home/"+ homeID +"/light/+/info", 0);
            System.out.println("Subscribed");
        } catch (MqttException e) {
            e.printStackTrace();

        }

    }
    public void unsubscribe(){

        try{
            client.unsubscribe("lightcontrol/home/"+ homeID +"/light/+/info");
        } catch (MqttException e){
            e.printStackTrace();
        }
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(message);
        json = new JSONObject(message.toString()).getJSONObject("lightInfo");

        LightObject light = new LightObject(json.getString("id"), json.getString("state")
                , json.getString("color"), json.getString("room"), json.getString("name"));
        LightObject roomLight = new LightObject(json.getString("id"), json.getString("state")
                , json.getString("color"), json.getString("room"), "undefined");

        System.out.println("Got message arrived");
        setLightArray(light);
        setRoomArray(roomLight);
        pingFragment();
        System.out.println("Pinged fragments");



    }

    // Message to change state of a light
    public void publish(LightObject light) {
        MessageHandler messageHandler = new MessageHandler();
        try {
            String message = messageHandler.changeState(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/"+light.getId()+"/commands"
                    , message.getBytes(), 0, false);

            System.out.println("1Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/"+
                    "light/"+light.getId()+"/commands");
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }



    //Message to change Light Color
    public void publishColor(LightObject light) {
        MessageHandler messageHandler = new MessageHandler();


        try {
            String message = messageHandler.changeColor(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/" + light.getId() + "/commands"
                    , message.getBytes(), 0, false);

            System.out.println("2Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/light/" + light.getId() + "/commands");
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }



    //Message to Change state of a Room
    public void publishRoom(LightObject room) {
        MessageHandler messageHandler = new MessageHandler();

        try {
            String message = messageHandler.changeState(room).toString();
            client.publish("lightcontrol/home/"+ homeID +"/room/" + room.getRoom() + "/commands"
                    , message.getBytes(), 0, false);

            System.out.println("3Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/room/" + room.getRoom());
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }

    //Message to change Room Color
    public void publishColorRoom(LightObject light) {
        MessageHandler messageHandler = new MessageHandler();

        try {
            String message = messageHandler.changeColor(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/room/" + light.getRoom() + "/commands"
                    , message.getBytes(), 0, false);

            System.out.println("4Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/room/" + light.getRoom() + "/commands");
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }

    //Message to change a lights name
    public void publishNameChange(LightObject light){
        MessageHandler messageHandler = new MessageHandler();

        try{
            String message = messageHandler.changeName(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/" + light.getId() + "/commands"
                    , message.getBytes(), 0, false);
            System.out.println("5Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/"+
                    "light/"+light.getId()+"/commands");
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    //Message to change a rooms name
    /*public void publishNameChangeRoom(LightObject light){
        MessageHandler messageHandler = new MessageHandler();

        try{
            String message = messageHandler.changeName(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/" + light.getId() + "/commands"
                    , message.getBytes(), 0, false);
            System.out.println("Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/"+
                    "room/"+light.getRoom()+"/commands");
        } catch (MqttException e){
            e.printStackTrace();
        }
    }*/

    public void publishLightChangeRoom(LightObject light){
        MessageHandler messageHandler = new MessageHandler();

        try{
            String message = messageHandler.changeRoom(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/" + light.getId() + "/commands"
                    , message.getBytes(), 0, false);
            System.out.println("6Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/"+
                    "light/"+light.getId()+"/commands");
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void publishRoomChangeRoom(LightObject light, String oldRoom){
        MessageHandler messageHandler = new MessageHandler();

        try{
            String message = messageHandler.changeRoom(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/room/" + oldRoom + "/commands"
                    , message.getBytes(), 0, false);
            System.out.println("this is bill Sending: " + message + "to topic: lightcontrol/home/"+ homeID +"/"+
                    "room/"+oldRoom+"/commands");
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    public void publishJSON (String topic, JSONObject json){

        System.out.println("in publishJSON got topic: " + topic + " and object: " + json);
        try{
            String message = json.toString();
            client.publish(topic,message.getBytes(),0,false);
            System.out.println("Hellooooooo");
            // client.publish("lightcontrol/home/"+ homeID +"/room/" + oldRoom + "/commands", message.getBytes(), 0, false);
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    static int rCounter = 0;

    // Array of Rooms
    private void setRoomArray(LightObject light) throws IOException {


        LightObject[] temp;
        boolean check = true;

        if (roomArray.length == 0) {
            roomArray = new LightObject[1];
            roomArray[0] = light;

        }
        else{
            for(int i = 0; i < roomArray.length; i++){
                if(roomArray[i].getRoom().equals(light.getRoom())){
                    if(light.getState().equals("off")){
                        roomArray[i].setState("off");
                    }else{
                        roomArray[i].setState("on");
                    }
                    check = false;
                }
            }
            if(check){
                temp = roomArray;
                roomArray = new LightObject[roomArray.length + 1];
                for (int i = 0; i < temp.length; i++) {
                    roomArray[i] = temp[i];
                }
                roomArray[roomArray.length-1] = light;


            }
        }


        for (int i = 0; i < roomArray.length; i++) {
            System.out.println("Rooms in MQTT class ["+ i + "] = " + roomArray[i].getRoom());
        }


    }

    // Set Array of lights
    private void setLightArray(LightObject light) {

        LightObject[] temp;
        boolean check = false;

        int pos = 0;

        if (lightArray.length == 0) {
            lightArray = new LightObject[1];
            lightArray[0] = light;
        } else {
            for (int i = 0; i < lightArray.length; i++) {
                if (lightArray[i].getId().equals(light.getId())) {
                    pos = i;
                    check = true;
                    break;
                }
            }
            if (check) {
                lightArray[pos] = light;
            } else {
                temp = lightArray;
                lightArray = new LightObject[lightArray.length + 1];
                for (int i = 0; i < temp.length; i++) {
                    lightArray[i] = temp[i];
                }
                lightArray[lightArray.length-1] = light;
            }
        }




        for (int i = 0; i < lightArray.length; i++) {
            System.out.println("lightArray["+ i + "] = " + lightArray[i].getId() + " " + lightArray[i].getState() + " "
                    + lightArray[i].getColor() + " " + lightArray[i].getRoom());
        }

    }



    // Get array of Rooms
    public LightObject[] getRoomArray() {

        for (int i = 0; i < roomArray.length; i++) {
            System.out.println("In mqttconnection roomArray : RoomList["+ i + "] = id = " + roomArray[i].getId() + " " + roomArray[i].getRoom() + " " + roomArray[i].getState());
        }

        return roomArray;
    }


    // Get array of Lights
    public LightObject[] getLightArray() {
        for (int i = 0; i < lightArray.length; i++) {
            System.out.println("In mqttconnection getLightArray : lightArray["+ i + "] = " + lightArray[i].getId() + " " + lightArray[i].getState() + " "
                    + lightArray[i].getColor() + " " + lightArray[i].getRoom());
        }
        return lightArray;
    }

    public void resetData(){
        lightArray = new LightObject[0];
        roomArray = new LightObject[0];
    }



    private void pingFragment(){
        cl.callback("success");
    }
}