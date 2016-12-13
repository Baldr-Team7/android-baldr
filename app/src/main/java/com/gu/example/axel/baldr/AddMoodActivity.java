package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashSet;
import java.util.Set;



/* Created by Axel on 02-Oct-16.
 */

public class AddMoodActivity extends Fragment{
    private Button save;
    private EditText moodname;
    MainActivity ma;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_mood, container, false);


        ma = (MainActivity) container.getContext();

        Button save = (Button) view.findViewById(R.id.button2);

        moodname = (EditText) view.findViewById(R.id.moodName);
        moodname.setText("test");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMood(view);
            }
        });
        return view;
    }


    public void saveMood(View view){

        String mood = moodname.getText().toString();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ma);
        SharedPreferences.Editor editor = preferences.edit();
        Set <String> moodSet = new HashSet<String>();
        moodSet=preferences.getStringSet("moods", moodSet);


        if (mood.length()>0) {          // First get moodname from EditText, if empty we just do nothing
            mood+='#';                  // Add # at end of name as delimiter

            LightObject[] lights = ma.connection.getLightArray();
            for (int i=0;i<lights.length-1;i++){          // Go through lightarray and add to string for each
                mood+= lights[i].getJSON().toString()+'$';  // then end with '$'
            }
            Set<String> newMoodSet = new HashSet<String>();
            newMoodSet.addAll(moodSet);
            newMoodSet.add(mood);
            editor.putStringSet("moods", newMoodSet);
            editor.commit();
            System.out.println("Mood saved");
        }}

}