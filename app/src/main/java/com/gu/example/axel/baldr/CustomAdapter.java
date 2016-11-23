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

    JSONParser jsonParser;
    Context context;
    LightObject[] data;
    MqttConnection sender;

    public List<Json> lightItems;
    public LayoutInflater inflater;


    public CustomAdapter(Context context, List<Json> lightItems){
        this.context = context;
        this.lightItems = lightItems;
        sender = new MqttConnection(context);
        sender.connect();

    }

    @Override
    public int getCount() {

        return lightItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return lightItems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.light_row, parent,false);
        Json items = (Json) getItem(position);
        TextView lName = (TextView) rowView.findViewById(R.id.lightName);
        //TextView lRoom = (TextView) rowView.findViewById(R.id.lightRoom);
        Switch lSwitch = (Switch) rowView.findViewById(R.id.lightSwitch);

/*        View vi = View.inflate(context, R.layout.light_row, null);
        TextView lName = (TextView) vi.findViewById(R.id.lightName);
        TextView lRoom = (TextView) vi.findViewById(R.id.lightRoom);
        Switch lSwitch = (Switch) vi.findViewById(R.id.lightSwitch);
        TextView edit = (TextView) vi.findViewById(R.id.touchEdit);

        lName.setText(data[position].getName());
        lRoom.setText(data[position].getRoom());

        vi.setTag(data[position].getId());

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                long pTemp = getItemId(position);
                int p = (int)pTemp;

                MainActivity ma = (MainActivity) context;
                ma.editLight(data[p]);


            }
        });*/

        lName.setText(items.getLightInfo().getRoom());

        lSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long pTemp = getItemId(position);
                int p = (int)pTemp;
                if(data[p].getState()){
                    //data[p].getId() + "off"


                    sender.publish();
                    data[p].setState(false);
                    System.out.println(context);
                }
                else{
                    //data[p].getId() + "on"

                    sender.publish();

                    data[p].setState(true);
                    System.out.println(context);
                }
            }
        });

        return rowView;
    }








}


