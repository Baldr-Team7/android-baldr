package com.gu.example.axel.baldr;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Axel on 02-Oct-16.
 */

public class AddLightActivity extends AppCompatActivity implements CustomListener{

    private TextView textView;
    private EditText addcode;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_light);

        Button btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.text);
        addcode   = (EditText)findViewById(R.id.discoveryCode);
        add = (Button) findViewById(R.id.button3);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLight(view);

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        setTitle("New Light");
    }

    public void exitAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void addLight(View view) {
        JSONObject jin =  new JSONObject();
        JSONObject newLight = new JSONObject();
        String code = addcode.getText().toString();
        // TODO Give error if code isn't correct format
        String topic="/lightcontrol/discovery/";
        try {
            String homeid="asdf";
            jin.put("discoveryCode",code);
            jin.put("home",homeid);
            newLight.put("version", 1);
            newLight.put("protocolName", "baldr");
            newLight.put("discovery",jin);
            MqttConnection conn = new MqttConnection(getApplicationContext(), this); // TODO Context and stuff missing
            conn.connect();
            conn.publishJSON(topic,newLight);
        }catch(JSONException e){
            System.out.println(e);
        }

    }

    @Override
    public void callback(String result) {

    }

}