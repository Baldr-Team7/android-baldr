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
    AddLightActivity ad;
        //TextView textView;
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url  = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return  null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //ad.textView.setText(s);

    }
}
