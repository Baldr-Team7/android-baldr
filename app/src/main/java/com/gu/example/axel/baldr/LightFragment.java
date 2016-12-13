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

public class LightFragment extends Fragment {
    private ListView lList = null;
    private CustomAdapter adapter;
    private LightObject[] lArray = new LightObject[0];
    public MqttConnection connection;
    MainActivity ma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.lights, container, false);
        lList = (ListView) view.findViewById(R.id.lightList);

         ma = (MainActivity) container.getContext();

        setLights(ma.connection.getLightArray());

        return view;
    }

    public void setLights(LightObject[] lightArray){
        System.out.println("got to light fragment");
        adapter = new CustomAdapter(ma, lightArray, 1);
        lList.setAdapter(adapter);
    }

}

