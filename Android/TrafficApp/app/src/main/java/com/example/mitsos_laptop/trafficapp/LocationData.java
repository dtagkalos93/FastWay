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


    public void LocationDataRemove(){
        editor.clear();
        editor.commit();
    }

    public void tracking(String trackPoint){
        editor.putString("track",trackPoint);
        editor.commit();
    }

    public void initCord (String begin,String end){
        editor.putString("begin",begin);
        editor.putString("end",end);
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

    public void total(String totalDis,int totalTime){
        editor.putString("totalDistance",totalDis);
        editor.putInt("totalTime",totalTime);
        editor.commit();

    }

    public String getTotalDistance(){
        return pref.getString("totalDistance","");
    }

    public int getTotalTime(){
        return pref.getInt("totalTime",0);
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


    public String trackPoint() {return  pref.getString("track","");}

}
