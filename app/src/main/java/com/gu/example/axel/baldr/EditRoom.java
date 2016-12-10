package com.gu.example.axel.baldr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.widget.AmbilWarnaPrefWidgetView;

/**
 * Created by Axel on 06-Oct-16.
 */

public class EditRoom extends Fragment implements CustomListener {

    private String colorStr;
    private String lName;
    public AmbilWarnaDialog dialog;
    Button colorBtn;
    Button saveBtn;
    TextView input;



    MqttConnection connection;
    LightObject light;


    public EditRoom(String colorStr, LightObject light){
        this.colorStr = colorStr;
        this.light = light;
    }



    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_light, container, false);

        connection = new MqttConnection(getContext(), this);
        connection.connect();

        Bundle bundle = this.getArguments();
        if(bundle != null){
            // colorStr = bundle.getString("color", light.getColor());


            System.out.println(colorStr);
            lName = bundle.getString("name");

        }

        dialog = new AmbilWarnaDialog(getContext(), 000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                // Get Hex color code
                colorStr = "#" +Integer.toHexString(color).substring(2).toUpperCase();

                System.out.println("Before set color: " + light.getRoom() + " had " + light.getColor());
                light.setColor(colorStr);
                System.out.println("After set color: " + light.getRoom() + " has " + light.getColor());
                connection.publishColorRoom(light);

                dialog.show();

            }
        });

        input = (TextView) view.findViewById(R.id.lNameEdit);
        input.setText(light.getName());

        saveBtn = (Button) view.findViewById(R.id.nameBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.setName(input.getText().toString());
                connection.publishNameChangeRoom(light);
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



    @Override
    public void callback(String result) {

    }

}
