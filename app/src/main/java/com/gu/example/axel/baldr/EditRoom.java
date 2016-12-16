package com.gu.example.axel.baldr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * Created by Axel on 06-Oct-16.
 * Made by Aras and Axel.
 */

public class EditRoom extends Fragment {

    private String colorStr;
    private String lName;
    public AmbilWarnaDialog dialog;
    Button colorBtn;
    Button saveBtn;
    TextView input;

    LightObject light;
    MainActivity ma;


    public EditRoom(String colorStr, LightObject light){
        this.colorStr = colorStr;
        this.light = light;
    }



    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_room, container, false);

        ma = (MainActivity) container.getContext();



        dialog = new AmbilWarnaDialog(getContext(), 000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                // Get Hex color code
                colorStr = "#" +Integer.toHexString(color).substring(2).toUpperCase();

                light.setColor(colorStr);
                ma.connection.publishColorRoom(light);

                dialog.show();

            }
        });

        input = (TextView) view.findViewById(R.id.lNameEdit);
        input.setText(light.getName());

        saveBtn = (Button) view.findViewById(R.id.nameBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().equals("")) {
                    String oldRoom = light.getRoom();
                    light.setRoom(input.getText().toString());
                    ma.connection.publishRoomChangeRoom(light, oldRoom);
                }
            }
        });


        colorBtn = (Button) view.findViewById(R.id.colorBtn);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });


        return view;
    }



}
