package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Axel on 02-Oct-16.
 */

public class AddLightFragment extends Fragment implements CustomListener{

    private TextView textView;
    private EditText addcode;
    private Button add;
    MainActivity ma;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_light, container, false);

        ma = (MainActivity) container.getContext();


        Button btn = (Button) view.findViewById(R.id.button);
        textView = (TextView) view.findViewById(R.id.text);
        addcode   = (EditText)view.findViewById(R.id.discoveryCode);
        add = (Button) view.findViewById(R.id.button3);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Button clicked");
                addLight();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        return view;
    }

  /*  public void exitAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    public void addLight() {
        System.out.println("Got into addLight");
        JSONObject jin =  new JSONObject();
        JSONObject newLight = new JSONObject();
        String code = addcode.getText().toString();
        // TODO Give error if code isn't correct format
        String topic="lightcontrol/discovery";
        try {
            String homeid="asdf";
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

    @Override
    public void callback(String result) {

    }

}