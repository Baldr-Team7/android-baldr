package com.gu.example.axel.baldr;


import android.content.Intent;

import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.View;


//import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

<<<<<<< HEAD
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.List;

=======
>>>>>>> origin/master

public class MainActivity extends AppCompatActivity {

    private int fabState = 1;
    BottomBar bottomBar;
    private Toolbar toolbar;
    FloatingActionButton fab;
    MqttConnection connection;
<<<<<<< HEAD
<<<<<<< HEAD
    CustomListener cl;
=======
    LightFragment lightF;
>>>>>>> origin/master

=======
>>>>>>> parent of 079ef65... HomeID & LightDd deleted



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        System.out.println("heeey " + getApplicationContext());

<<<<<<< HEAD
<<<<<<< HEAD
=======
        /*connection = new MqttConnection(getApplicationContext(), this);
        connection.connect();*/
>>>>>>> origin/master
=======
        /*connection = new MqttConnection();
        connection.connect();*/
>>>>>>> parent of 079ef65... HomeID & LightDd deleted

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.bringToFront();





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabState == 1) {
                    //Add light
                    Intent intent = new Intent(MainActivity.this, AddLightActivity.class);
                    startActivity(intent);
                }
                else if (fabState == 2){
                    //Add room
                    Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
                    startActivity(intent);
                }
                else if (fabState == 3){
                    //Add mood
                    Intent intent = new Intent(MainActivity.this, AddMoodActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Setting listners for the bottombar, switching fragments and altering the FAB.
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.lightTab){
                    lightF = new LightFragment();
                    fabState = 1;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,lightF)
                            .addToBackStack(null)
                            .commit();
                    fab.show();
                    setTitle("Lights");

                }
                else if (tabId == R.id.roomTab){
                    RoomFragment f = new RoomFragment();
                    fabState = 2;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
                            .addToBackStack(null)
                            .commit();
                    fab.show();
                    setTitle("Rooms");
                }
                else if (tabId == R.id.moodTab){
                    MoodFragment f = new MoodFragment();
                    fabState = 3;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
                            .addToBackStack(null)
                            .commit();
                    fab.show();
                    setTitle("Moods");
                }
            }

        });
    }

    public void editLight(LightObject data){
            LightObject light = data;
            EditLight f = new EditLight();
            Bundle bundle = new Bundle();
            bundle.putString("color", light.getColor());
            bundle.putString("name", light.getState()+light.getId());
            f.setArguments(bundle);
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, f)
                    .addToBackStack(null)
                    .commit();
            fab.hide();
            setTitle("Edit " + light.getId());
    }

    public void editRoom(LightObject data){
        LightObject light = data;
        EditLight f = new EditLight();
        Bundle bundle = new Bundle();
        bundle.putString("color", light.getColor());
        bundle.putString("name", light.getRoom());
        f.setArguments(bundle);
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, f)
                .addToBackStack(null)
                .commit();
        fab.hide();
        setTitle("Edit " + light.getRoom());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

<<<<<<< HEAD
<<<<<<< HEAD



=======
    /*@Override
    public void callback(String result){
        LightObject[] lArray = new LightObject[connection.getLightArray().length];
        lArray = connection.getLightArray();
        for (int i = 0; i < lArray.length; i++) {
            System.out.println("in lightfragment callback : LightList["+ i + "] = " + lArray[i].getId() + " " + lArray[i].getState() + " "
                    + lArray[i].getColor() + " " + lArray[i].getRoom());
        }

        lightF.setLights(lArray);


    }*/
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }
    }
>>>>>>> origin/master
=======
>>>>>>> parent of 079ef65... HomeID & LightDd deleted
}
