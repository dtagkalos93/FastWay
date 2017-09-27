package com.example.mitsos_laptop.trafficapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class FastDestination extends AppCompatActivity {
    Destinations destination;

    private Spinner spinnertime;
    private Spinner spinnerday;
    private Spinner spinnerdestination;
    private NetConnection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_destination);

        spinnertime = (Spinner) findViewById(R.id.time_spinner);
        spinnerday = (Spinner) findViewById(R.id.day_spinner);
        spinnerdestination = (Spinner) findViewById(R.id.destination_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(this,
                R.array.day_array, android.R.layout.simple_spinner_item);

        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnertime.setAdapter(adapter);
        spinnerday.setAdapter(adapterDay);


        destination = new Destinations();
        Log.d("Fast", destination.getDest().toString());


        ArrayAdapter<String> adapterDest =
                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, destination.getDest());
        adapterDest.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);

        spinnerdestination.setAdapter(adapterDest);


    }

    public void onfind(View view) {
        con = new NetConnection();
        con.isNetworkAvailable(this);
        disabeButtons();
        String day=spinnerday.getSelectedItem().toString().split("\\(")[0];

        String time=spinnertime.getSelectedItem().toString().split("\\(")[0];
        String dest=spinnerdestination.getSelectedItem().toString().split("\\.")[0];
        if(day.equals("Days of the Week"))
            day="Daily";
        con.getDirections(time,day,dest,this);


    }

    private void disabeButtons() {
        ((Spinner) findViewById(R.id.destination_spinner)).setEnabled(false);
        ((Spinner) findViewById(R.id.day_spinner)).setEnabled(false);
        ((Spinner) findViewById(R.id.time_spinner)).setEnabled(false);
        ((Button) findViewById(R.id.find)).setEnabled(false);
        ((ProgressBar)findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
    }

    public void enableButtons() {
        ((Spinner) findViewById(R.id.destination_spinner)).setEnabled(true);
        ((Spinner) findViewById(R.id.day_spinner)).setEnabled(true);
        ((Spinner) findViewById(R.id.time_spinner)).setEnabled(true);
        ((Button) findViewById(R.id.find)).setEnabled(true);
        ((ProgressBar)findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
    }

}
