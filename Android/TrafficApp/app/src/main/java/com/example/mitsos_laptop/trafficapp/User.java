package com.example.mitsos_laptop.trafficapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mitsos on 17/3/2017.
 */

public class User {
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    /**
     *  Context Needed to initialize SharedPreferences
     * @param c
     */
    public User(Context c){
        pref = c.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void logIn(String username){
        editor.putString("username",username);
        editor.commit();
    }

    public void logOut(){

        editor.clear();
        editor.commit();
    }

    public boolean loggedIn(Context ctx){

        return pref.contains("username");
    }




}
