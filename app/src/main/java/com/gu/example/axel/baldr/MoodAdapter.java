package com.gu.example.axel.baldr;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Axel on 12-Dec-16.
 * made by MAttias and Axel
 */

public class MoodAdapter extends BaseAdapter {

    Context context;
    String[] data;
    MainActivity ma;
    private int adapterCheck = 0;

    public LayoutInflater inflater;


    public MoodAdapter(Context context, String[] data){
        this.context = context;
        this.data = data;
        ma = (MainActivity) this.context;

    }

    @Override
    public int getCount() {

        return data.length;
    }

    @Override
    public Object getItem(int position)
    {
        return data[position];
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        View vi = View.inflate(context, R.layout.room_row, null);
        moodAdapter(position, vi);



        return vi;
    }

    public void moodAdapter(final int position, View vi) {
        TextView moodName = (TextView) vi.findViewById(R.id.roomName);
        Switch moodSwitch = (Switch) vi.findViewById(R.id.roomSwitch);

        int i=0;
        String mood=data[position];
        while (!(mood.charAt(i)=='#'))
            i++;

        moodName.setText(mood.substring(0,i));





        moodSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                setMood(position);
            }
        });
    }

    public void setMood(int index) {
        String message = data[index];
        String topic, id;
        int j = 0;
        int i = 0;

        while (!(message.charAt(j) == '#')) {  // First we remove the moodname from the saved string
            j++;
        }
        message = message.substring(j + 1);
        while (message.length() > 1) { // There should be a $ at the end
            i = 0; // start from beginning of string
            while (!(message.charAt(i) == '$')) {  // step through string until we find $ that marks end of a light
                i++;
            }
            try {
                JSONObject json = new JSONObject(message.substring(0, i));
                JSONObject light = new JSONObject();
                //              json.put("light", message.substring(0, i - 1)); //anything in the string up to '$' is a json message we send
                if (message.length() > i + 1) { // Is there anything behind the last $?
                    message = message.substring(i + 1);            // If so cut the string and keep going
                } else { // there is nothing behind the last $, so just cut anything ahead of it and be done
                    message = "$";
                }
                light = json.getJSONObject("lightCommand");
                id = light.getString("id");
                topic = "lightcontrol/home/" + ma.connection.homeID + "/light/" + id + "/commands";
                ma.connection.publishJSON(topic, json);
            } catch (JSONException e) {
                System.out.println(e);
            }
        }

    }
}