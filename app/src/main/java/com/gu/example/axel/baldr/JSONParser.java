package com.gu.example.axel.baldr;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by arasb on 2016-11-20.
 */

public class JSONParser extends AsyncTask<String, String, String> {

    String data;
    String protocolName;
    String color;
    String state;
    String room;
    String mood;

    
    @Override
    protected String doInBackground(String... params) {
     return null;


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //ad.textView.setText(s);

    }
}
