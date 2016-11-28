package com.gu.example.axel.baldr;

import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.Field;
import java.util.List;

import static android.R.id.list;


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

       /* MqttConnection sender = new MqttConnection(getContext());
        sender.connect();
        sender.subscribe();*/

        /*lList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Clicked light " + view.getTag(), Toast.LENGTH_SHORT).show();

            }
        });*/


        return view;
    }


    //lightList = (ListView)findViewByID(R.layout.light_row);
}
