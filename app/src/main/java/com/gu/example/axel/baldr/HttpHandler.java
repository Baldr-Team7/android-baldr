package com.gu.example.axel.baldr;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by arasb on 2016-11-19.
 */

public class HttpHandler {

    private static final String TAg = HttpHandler.class.getSimpleName();

    public HttpHandler() {

    }


    public String httpServiceCall(String urlReq) {
        String result = null;

        try {
            URL url = new URL(urlReq);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Read call backs
            InputStream in = new BufferedInputStream(connection.getInputStream());
            result = streamToString(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            //e.getMessage();
            e.printStackTrace();
        }

        return result;
    }



    private String streamToString(InputStream inputStream){
        BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String readLine;
        try {
            while( (readLine = bufferedReader.readLine()) != null){
                stringBuilder.append(readLine).append('\n');
            }
        } catch (IOException e) {
                e.printStackTrace();
            }
        finally {
            try {
                inputStream.close();
            }
         catch (IOException e) {
            e.printStackTrace();
        }
        }
        return  stringBuilder.toString();
    }

    }



