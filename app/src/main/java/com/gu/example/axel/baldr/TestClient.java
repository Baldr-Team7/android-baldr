package com.gu.example.axel.baldr;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by Axel on 04-Nov-16.
 */

public class TestClient {
    private MqttClient client;

    public TestClient(){
        try {
            client = new MqttClient("tcp://tann.si:8883", //URI
                    MqttClient.generateClientId(), //ClientId
                    new MemoryPersistence()); //Persistence
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            client.publish(
                    "/home/FF/Lights/FF", // topic
                    message.getBytes(UTF_8), // payload
                    2, // QoS
                    false); // retained?
        } catch (MqttException e){
            e.printStackTrace();
        }
    }
}
