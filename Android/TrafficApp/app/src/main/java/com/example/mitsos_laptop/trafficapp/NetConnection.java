package com.example.mitsos_laptop.trafficapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Mitsos on 11/3/2017.
 */

public class NetConnection {
    //protected static final String serverIP = "http://192.168.1.3:8080/TrafficServer/";
    //protected static final String serverIP = "http://10.0.2.2:8080/TrafficServer/";
    protected static final String serverIP = "http://2.86.79.216:60000/TrafficServer/";

    private LocationData data;

    private Destinations destination;
    private ArrayList<String> dest=new ArrayList<String>();
    private ArrayList<String> fromList=new ArrayList<String>();
    private ArrayList<String> toList=new ArrayList<String>();

    public void existUser(final String username, String password, final Context ctx){
        String url=serverIP + "login/dologin?username=" + username +"&password="+password;
        Log.d("Send mesage",url);

        final ProgressBar progress = (ProgressBar) ((Activity)ctx).findViewById(R.id.progress_bar);
        final TextView errormsg = (TextView) ((Activity)ctx).findViewById(R.id.message);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        try {
                            String result =response.getString("login");
                            Log.d("result", result);


                            if(result.equals("true")){
                                User usr=new User(ctx);
                                usr.logIn(username);
                                Toast.makeText(ctx, ctx.getResources().getString(R.string.loggedin), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(((Activity)ctx), MainActivity.class);
                                ((Activity)ctx).finish();
                                ctx.startActivity(intent);

                            }
                            else{


                                ((MainActivity)ctx).enableButtons();
                                progress.setVisibility(View.GONE);
                                errormsg.setText(ctx.getResources().getString(R.string.invalidlogin));
                                errormsg.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);

    }


    public void newUser(final String username, String password, final Context ctx){
        String url=serverIP + "register/doregister?username=" + username +"&password="+password;
        Log.d("Send mesage",url);

        final ProgressBar progress = (ProgressBar) ((Activity)ctx).findViewById(R.id.progress_bar);
        final TextView errormsg = (TextView) ((Activity)ctx).findViewById(R.id.message);
        progress.setVisibility(View.VISIBLE);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        try {
                            String result =response.getString("register");
                            Log.d("result", result);
                            if(result.equals("true")){
                                User usr=new User(ctx);
                                usr.logIn(username);
                                Toast.makeText(ctx, ctx.getResources().getString(R.string.registered), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(((Activity)ctx), MainActivity.class);
                                ((Activity)ctx).finish();
                                ctx.startActivity(intent);

                            }
                            else{
                                ((Register)ctx).enableButtons();
                                progress.setVisibility(View.GONE);
                                errormsg.setText(ctx.getResources().getString(R.string.userExist));
                                errormsg.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);

    }

    public void sendLoc(final String startPoint, String endPoint,String ourtime, final Context ctx){
        data=new LocationData(ctx);
        Log.d("Send Location:",startPoint+" endPoint:" + endPoint);
        String url=serverIP+"car?startpos="+startPoint+"&finalpos="+endPoint+"&ourtime="+ourtime+"&id="+data.id() ;
        Log.d("Send mesage",url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        try {
                            String result =response.getString("login");
                            Log.d("result", result);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);

    }

    public void sendDestination(final String startPoint, final String endPoint, final Context ctx){

        data= new LocationData(ctx);
        String url=serverIP+"destination?startpos="+startPoint+"&finalpos="+endPoint ;
        Log.d("Send mesage",url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        try {
                            String result =response.getString("Destination");
                            Log.d("result", result);



                            data.id(result);
                            Intent intent = new Intent(((Activity)ctx),StatTracking.class);
                            ((Activity)ctx).finish();
                            ctx.startActivity(intent);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);

    }




    public void getDistanceOnRoad(double latitude, double longitude,
                                     double prelatitute, double prelongitude,Context ctx) {

        data=new LocationData(ctx);
        String startPoint=longitude+","+latitude;
        String endpoint=prelatitute+","+prelongitude;
        String url=serverIP+"distance?startpos="+startPoint+"&finalpos="+endpoint ;
        Log.d("Send mesage",url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        try {
                            String result =response.getString("distance");
                            Log.d("result", result);

                            data.distance(result);






                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);
    }

    public void findDestination(final Context ctx) {


        final String url=serverIP+"alldestination" ;
        Log.d("Send mesage",url);

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {
                        for(int i=1;i<=response.length();i++){
                            try {
                                Log.d(i+"",response.get(i+"").toString());
                                JSONArray dest1=response.getJSONArray(i+"");
                                JSONObject dest2=dest1.getJSONObject(0);
                                fromList.add(dest2.getString("from"));
                                toList.add(dest2.getString("to"));
                                dest.add(i+". From: "+ fromList.get(i-1)+ "\nTo: "+ toList.get(i-1));
                                Log.d("FindDestination",dest.get(i-1));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        destination=new Destinations();
                        destination.Destinations(dest);
                       /* destination.From(fromList,ctx);
                        destination.To(toList);*/


                        Intent intent = new Intent(((Activity)ctx),FastDestination.class);
                        ((Activity)ctx).finish();
                        ctx.startActivity(intent);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);
    }


    public void getDirections(String time, String day, String id, final Context ctx) {


        //String url=serverIP+"FastestRoute?time="+time+"&day="+day+"&id="+id;
        String url=serverIP+"FastestRoute?time=Afrenoon&day="+day+"&id="+id;
        Log.d("Send mesage",url);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject response) {


                        destination=new Destinations();
                        destination.Directions(response);

                        Intent intent = new Intent(((Activity)ctx),FastestWayMap.class);
                        ((Activity)ctx).finish();
                        ctx.startActivity(intent);

                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("Volley", "Error");
                            }
                        });

        RequestQueue rQueue = Volley.newRequestQueue(ctx);
        rQueue.add(jsObjRequest);
    }



}
