package com.gu.example.axel.baldr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Axel on 02-Oct-16.
 */

public class AddMoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mood);

    }

    public void exitAdd(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
