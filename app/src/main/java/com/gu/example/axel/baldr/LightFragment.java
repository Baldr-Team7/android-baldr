package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        connection = new MqttConnection(getContext(), this);
        connection.connect();




        System.out.println("Larry lrngth " + lArray.length);


        return view;
    }

    @Override
    public void callback(String result){
        lArray = connection.getLightArray();
        adapter = new CustomAdapter(getContext(), lArray, connection);
        lList.setAdapter(adapter);
    }


    //lightList = (ListView)findViewByID(R.layout.light_row);

}

