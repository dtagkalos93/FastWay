package com.example.mitsos_laptop.trafficapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    private User user;
    private TextView errormessage;
    private Button login;
    private Button signup;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user=new User(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(user.loggedIn(this)){
            findViewById(R.id.loginlayout).setVisibility(View.VISIBLE);
            findViewById(R.id.nologinlayout).setVisibility(View.GONE);

        }
        else {
            findViewById(R.id.loginlayout).setVisibility(View.GONE);
            findViewById(R.id.nologinlayout).setVisibility(View.VISIBLE);
            login=(Button)findViewById(R.id.login);
            signup=(Button)findViewById(R.id.register);
            bar=(ProgressBar)findViewById(R.id.progress_bar);

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onLogin(View v){

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);

        errormessage=(TextView)findViewById(R.id.message);
        if(username.getText().length() < 1){

            errormessage.setText(getResources().getString(R.string.error_name));
            errormessage.setVisibility(View.VISIBLE);
        }
        else if(password.getText().length() < 1){

            errormessage.setText(getResources().getString(R.string.error_password));
            errormessage.setVisibility(View.VISIBLE);
        }
        else{
            NetConnection com = new NetConnection();
            disablleButtons();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            com.existUser(username.getText().toString(),password.getText().toString(),this);
        }



    }

    public void onRegister(View V){

        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    public void onCar(View V){
        Intent intent = new Intent(MainActivity.this, CarAction.class);
        startActivity(intent);
    }


    public void disablleButtons(){
        login.setEnabled(false);
        signup.setEnabled(false);
        bar.setVisibility(View.VISIBLE);
    }

    public void enableButtons(){
        login.setEnabled(true);
        signup.setEnabled(true);
        bar.setVisibility(View.GONE);
    }



}
