package com.gu.example.axel.baldr;


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


public class MainActivity extends AppCompatActivity {

    private int fabState = 1;
    BottomBar bottomBar;
    private Toolbar toolbar;
    FloatingActionButton fab;
    SharedPreferences preferences;

    public String homeID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabState == 1) {
                    //Add light
                    AddLightFragment f = new AddLightFragment();
                    fabState = 1;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
                            .addToBackStack(null)
                            .commit();
                    fab.hide();
                    setTitle("Lights");
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
                    LightFragment f = new LightFragment();
                    fabState = 1;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
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
                            .commit();
                    fab.hide();
                    setTitle("Rooms");
                }
                else if (tabId == R.id.moodTab){
                    MoodFragment f = new MoodFragment();
                    fabState = 3;
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame,f)
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

}
