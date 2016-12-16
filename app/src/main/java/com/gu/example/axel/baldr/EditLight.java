package com.gu.example.axel.baldr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * Created by Axel on 06-Oct-16.
 */

public class EditLight extends Fragment {


    private String colorStr;

    private String lName;
    private String rName;

    public AmbilWarnaDialog dialog;
    Button colorBtn;
    Button saveAll;

    MainActivity ma;

    EditText editLname;
    EditText editRname;


    LightObject light;


    public EditLight(String colorStr, LightObject light){
        this.colorStr = colorStr;
        this.light = light;
        lName = this.light.getName();
    }



    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_light, container, false);

        ma = (MainActivity) container.getContext();


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
                ma.connection.publishColor(light);

                dialog.show();

            }
        });



        editLname = (EditText) view.findViewById(R.id.editLname);
        editLname.setText(light.getName());

        editRname = (EditText) view.findViewById(R.id.editRname);
        editRname.setText(light.getRoom());



        saveAll = (Button) view.findViewById(R.id.saveAll);
        saveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!editLname.getText().toString().equals(light.getName()) &&
                        !editRname.getText().toString().equals(light.getRoom())){

                    light.setName(editLname.getText().toString());
                    light.setRoom(editRname.getText().toString());

                    ma.connection.publishNameChange(light);
                    ma.connection.publishLightChangeRoom(light);
                }
                else if(!editLname.getText().toString().equals(light.getName())){
                    light.setName(editLname.getText().toString());

                    ma.connection.publishNameChange(light);
                }
                else if(!editRname.getText().toString().equals(light.getRoom())){
                    light.setRoom(editRname.getText().toString());
                    ma.connection.publishLightChangeRoom(light);
                }
                else{
                    System.out.println("Nothing changed");
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