package com.example.mitsos_laptop.trafficapp;

/**
 * Created by Mitsos on 21/8/2017.
 */

public class Duration {
    public String text;
    public int value;

    public Duration(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText(){
        return text;
    }
}