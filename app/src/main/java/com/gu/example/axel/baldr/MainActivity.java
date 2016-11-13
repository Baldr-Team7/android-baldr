package com.gu.example.axel.baldr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


public class MainActivity extends AppCompatActivity {

    private int fabState = 1;
    BottomBar bottomBar;
    private Toolbar toolbar;
    FloatingActionButton fab;

    MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String clientId = MqttClient.generateClientId();
         client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://tann.si:8883",
                        clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        //options.setUserName("USERNAME");
       // options.setPassword("PASSWORD".toCharArray());



        try {
            IMqttToken token = client.connect();
            //IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(MainActivity.this, "Connectd", Toast.LENGTH_LONG).show();
                    System.out.println("Connected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this, "Failed to Connect", Toast.LENGTH_LONG).show();

                    System.out.print("Failed to connect");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.bringToFront();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabState == 1) {
                    Intent intent = new Intent(MainActivity.this, AddLightActivity.class);
                    startActivity(intent);













                }
                else if (fabState == 2){
                    //Add room
                    //publish();
                    Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
                    startActivity(intent);
                    //publish();
                }
                else if (fabState == 3){
                    //Add mood
                    Intent intent = new Intent(MainActivity.this, AddMoodActivity.class);
                    startActivity(intent);
                }
            }
        });

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.lightTab){

                    LightFragment f = new LightFragment();
                    fabState = 1;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();

                    setTitle("Lights");

                }
                else if (tabId == R.id.roomTab){
                   // publish();
                    RoomFragment f = new RoomFragment();
                    fabState = 2;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                    setTitle("Rooms");
                }
                else if (tabId == R.id.moodTab){
                    MoodFragment f = new MoodFragment();
                    fabState = 3;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                    setTitle("Moods");
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void publish(View v){
        try {
            client.publish("test", "Hello".getBytes(), 0, false);
        }
        catch(MqttException e){
            e.printStackTrace();
        }


    }
}
