package com.example.mitsos_laptop.trafficapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mitsos on 6/4/2017.
 */

public class LocationData {

    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;

    public LocationData(Context c){
        pref = c.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void tracking(String track){
        editor.putString("track",track);
        editor.commit();
    }

    public void initCord (String begin,String end){
        editor.putString("begin",begin);
        editor.putString("end",end);
        editor.commit();
    }

    public void distCord(String init,String stop){
        editor.putString("init",init);
        editor.putString("stop",stop);
        editor.commit();
    }

    public void id(String id){
        editor.putString("id",id);
        editor.commit();
    }

    public void distance(String dis){
        editor.putString("distance",dis);
        editor.commit();
    }

    public void total(String totalDis,String totalTime){
        editor.putString("totalDistance",totalDis);
        editor.putString("totalTime",totalTime);
        editor.commit();

    }

    public String getTotalDistance(){
        return pref.getString("totalDistance","");
    }

    public String getTotalTime(){
        return pref.getString("totalTime","");
    }


    public String getDistance(){
        return pref.getString("distance","");
    }

    public String id(){
        return pref.getString("id","");
    }

    public String begin(){
        return pref.getString("begin","");
    }

    public String end(){
        return pref.getString("end","");
    }

    public String init(){
        return pref.getString("init","");
    }

    public String stop(){
        return pref.getString("stop","");
    }

    public String track() {return  pref.getString("track","");}

}
