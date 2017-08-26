package com.example.mitsos_laptop.trafficapp;

/**
 * Created by Mitsos on 21/8/2017.
 */


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    public static Distance distance;
    public static Duration duration;
    public static String endAddress;
    public static LatLng endLocation;
    public static String startAddress;
    public static LatLng startLocation;

    public static List<LatLng> points;

    public void setDistance(Distance distance){
        this.distance=distance;
    }

    public  Distance getDistance(){
        return distance;
    }

    public void setDuration(Duration duration){
        this.duration=duration;
    }

    public  Duration getDuration(){
        return duration;
    }


    public void setEndAddress(String endAddress){
        this.endAddress=endAddress;
    }

    public  String getEndAddress(){
        return endAddress;
    }

    public void setEndLocation(LatLng endLocation ){
        this.endLocation=endLocation;
    }

    public  LatLng getEndLocation(){
        return endLocation;
    }

    public void setStartAddress(String startAddress){
        this.startAddress=startAddress;
    }

    public  String  getStartAddress(){
        return startAddress;
    }

    public void setStartLocation(LatLng startLocation){
        this.startLocation=startLocation;
    }

    public  LatLng getStartLocation(){
        return startLocation;
    }

    public void setPoints(List<LatLng> points){
        this.points=points;
    }

    public  List<LatLng> getPoints(){
        return points;
    }
}