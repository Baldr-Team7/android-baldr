package com.gu.example.axel.baldr;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ListView;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by Axel on 02-Oct-16.
 * Made by Mattias and Axel.
 */

public class MoodFragment extends Fragment {

    View v;
    SharedPreferences preferences;
    Set<String> moodlist = new HashSet<String>();
    String[] moodarray;
    MoodAdapter adapter;
    MainActivity ma;
    ListView listing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.moods, container, false);

        listing = (ListView) v.findViewById(R.id.moodList);

        ma = (MainActivity) container.getContext();

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        moodlist = preferences.getStringSet("moods", null);
        if (!(moodlist == null)) {
            moodarray = moodlist.toArray(new String[0]);
            /*for (int i = 0; i < moodarray.length; i++) {
                /*TODO Draw moods on screen. Make them clickable. The JSON needed to set a mood if user chooses to is available in moodarray.
                Structure of string will be Moodname#JSON-String so we will need to remove anything up to and including the # before we do the string->JSON.
                Each light will be separated by $ character, the setMood will send 1 message for each light through the MQTTConnection
                Does have drawback of not allowing # in moodnames, maybe there is a better way.
                The eventhandler only needs to call setMood with correct index.

            }*/


            //moodarray = moodlist.toArray(new String[0]);

            adapter = new MoodAdapter(ma, moodarray);
            listing.setAdapter(adapter);
        }

        return v;
    }

}
