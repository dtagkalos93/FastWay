package com.example.mitsos_laptop.trafficapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Mitsos on 11/3/2017.
 */

public class Register extends AppCompatActivity {

    private TextView errormessage;
    private Button signup;
    private ProgressBar bar;
    private NetConnection con;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View v) {
        con = new NetConnection();
        con.isNetworkAvailable(this);
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        errormessage = (TextView) findViewById(R.id.message);
        signup = (Button) findViewById(R.id.register);
        errormessage = (TextView) findViewById(R.id.message);
        if (username.getText().length() < 1) {

            errormessage.setText(getResources().getString(R.string.error_name));
            errormessage.setVisibility(View.VISIBLE);
        } else if (password.getText().length() < 1) {

            errormessage.setText(getResources().getString(R.string.error_password));
            errormessage.setVisibility(View.VISIBLE);
        } else {
            disablleButtons();
            NetConnection com = new NetConnection();
            com.newUser(username.getText().toString(), password.getText().toString(), this);
        }
    }
        public void disablleButtons(){

            signup.setEnabled(false);

        }

        public void enableButtons(){

            signup.setEnabled(true);

        }



}
