package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Axel on 08-Dec-16.
 */



public class SettingsFragment extends Fragment {

    Button save;
    TextView text;
    String houseId;
    MainActivity ma;
    EditText input;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings, container, false);

        ma = (MainActivity) getContext();

        text = (TextView) view.findViewById(R.id.houseId);
        houseId = ma.homeID;
        text.setText("Your Current HomeID is: " + houseId);

        input = (EditText)view.findViewById(R.id.editHouseId);


        save = (Button) view.findViewById(R.id.saveHouseId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Remove local data, and set the subscription to the new homeId.
                ma.connection.resetData();
                ma.connection.unsubscribe();
                text.setText("Your Current HomeID is: " + changeHomeId(input.getText().toString()));
                ma.connection.subscribe();


            }
        });

        return view;
    }

    //Changes the homeId saved on the phone and returns the new one.
    public String changeHomeId(String input){

        ma.updateHomeId(input);


        return houseId = input;
    }
}
