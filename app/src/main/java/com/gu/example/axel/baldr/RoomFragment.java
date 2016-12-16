package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Axel on 02-Oct-16.
 * Made by Aras and Axel
 */

public class RoomFragment extends Fragment {

    private ListView roomList = null;
    private CustomAdapter adapter;
    private LightObject[] roomArray = new LightObject[0];
    MainActivity ma;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rooms, container, false);

        roomList = (ListView) view.findViewById(R.id.roomList);

        ma = (MainActivity) container.getContext();

        setRooms(ma.connection.getRoomArray());

        return view;
    }

    //Gets the latest array and populates UI with rooms.
    public void setRooms(LightObject[] rooms){
        adapter = new CustomAdapter(ma, rooms, 2);
        roomList.setAdapter(adapter);
    }

}
