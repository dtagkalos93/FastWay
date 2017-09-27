package com.example.mitsos_laptop.trafficapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class SelectMap extends AppCompatActivity {

    private NetConnection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_map);

    }

    public void onStandar(View view){
        con = new NetConnection();
        con.isNetworkAvailable(this);
        Intent intent = new Intent(SelectMap.this, Destination.class);
        startActivity(intent);
    }

    public void onFast(View view){
        con = new NetConnection();
        con.isNetworkAvailable(this);
        disabeButtons();
        NetConnection con = new NetConnection();
        con.findDestination(this);


    }

    private void disabeButtons() {
        ((Button) findViewById(R.id.fast)).setEnabled(false);
        ((Button) findViewById(R.id.standar)).setEnabled(false);
        ((ProgressBar)findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
}

    public void enableButtons() {
        ((Button) findViewById(R.id.fast)).setEnabled(true);
        ((Button) findViewById(R.id.standar)).setEnabled(true);
        ((ProgressBar)findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
    }


}
