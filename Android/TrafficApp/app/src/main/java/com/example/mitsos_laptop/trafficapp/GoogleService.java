package com.example.mitsos_laptop.trafficapp;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class GoogleService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 30000;
    public static String str_receiver = "com.example.mitsos_laptop.trafficapp.receiver";
    Intent intent;
    private boolean firstTime = false;
    private long tStart;
    private long tEnd;
    private NetConnection con;
    private String midpoint;
    private String destination;
    private double totalDistance = 0.0;
    private long totalTime = 0;

    private LocationData trackData;


    public GoogleService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Google Service", "on create");
        mTimer = new Timer();
        tStart = System.currentTimeMillis();
        trackData = new LocationData(this);
        //Log.e("initCord",trackData.end());
        con = new NetConnection();
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
        intent = new Intent(str_receiver);
        //fn_getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 0, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        newLocation(location);

                    }
                }

            }


            if (isGPSEnable) {
                location = null;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        newLocation(location);
                    }
                }
            }


        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("Google Service", "timer");

                    fn_getlocation();
                }
            });

        }
    }

    public void fn_update(Location location) {

        intent.putExtra("latutide", location.getLatitude() + "");
        intent.putExtra("longitude", location.getLongitude() + "");
        sendBroadcast(intent);
    }

    private void newLocation(Location location) {
        tEnd = System.currentTimeMillis();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        /*DecimalFormat format = new DecimalFormat("#0.######", symbols);
        latitude = Double.valueOf(format.format(latitude));
        longitude = Double.valueOf(format.format(longitude));*/
        String stopPoint = latitude + "," + longitude;
        Log.d("StopPoint","StopPoint "+stopPoint+ "TrackPoint"+ trackData.trackPoint());
        String[] from = trackData.end().split(",");
        double latitudeEnd = Double.parseDouble(from[0]);
        double longitudeEnd = Double.parseDouble(from[1]);
        Location locationEnd = new Location("endPoint");
        locationEnd.setLatitude(latitudeEnd);
        locationEnd.setLongitude(longitudeEnd);
        con.getDistanceOnRoad(latitude, longitude, latitudeEnd, longitudeEnd, this);
        String dis = trackData.getDistance();
        if (Double.parseDouble(dis) <= 0.1) {
            con.sendLoc(trackData.trackPoint(), stopPoint, TimeUnit.MILLISECONDS.toMinutes(tEnd - tStart) + "", this);
            mTimer.cancel();
            Intent dialogIntent = new Intent(this, TrackerFinish.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);

        } else {
            if (tEnd - tStart > 120000) {
                Log.d("MIDPOINT", "PointA" + trackData.trackPoint() + "PointB" + stopPoint);
                midpoint=trackData.trackPoint();
                trackData.tracking(stopPoint);
                con.sendLoc(midpoint, stopPoint, TimeUnit.MILLISECONDS.toMinutes(tEnd - tStart) + "", this);
                tStart = System.currentTimeMillis();


            }


        }


    }


}