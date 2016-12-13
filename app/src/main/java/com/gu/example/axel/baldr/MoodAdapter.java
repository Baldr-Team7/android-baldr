package com.gu.example.axel.baldr;

/**
 * Created by Axel on 12-Dec-16.
 */

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

    public void setMood(int index){
        String message=data[index];
        String topic="asdf";    //TODO Correct string here. If can't access homeid then modify publish method.
        int j=0;
        int i=0;

        while (!(message.charAt(j)=='#')){  // First we remove the moodname from the saved string
            j++;
        }
        message=message.substring(j+1);
        while (message.length()>1){ // There should be a $ at the end
            while (!(message.charAt(i)=='$')){
                i++;
            }
            try {
                JSONObject json = new JSONObject();
                json.put("light", message.substring(0, i - 1)); //anything in the string up to '$' is a json message we send
                message = message.substring(i + 1);            //then there could remain more which we will iterate on
                ma.connection.publishJSON(topic,json);
            }
            catch (JSONException e){
                System.out.println(e);
            }
        }

    }

}
