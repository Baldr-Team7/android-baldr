package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
        text.setText(houseId);

        input = (EditText)view.findViewById(R.id.editHouseId);


        save = (Button) view.findViewById(R.id.saveHouseId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(changeHomeId(input.getText().toString()));

            }
        });

        return view;
    }

    public String changeHomeId(String input){

        ma.updateHomeid(input);


        return houseId = input;
    }
}
