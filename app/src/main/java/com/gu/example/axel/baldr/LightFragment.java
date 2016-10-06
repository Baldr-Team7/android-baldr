package com.gu.example.axel.baldr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
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
    private LightObject[] lArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights, container, false);
        lList = (ListView) view.findViewById(R.id.lightList);

        lArray = new LightObject[20];

        lArray[0] = new LightObject("Light1", "No Room", 0, 1);
        lArray[1] = new LightObject("Light2", "No Room", 0, 2);
        lArray[2] = new LightObject("Light3", "Kitchen", 0, 3);
        lArray[3] = new LightObject("Light4", "Kitchen", 0, 4);
        lArray[4] = new LightObject("Light5", "Living room", 0, 5);
        lArray[5] = new LightObject("Light6", "Living room", 0, 6);
        lArray[6] = new LightObject("Light7", "Living room", 0, 7);
        lArray[7] = new LightObject("Light8", "Living room", 0, 8);
        lArray[8] = new LightObject("Light9", "Living room", 0, 9);
        lArray[9] = new LightObject("Light10", "Living room", 0, 10);
        lArray[10] = new LightObject("Light11", "Living room", 0, 11);
        lArray[11] = new LightObject("Light12", "Living room", 0, 12);
        lArray[12] = new LightObject("Light13", "Living room", 0, 13);
        lArray[13] = new LightObject("Light14", "Living room", 0, 14);
        lArray[14] = new LightObject("Light15", "Living room", 0, 15);
        lArray[15] = new LightObject("Light16", "Living room", 0, 16);
        lArray[16] = new LightObject("Light17", "Living room", 0, 17);
        lArray[17] = new LightObject("Light18", "Living room", 0, 18);
        lArray[18] = new LightObject("Light19", "Living room", 0, 19);
        lArray[19] = new LightObject("Light20", "Living room", 0, 20);

        adapter = new CustomAdapter(getContext(), lArray);
        lList.setAdapter(adapter);


        return view;
    }


    //lightList = (ListView)findViewByID(R.layout.light_row);
}
