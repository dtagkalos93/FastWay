package com.example.mitsos_laptop.trafficapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mitsos on 8/8/2017.
 */

public class Destinations  {

    private static ArrayList<String> dest;
    private static JSONObject directions;
    private static ArrayList<String> to;

/*
    public void From(ArrayList<String> fromList,Context ctx){
        from=new ArrayList<>();


        for (int i=0;i<fromList.size();i++){

            dest.add(fromList.get(i));
            Log.d("Destinations",from.get(i));
        }
    }

    public void To(ArrayList<String> toList){
        to=new ArrayList<>();
        for (int i=0;i<toList.size();i++){
            dest.add(toList.get(i));
            Log.d("Destinations",to.get(i));
        }
    }
*/
    public void Directions(JSONObject dir){
        directions=dir;
    }

    public void Destinations(ArrayList<String> destinations){
        dest=new ArrayList<>();
        for (int i=0;i<destinations.size();i++){
            dest.add(destinations.get(i));
            Log.d("Destinations",dest.get(i));
        }
    }

    public ArrayList<String> getDest() {
        return dest;
    }
    public JSONObject getDirections() {
        return directions;
    }
    public ArrayList<String> getTo() {
        return to;
    }
}
