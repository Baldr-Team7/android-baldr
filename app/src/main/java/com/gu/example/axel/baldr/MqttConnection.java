package com.gu.example.axel.baldr;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttService;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Axel on 04-Nov-16.
 */

public class MqttConnection implements MqttCallback{

    MqttAndroidClient client;
    Context c;


    public MqttConnection(Context context) {
        c = context;
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

        public void publish(){
            try {
                client.publish("test", "Hello".getBytes(), 0, false);
            } catch (MqttException e) {
                e.printStackTrace();

            }


        }
    public void subscribe(){
            try {
                client.setCallback(this);
                client.subscribe("test", 0);
                System.out.println("Subscribed");
            } catch (MqttException e) {
                e.printStackTrace();

            }

        }

    /*@Override
    public void messageArrived(MqttTopic topic, MqttMessage message){
        System.out.println(message);
    }*/

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    @Override
    public void connectionLost(Throwable cause) {

    }
}