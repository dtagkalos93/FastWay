package com.example.mitsos_laptop.trafficapp;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


public class Destination extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "Destination";

    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter fromAdapter;
    private PlaceAutocompleteAdapter toAdapter;

    private  AutoCompleteTextView from;
    private  AutoCompleteTextView to;

    private String fromStr;
    private String toStr;

    private LocationData data;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        data= new LocationData(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();


         from = (AutoCompleteTextView) findViewById(R.id.from);
         to = (AutoCompleteTextView) findViewById(R.id.to);
        from.setOnItemClickListener(fromAutocompleteClickListener);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        fromAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        from.setAdapter(fromAdapter);

        to.setOnItemClickListener(toAutocompleteClickListener);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        toAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        to.setAdapter(toAdapter);

    }

    private AdapterView.OnItemClickListener fromAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = fromAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.d(TAG, "Autocomplete item selected: " + primaryText);


            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(fromUpdatePlaceDetailsCallback);



            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> fromUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            fromStr=place.getLatLng().toString();


            // Format details of the place for display and show it in a TextView.
            /*mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));*/

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                //mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                //mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                //mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };


    private AdapterView.OnItemClickListener toAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = toAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.d(TAG, "Autocomplete item selected: " + primaryText);


            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(toUpdatePlaceDetailsCallback);



            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> toUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);
            toStr=place.getLatLng().toString();


            // Format details of the place for display and show it in a TextView.
            /*mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));*/

            // Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                //mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                //mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                //mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }

            Log.i(TAG, "Place details received: " + toStr);

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }


    public void direction(View view){
        Log.d(TAG, "Here i am " + toStr);
        Log.d(TAG, "Here i am 2 " + fromStr);

        if(fromStr!=null && toStr!=null) {
            String[] fromPart=fromStr.split("\\(");
            fromPart=fromPart[1].split("\\)");
            fromStr=fromPart[0];
            String[] toPart=toStr.split("\\(");
            toPart=toPart[1].split("\\)");
            toStr=toPart[0];

            data.initCord(fromStr,toStr);

            Intent intent = new Intent(Destination.this, Map.class);
            startActivity(intent);
            finish();


        }
    }
}
