package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Axel on 02-Oct-16.
 */

public class LightFragment extends Fragment implements CustomListener {
    //View v;
    private ListView lList = null;
    private CustomAdapter adapter;
    private LightObject[] lArray = new LightObject[0];
    public MqttConnection connection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights, container, false);
        lList = (ListView) view.findViewById(R.id.lightList);
        //setHasOptionsMenu(true);


        connection = new MqttConnection(getContext(), this);
        connection.connect();

        System.out.println("Larry lrngth " + lArray.length);

        adapter = new CustomAdapter(getContext(), lArray, connection, 1);
        lList.setAdapter(adapter);


        return view;
    }

    @Override
    public void callback(String result){
        lArray = connection.getLightArray();
        for (int i = 0; i < lArray.length; i++) {
            System.out.println("in lightfragment callback : LightList["+ i + "] = " + lArray[i].getId() + " " + lArray[i].getState() + " "
                    + lArray[i].getColor() + " " + lArray[i].getRoom());
        }
        adapter = new CustomAdapter(getContext(), lArray, connection, 1);
        lList.setAdapter(adapter);
    }

    public void setLights(LightObject[] lightArray){
        lArray = lightArray;
        adapter = new CustomAdapter(getContext(), lArray, connection, 1);
        lList.setAdapter(adapter);
    }


    //lightList = (ListView)findViewByID(R.layout.light_row);

}

