package com.gu.example.axel.baldr;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Axel on 06-Oct-16.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    LightObject[] data;
    MqttConnection sender;
    private static LayoutInflater xInflater = null;


    public CustomAdapter(Context context, LightObject[] data){
        this.context = context;
        this.data = data;
        sender = new MqttConnection(context);
        sender.connect();
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = View.inflate(context, R.layout.light_row, null);
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
                ma.editLight();


            }
        });

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

        return vi;
    }
}
