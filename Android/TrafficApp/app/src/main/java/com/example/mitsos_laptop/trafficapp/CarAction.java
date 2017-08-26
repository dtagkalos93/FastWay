package com.example.mitsos_laptop.trafficapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CarAction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_action);
    }

    public void onNavigation(View view){
        Intent intent = new Intent(CarAction.this, SelectMap.class);
        startActivity(intent);
    }

    public void onLocation(View view){
        Intent intent = new Intent(CarAction.this, Tracker.class);
        startActivity(intent);
    }
}
