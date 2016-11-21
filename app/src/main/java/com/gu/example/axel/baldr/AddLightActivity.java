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

                new JSONParser().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt");

                // textView.setText(s);

            }
        });
        setTitle("New Light");
    }

    public void exitAdd(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }







   /*public class JSONParser extends AsyncTask<String, String, String> {

        //TextView textView;
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

               return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textView.setText(s);

        }
    }*/

}