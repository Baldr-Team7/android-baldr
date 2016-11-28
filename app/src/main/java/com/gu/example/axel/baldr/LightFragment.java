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

public class LightFragment extends Fragment {
    //View v;
    private ListView lList = null;
    private CustomAdapter adapter;
    private LightObject[] lArray = new LightObject[0];
    public MqttConnection connection;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights, container, false);
        lList = (ListView) view.findViewById(R.id.lightList);

        connection = new MqttConnection(getContext());
        connection.connect();

        lArray = connection.getLightArray();


        System.out.println("Larry lrngth " + lArray.length);



        adapter = new CustomAdapter(getContext(), lArray, connection);
        lList.setAdapter(adapter);


        return view;
    }




    //lightList = (ListView)findViewByID(R.layout.light_row);
}
