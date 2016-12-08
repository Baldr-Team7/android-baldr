package com.gu.example.axel.baldr;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Axel on 06-Oct-16.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    LightObject[] data;
    MqttConnection connection;
    private int adapterCheck = 0;

    public LayoutInflater inflater;


    public CustomAdapter(Context context, LightObject[] lightItems, MqttConnection connection, int adapterCheck){
        this.context = context;
        // this.lightItems = lightItems;
        data = lightItems;
        this.connection = connection;
        this.adapterCheck = adapterCheck;


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
    public View getView( final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View vi = null;

        if(adapterCheck == 1) {
            vi = View.inflate(context, R.layout.light_row, null);
            lightAdapter(position, vi);
        }
        else if(adapterCheck == 2) {
            vi = View.inflate(context, R.layout.room_row, null);
            roomAdapter(position, vi);

        }

        return vi;
    }



    public void roomAdapter(final int position, View vi) {
        TextView roomName = (TextView) vi.findViewById(R.id.roomName);
        Switch roomSwitch = (Switch) vi.findViewById(R.id.roomSwitch);

        TextView editRoom = (TextView) vi.findViewById(R.id.touchEditRoom);

        System.out.println("Nr " +data[position].getId() + " is " + data[position].getState());

        if(data[position].getState().equals("on")){
            roomSwitch.setChecked(true);
        }
        if(data[position].equals(data[position])) {
            roomName.setText(data[position].getRoom());
        }

        editRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //long pTemp = getItemId(p);
                int p = position;

                MainActivity ma = (MainActivity) context;
                ma.editRoom(data[p]);


            }
        });


            if(data[position].getState().equals("on")) {
                roomSwitch.setChecked(true);
        }
    /*    roomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });*/

            roomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (data[position].getState().equals("on")) {

                        connection.publishRoom(data[position]);
                        System.out.println(context);
                    } else {
                        connection.publishRoom(data[position]);
                        System.out.println(context);
                    }
                });



                }
            }
    }



    public void lightAdapter(final int position, View vi){
        final int p = position;
        //View vi = View.inflate(context, R.layout.light_row, null);
        TextView lName = (TextView) vi.findViewById(R.id.lightName);
        TextView lRoom = (TextView) vi.findViewById(R.id.lightRoom);
        Switch lSwitch = (Switch) vi.findViewById(R.id.lightSwitch);
        TextView editLight = (TextView) vi.findViewById(R.id.touchEditLight);

        lName.setText(data[position].getState() + data[position].getId());
        lRoom.setText(data[position].getRoom());

        //lSwitch.setChecked(true);

        System.out.println("Nr " +data[position].getId() + " is " + data[position].getState());

        if(data[position].getState().equals("on")){
            lSwitch.setChecked(true);
        }

        vi.setTag(data[position].getId());

        editLight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //long pTemp = getItemId(p);
                int p = position;

                MainActivity ma = (MainActivity) context;
                ma.editLight(data[p]);


            }
        });


        lSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long pTemp = getItemId(p);
                int p = (int)pTemp;
                if(data[position].getState().equals("on")){

                    connection.publish(data[position]);
                    System.out.println(context);
                }
                else{
                    //data[p].getId() + "on"

                    connection.publish(data[position]);
                    System.out.println(context);
                }
            }
        });
    }
}


