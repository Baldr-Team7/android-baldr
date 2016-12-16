package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Axel on 02-Oct-16.
 * Made by Mattias and Axel
 */

public class AddLightFragment extends Fragment{

    private TextView textView;
    private EditText addcode;
    private Button add;
    MainActivity ma;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_light, container, false);

        ma = (MainActivity) container.getContext();


        textView = (TextView) view.findViewById(R.id.text);
        addcode   = (EditText)view.findViewById(R.id.discoveryCode);
        add = (Button) view.findViewById(R.id.button3);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLight();
            }
        });


        return view;
    }



    public void addLight() {
        JSONObject jin =  new JSONObject();
        JSONObject newLight = new JSONObject();
        String code = addcode.getText().toString();
        // TODO Give error if code isn't correct format
        String topic="lightcontrol/discovery";
        try {
            String homeid=ma.homeID;
            jin.put("discoveryCode",code);
            jin.put("home",homeid);
            newLight.put("version", 1 );
            newLight.put("protocolName", "baldr");
            newLight.put("discovery",jin);
            ma.connection.publishJSON(topic,newLight);
        }catch(JSONException e){
            System.out.println(e);
        }

    }

}