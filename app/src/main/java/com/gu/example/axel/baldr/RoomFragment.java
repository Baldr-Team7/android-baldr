package com.gu.example.axel.baldr;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Axel on 02-Oct-16.
 */

public class RoomFragment extends Fragment implements CustomListener {

    private ListView roomList = null;
    private CustomAdapter adapter;
    private LightObject[] roomArray = new LightObject[0];
    public MqttConnection connection;


    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.rooms, container, false);
        roomList = (ListView) v.findViewById(R.id.roomList);

        for(int i = 0; i < roomArray.length; i++) {
            System.out.println(roomArray[i].getRoom());
        }

        adapter = new CustomAdapter(getContext(), roomArray, connection, 1);
        roomList.setAdapter(adapter);

        return v;
    }

    @Override
    public void callback(String result){
        roomArray = connection.getLightArray();
        for (int i = 0; i < roomArray.length; i++) {
            System.out.println("in roomfragment callback : RoomList["+ i + "] = " + roomArray[i].getRoom());
        }
        adapter = new CustomAdapter(getContext(), roomArray, connection, 1);
        roomList.setAdapter(adapter);
    }
}
