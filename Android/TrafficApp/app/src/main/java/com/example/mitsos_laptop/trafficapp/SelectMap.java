package com.example.mitsos_laptop.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class SelectMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);
    }

    public void onStandar(View view){
        Intent intent = new Intent(SelectMap.this, Destination.class);
        startActivity(intent);
    }

    public void onFast(View view){
        NetConnection con = new NetConnection();
        con.findDestination(this);


    }

}