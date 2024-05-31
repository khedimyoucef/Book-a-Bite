package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.health.connect.datatypes.ActiveCaloriesBurnedRecord;
import android.health.connect.datatypes.InstantRecord;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    public GoogleMap googleMap; // Declare a class-level variable to hold the GoogleMap object
    private AutocompleteSupportFragment autocompleteFragment;
     Marker marker;
    EditText locationSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_maps_view);










        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);

        mapFragment.getMapAsync(this);

        /*
        try {
            // Initialize Places API with your API key
             Places.initialize(getApplicationContext(), "AIzaSyAu0nAy4Rrbj2FSlnt2a3TrjNS63ZYtNQU");
            Places.initialize(getApplicationContext(), "AIzaSyDt4xfB1gWSfKHTcxcW_NM7oHU0ZLqyRDs");

            // Create a PlacesClient
            PlacesClient placesClient = Places.createClient(MapsActivity.this);

            // Your code to use the PlacesClient goes here
            // For example, you can use placesClient to fetch places data

        } catch (Exception e) {
            // Handle any exceptions that occur during initialization or creation of PlacesClient
            // Print the error message to Logcat or display it to the user
            Log.e("PlacesAPI", "Error initializing Places API: " + e.getMessage());
            Toast.makeText(MapsActivity.this,"Error initializing places api key",Toast.LENGTH_LONG).show();
            // You can also display the error message to the user using a Toast or AlertDialog
            // Toast.makeText(getApplicationContext(), "Error initializing Places API: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        locationSearchBar =  findViewById(R.id.locationSearchBar);
        locationSearchBar.setFocusable(false);//when the search bar is not clicked yet it's unfocused


        locationSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                //building an intent to display the autocomplete places as an array list
                Intent intent1 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(MapsActivity.this);
                startActivityForResult(intent1,153);
            }


        });

        */


        /*
        // Initialize the AutocompleteSupportFragment.
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);


        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                    if (marker != null) {//if there's a marker placed we remove it
                        marker.remove();
                    }else {


                        // Add marker and move camera to the selected place
                       marker =  googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName() + " at Address : " + place.getAddress()));
                       googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
                       googleMap.animateCamera(CameraUpdateFactory.zoomTo(12),2000,null);

                    }
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(MapsActivity.this,"An error occured while searching for the location",Toast.LENGTH_SHORT).show();
            }
        });






        */

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        // LatLng location = new LatLng(35.21130191525293, -0.632610142189089);//the position where to launch the map using a dropped marker
        LatLng location = new LatLng(PublicData.getLatitude(),PublicData.getLongitude());
        marker = googleMap.addMarker(new MarkerOptions().position(location).title("Your current location"));
        new Handler().postDelayed(new Runnable() {//waiting for the map to fully load
            @Override
            public void run() {
                // Code to be executed after the delay
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,5));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);
            }
        }, 2000); // Delay for 1 second (1000 milliseconds)


    }

    /*
    private void performNearbyRestaurantSearch(LatLng location) {
        // Define the radius for the nearby search (in meters)
        int radius = 5000; // 5 kilometers

        // Construct a request for nearby restaurants
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationBias(RectangularBounds.newInstance(location, location))
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setQuery("restaurant")
                .build();

        // Get the PlacesClient
        PlacesClient placesClient = Places.createClient(this);

        // Perform the nearby search asynchronously
        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            // Handle the successful response
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                LatLng placeLatLng = prediction.getPlace().getLatLng();
                if (placeLatLng != null) {
                    // Add a marker for each nearby restaurant
                    googleMap.addMarker(new MarkerOptions()
                            .position(placeLatLng)
                            .title(prediction.getPrimaryText(null).toString()));
                }
            }
        }).addOnFailureListener((exception) -> {
            // Handle the failure
            Log.e(TAG, "Nearby search failed: " + exception.getMessage());
        });
    }



     */
    //handling the autocomplete support fragment



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 153 && resultCode == RESULT_OK) {
            // Handling valid response from autocomplete
            Place place = Autocomplete.getPlaceFromIntent(data);
            Toast.makeText(MapsActivity.this,"name :" + place.getName(),Toast.LENGTH_LONG).show();
            locationSearchBar.setText(place.getAddress());


            if (googleMap != null) {
                // Add marker and move camera to the selected place
                googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName() + " at Address : " + place.getAddress()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
            } else {
                // Display toast if GoogleMap object is null
                Toast.makeText(MapsActivity.this, "Map is not loaded properly (null)", Toast.LENGTH_LONG).show();
            }


        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Handling error in autocomplete request
            Status status = Autocomplete.getStatusFromIntent(data);
            if (status != null) {
                // Display error message
                Toast.makeText(MapsActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }




     //this is the old way of handling the back button click not suitable for gestures as it cannot differentiate between a gesture in the app or going back
    @Override
    public void onBackPressed() {
        // Show a confirmation dialog
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to go back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Yes, so finish the activity and go back
                        //we reset the location lat and long values
                        // it is set to 0 to indicate that it's not set properly
                        PublicData.setLongitude(0);
                        PublicData.setLatitude(0);
                        Intent intent = new Intent(MapsActivity.this, RestaurantCheckInActivity.class);
                        startActivity(intent);
                        // Finish the current activity (optional)
                        finish(); // by finishing this activity and freeing it from the call stack we assert that the geolocation request is initialized again (in case of an error for example )
                    }
                })
                .setNegativeButton("No", null) // Do nothing if user clicks No
                .show();


        // super.onBackPressed(); // If you want to keep default back behavior

    }

        public void goToMenu(View v) {
            // Handle button click event
            Intent intent = new Intent(MapsActivity.this, FillMenu.class);
            startActivity(intent);
            finish();
        }

}

