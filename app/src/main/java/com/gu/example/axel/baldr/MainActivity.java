package com.gu.example.axel.baldr;


import android.content.Context;
import android.content.Intent;

import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;


//import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


public class MainActivity extends AppCompatActivity implements CustomListener{

    private int fabState = 1;
    BottomBar bottomBar;
    private Toolbar toolbar;
    FloatingActionButton fab;
    SharedPreferences preferences;
    MqttConnection connection;

    public String homeID;

    LightFragment lightFragment;
    RoomFragment roomFragment;
    MoodFragment moodFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightFragment = new LightFragment();
        roomFragment = new RoomFragment();
        moodFragment = new MoodFragment();


        System.out.println("heeey ");
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        System.out.println("heeey " + context);



        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        homeID = preferences.getString("homeID", "");
        if(homeID.equals("")){
            System.out.println("Homeid was empty ERROR");
            homeID = "asdf";
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("homeID", homeID);
            editor.commit();
        }




        System.out.println("heeey " + getApplicationContext());


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.bringToFront();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,lightFragment)
                .commit();


        connection = new MqttConnection(this, this);
        connection.connect();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabState == 1) {
                    //Add light
                    AddLightFragment f = new AddLightFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
                            .addToBackStack(null)
                            .commit();
                    fab.hide();
                    setTitle("Add mood");
                }
                else if (fabState == 2){
                    //Add room
                    Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
                    startActivity(intent);
                }
                else if (fabState == 3){
                    //Add mood
                    AddMoodActivity f = new AddMoodActivity();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
                            .addToBackStack(null)
                            .commit();
                    fab.hide();
                    setTitle("Add mood");
                }
            }
        });

        //Setting listners for the bottombar, switching fragments and altering the FAB.
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.lightTab){
                    fabState = 1;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,lightFragment)
                            .commit();
                    fab.show();
                    //lightFragment.setLights(connection.getLightArray());
                    setTitle("Lights");

                }
                else if (tabId == R.id.roomTab){
                    fabState = 2;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,roomFragment)
                            .commit();
                    fab.hide();
                    setTitle("Rooms");
                    //roomFragment.setRooms(connection.getRoomArray());
                }
                else if (tabId == R.id.moodTab){
                    fabState = 3;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, moodFragment)
                            .commit();
                    fab.show();
                    setTitle("Moods");
                }
            }

        });
    }

    public void editLight(LightObject data){
            LightObject light = data;
            String color = light.getColor();
            EditLight f = new EditLight(color, light);
            Bundle bundle = new Bundle();

            /*bundle.getString(light.getColor());
            bundle.putString("color", light.getColor());
            bundle.putString("name", light.getName());
            f.setArguments(bundle);*/
            this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, f)
                    .addToBackStack(null)
                    .commit();
            fab.hide();
            setTitle("Edit " + light.getName());
    }

    public void editRoom(LightObject data){
        LightObject light = data;
        String color = light.getColor();
        EditRoom f = new EditRoom(color, light);
        /*Bundle bundle = new Bundle();
        bundle.putString("color", light.getColor());
        bundle.putString("name", light.getRoom());
        f.setArguments(bundle);*/
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, f)
                .addToBackStack(null)
                .commit();
        fab.hide();
        setTitle("Edit " + light.getRoom());
    }
    public void settings(){
        System.out.println("Got to settings()1");
        SettingsFragment f = new SettingsFragment();
        System.out.println("Got to settings()2");
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, f)
                .addToBackStack(null)
                .commit();
        System.out.println("Got to settings()3");
        fab.hide();
        System.out.println("Got to settings()4");
        setTitle("Settings");
    }

    public void updateHomeId(String input){
        homeID = input;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homeID", homeID);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("Got to listener");
        settings();
        return true;
    }


    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getSupportFragmentManager().popBackStack();
            fab.show();
        }
    }

    @Override
    public void callback(String result) {
        System.out.println("got to mainactivity");
        if(fabState == 1){
            lightFragment.setLights(connection.getLightArray());
        }
        else if(fabState == 2) {
            roomFragment.setRooms(connection.getRoomArray());
        }
    }
}
