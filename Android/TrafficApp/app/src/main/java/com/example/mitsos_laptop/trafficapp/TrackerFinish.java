package com.example.mitsos_laptop.trafficapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class TrackerFinish extends Activity {

    private LocationData trackData;
    private TextView totalDistance;
    private TextView totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker_finish);
        trackData = new LocationData(this);

        totalDistance = (TextView) findViewById(R.id.distance);
        totalTime = (TextView) findViewById(R.id.time);
        totalDistance.setText(trackData.getTotalDistance());
        totalTime.setText(trackData.getTotalTime());
    }

    public void finish(View view){




            Intent intent = new Intent(TrackerFinish.this, MainActivity.class);
            startActivity(intent);
            finish();



    }



}
