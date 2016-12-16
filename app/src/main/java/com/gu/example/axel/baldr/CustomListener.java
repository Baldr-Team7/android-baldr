package com.gu.example.axel.baldr;


/**
 * Created by Axel on 29-Nov-16.
 */

public interface CustomListener {
    //Function used to send messages from MqttConnection to MainActivity
    void callback(String result);
}
