package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ListView;

import java.util.Collections;
import java.util.Set;
import org.json.*;
import org.json.JSONObject;

/**
 * Created by Axel on 02-Oct-16.
 */

public class MoodFragment extends Fragment implements CustomListener {

    View v;
    SharedPreferences preferences;
    Set <String> moodlist = Collections.emptySet();
    String[] moodarray;
    MoodAdapter adapter;
    MqttConnection conn;
    ListView roomList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.moods, container, false);

        roomList = (ListView) v.findViewById(R.id.roomList);

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        moodlist=preferences.getStringSet("moods",moodlist);
        if (!moodlist.isEmpty()){
            moodarray=moodlist.toArray(new String[0]);
            for(int i=0;i<moodarray.length;i++){
                /*TODO Draw moods on screen. Make them clickable. The JSON needed to set a mood if user chooses to is available in moodarray.
                Structure of string will be Moodname#JSON-String so we will need to remove anything up to and including the # before we do the string->JSON.
                Each light will be separated by $ character, the setMood will send 1 message for each light through the MQTTConnection
                Does have drawback of not allowing # in moodnames, maybe there is a better way.
                The eventhandler only needs to call setMood with correct index.
                */
            }
        }

        conn = new MqttConnection(getContext(), this); //TODO Context and stuff missing
        conn.connect();

        moodarray=moodlist.toArray(new String[0]);

        adapter = new MoodAdapter(getContext(), moodarray, conn);
        roomList.setAdapter(adapter);
        return v;
    }



    @Override
    public void callback(String result) {

    }
}

/*  TODO Goes in MQTTConnection
    public void publishJSON (String topic, JSONObject json){
        try{
            client.publish(topic,json.toString().getBytes(),0,false);
        } catch (MqttException e){
            e.printStackTrace();
        }
    }
 */