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


/**
 * Created by Axel on 02-Oct-16.
 */

public class LightFragment extends Fragment {
    //View v;
    private ListView lList = null;
    private CustomAdapter adapter;
    private LightObject[] lArray;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights, container, false);
        lList = (ListView) view.findViewById(R.id.lightList);

        lArray = new LightObject[19];

        lArray[0] = new LightObject("Light2", "No Room", false, 1);
        lArray[1] = new LightObject("Light3", "Kitchen", true, 2);
        lArray[2] = new LightObject("Light4", "Kitchen", false, 3);
        lArray[3] = new LightObject("Light5", "Living room", true, 4);
        lArray[4] = new LightObject("Light6", "Living room", false, 5);
        lArray[5] = new LightObject("Light7", "Living room", true, 6);
        lArray[6] = new LightObject("Light8", "Living room", false, 7);
        lArray[7] = new LightObject("Light9", "Living room", false, 8);
        lArray[8] = new LightObject("Light10", "Living room", false, 9);
        lArray[9] = new LightObject("Light11", "Living room", false, 10);
        lArray[10] = new LightObject("Light12", "Living room", false, 11);
        lArray[11] = new LightObject("Light13", "Living room", false, 12);
        lArray[12] = new LightObject("Light14", "Living room", false, 13);
        lArray[13] = new LightObject("Light15", "Living room", false, 14);
        lArray[14] = new LightObject("Light16", "Living room", false, 15);
        lArray[15] = new LightObject("Light17", "Living room", false, 16);
        lArray[16] = new LightObject("Light18", "Living room", false, 17);
        lArray[17] = new LightObject("Light19", "Living room", false, 18);
        lArray[18] = new LightObject("Light20", "Living room", false, 19);

        adapter = new CustomAdapter(getContext(), lArray);
        lList.setAdapter(adapter);

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
