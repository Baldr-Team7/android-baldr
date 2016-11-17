package com.gu.example.axel.baldr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import java.lang.reflect.Field;

/**
 * Created by Axel on 06-Oct-16.
 */

public class EditLight extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_light, container, false);

        return view;
    }

}
