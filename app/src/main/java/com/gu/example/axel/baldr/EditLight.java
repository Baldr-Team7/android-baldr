package com.gu.example.axel.baldr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.widget.Button;

import java.lang.reflect.Field;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.widget.AmbilWarnaPrefWidgetView;

/**
 * Created by Axel on 06-Oct-16.
 */

public class EditLight extends Fragment {

    private String colorStr;
    private int color;
    public AmbilWarnaDialog dialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_light, container, false);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            colorStr = bundle.getString("color", "#FFFFFF");
            System.out.println(colorStr);
            colorStr = colorStr.substring(1);
            System.out.println(colorStr);
            //color = Integer.valueOf(colorStr);

        }

        dialog = new AmbilWarnaDialog(getContext(), 000000, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                System.out.println("Send new color: " + color);
            }
        });

        Button colorBtn = (Button) view.findViewById(R.id.colorBtn);

        colorBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });


        return view;
    }

}