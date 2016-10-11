package com.gu.example.axel.baldr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Axel on 06-Oct-16.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    LightObject[] data;
    private static LayoutInflater xInflater = null;

    public CustomAdapter(Context context, LightObject[] data){
        this.context = context;
        this.data = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = View.inflate(context, R.layout.light_row, null);
        TextView lName = (TextView) vi.findViewById(R.id.lightName);
        TextView lRoom = (TextView) vi.findViewById(R.id.lightRoom);
        Switch lSwitch = (Switch) vi.findViewById(R.id.lightSwitch);

        lName.setText(data[position].getName());
        lRoom.setText(data[position].getRoom());

        vi.setTag(data[position].getId());

        lSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(data[position].getState()){
                    data[position].setState(false);
                }
                else{
                    data[position].setState(true);
                }
            }
        });

        return vi;
    }
}
