package com.gu.example.axel.baldr;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.lang.reflect.Field;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.widget.AmbilWarnaPrefWidgetView;

/**
 * Created by Axel on 06-Oct-16.
 */

public class EditLight extends Fragment implements CustomListener {


    private String colorStr;
    private String lName;
    public AmbilWarnaDialog dialog;
    Button colorBtn;



    MqttConnection connection;
    LightObject light;


    public EditLight(String colorStr, LightObject light){
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

                System.out.println("Before set color: " + light.getId() + " had " + light.getColor());
               light.setColor(colorStr);
                System.out.println("After set color: " + light.getId() + " has " + light.getColor());
                connection.publishColor(light);

                dialog.show();

            }
        });


        colorBtn = (Button) view.findViewById(R.id.button4);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

        EditText lNameEdit = (EditText) view.findViewById(R.id.lNameEdit);
        lNameEdit.setText(lName);

        return view;
    }



    @Override
    public void callback(String result) {

    }

}
