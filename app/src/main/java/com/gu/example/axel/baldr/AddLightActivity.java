package com.gu.example.axel.baldr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Axel on 02-Oct-16.
 */

public class AddLightActivity extends AppCompatActivity {


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_light);

        Button btn = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.text);


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

}