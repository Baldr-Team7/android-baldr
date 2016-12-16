package com.gu.example.axel.baldr;

import android.content.Context;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


/**
 * Created by Axel on 04-Nov-16.
 * Made by Aras and Axel
 */

public class MqttConnection implements MqttCallback {

    MqttAndroidClient client;
    Context c;
    JSONObject json;
    private int counter = 0;
    CustomListener cl;

    String clientId;
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
        clientId = MqttClient.generateClientId();
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
                    long unixTimestamp = (System.currentTimeMillis() / 1000L);
                    JSONObject jsonPresence = new JSONObject();
                    try {
                        jsonPresence.put("version", 1);
                        jsonPresence.put("groupName", "Baldr");
                        jsonPresence.put("groupNumber", "7");
                        jsonPresence.put("connectedAt", unixTimestamp);
                        jsonPresence.put("rfcs", "[RFC 1, RFC 17, RFC 18]");
                        jsonPresence.put("clientVersion", "1.0");
                        jsonPresence.put("clientSoftware", "Baldr Android Client");
                        publishJSON("presence/" + clientId, jsonPresence);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
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

        setLightArray(light);
        setRoomArray(roomLight);
        pingFragment();
    }

    // Message to change state of a light
    public void publish(LightObject light) {
        MessageHandler messageHandler = new MessageHandler();
        try {
            String message = messageHandler.changeState(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/"+light.getId()+"/commands"
                    , message.getBytes(), 1, false);

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
                    , message.getBytes(), 1, false);

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
                    , message.getBytes(), 1, false);

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
                    , message.getBytes(), 1, false);

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
                    , message.getBytes(), 1, false);
            } catch (MqttException e){
            e.printStackTrace();
        }
    }


    //Move light to another room
    public void publishLightChangeRoom(LightObject light){
        MessageHandler messageHandler = new MessageHandler();

        try{
            String message = messageHandler.changeRoom(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/light/" + light.getId() + "/commands"
                    , message.getBytes(), 1, false);
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    //Change name of a room
    public void publishRoomChangeRoom(LightObject light, String oldRoom){
        MessageHandler messageHandler = new MessageHandler();

        try{
            String message = messageHandler.changeRoom(light).toString();
            client.publish("lightcontrol/home/"+ homeID +"/room/" + oldRoom + "/commands"
                    , message.getBytes(), 1, false);
        } catch (MqttException e){
            e.printStackTrace();
        }
    }

    //Publish JSON that's defined in other classes. Used for moods.
    public void publishJSON (String topic, JSONObject json){

        try{
            String message = json.toString();
            client.publish(topic,message.getBytes(),1,false);
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





    }



    // Get array of Rooms
    public LightObject[] getRoomArray() {

        return roomArray;
    }


    // Get array of Lights
    public LightObject[] getLightArray() {

        return lightArray;
    }

    //When homeId is changed, we empty room and light arrays to make space for the ones in the new home.
    public void resetData(){
        lightArray = new LightObject[0];
        roomArray = new LightObject[0];
    }


    //Uses the callback function from interface to inform MainActivity that new data is ready to use.
    private void pingFragment(){
        cl.callback("success");
    }
}