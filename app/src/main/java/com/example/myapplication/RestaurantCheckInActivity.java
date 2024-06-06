
package com.example.myapplication;


import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;


public class RestaurantCheckInActivity extends AppCompatActivity {

    //for firestore
    StorageReference storageReference;
    //for the image picker
    private CircularProgressView circularProgressView ;
    private FloatingActionButton button;
    private ImageView imageView;

    //for the location detection
    private TextView AddressText;
    private Uri currentUri;

    private EditText restaurantNameEt,streetNameEt;
    private Button LocationButton,nextBtn;
    private String streetName,restaurantName,wilaya;

    private LocationRequest locationRequest;
    Spinner wilayasSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_restaurant_check_in);
        wilayasSp =findViewById(R.id.wilayasSp);
        nextBtn = findViewById(R.id.nextButtonTv);

        //the wilayas:
        String[] wilayas = {
                "1. Adrar",
                "2. Chlef",
                "3. Laghouat",
                "4. Oum El Bouaghi",
                "5. Batna",
                "6. Béjaïa",
                "7. Biskra",
                "8. Béchar",
                "9. Blida",
                "10. Bouira",
                "11. Tamanrasset",
                "12. Tébessa",
                "13. Tlemcen",
                "14. Tiaret",
                "15. Tizi Ouzou",
                "16. Alger",
                "17. Djelfa",
                "18. Jijel",
                "19. Sétif",
                "20. Saïda",
                "21. Skikda",
                "22. Sidi Bel Abbès",
                "23. Annaba",
                "24. Guelma",
                "25. Constantine",
                "26. Médéa",
                "27. Mostaganem",
                "28. M'Sila",
                "29. Mascara",
                "30. Ouargla",
                "31. Oran",
                "32. El Bayadh",
                "33. Illizi",
                "34. Bordj Bou Arréridj",
                "35. Boumerdès",
                "36. El Tarf",
                "37. Tindouf",
                "38. Tissemsilt",
                "39. El Oued",
                "40. Khenchela",
                "41. Souk Ahras",
                "42. Tipaza",
                "43. Mila",
                "44. Aïn Defla",
                "45. Naâma",
                "46. Aïn Témouchent",
                "47. Ghardaïa",
                "48. Relizane",
                "49. Timimoun",
                "50. Bordj Badji Mokhtar",
                "51. Ouled Djellal",
                "52. Béni Abbès",
                "53. In Salah",
                "54. In Guezzam",
                "55. Touggourt",
                "56. Djanet",
                "57. El M'Ghair",
                "58. El Meniaa"
        };
        //fill the spinner:
        ArrayList<String> arrayList=new ArrayList<>(Arrays.asList(wilayas));
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getBaseContext(),R.layout.spinner_text_style,arrayList);
        wilayasSp.setAdapter(arrayAdapter);
        /////// pick the wilaya of the restraunt:
        wilayasSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected wilaya when an item is selected
                wilaya =  parentView.getItemAtPosition(position).toString();
                // Do something with the selected wilaya
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing when nothing is selected
            }
        });






        //here we get the reference to upload images or files to our storage bucket in firestore

        storageReference = FirebaseStorage.getInstance().getReference();



        //TODO :  assign the proper restaurant name to the currently signed in user by matching the userId and passing it's restaurantName as a child node after the user clicks the locate button


        imageView = findViewById(R.id.restaurantPicture);
        button=findViewById(R.id.add_picture_icon);
        //imagePicker plugin made by github dhaval2404

        button.setOnClickListener(v -> {
            ImagePicker.with(RestaurantCheckInActivity.this)
                    .cropSquare()//Crop image(Optional), Check Customization for more option
                    .galleryOnly()//to limit the input to gallery images only (the logo is a premade image )
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
            //comment2


        });

        //detecting the current location (outputs longitude and latitude values)


        AddressText = findViewById(R.id.addressText);
        LocationButton = findViewById(R.id.detectLocationBtn);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        /*

        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(RestaurantCheckInActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permissions are granted, proceed to get the current location
                    getCurrentLocation();

                    Toast.makeText(RestaurantCheckInActivity.this,"Fetching location please wait !",Toast.LENGTH_LONG).show();
                    //disabling the button to avoid overloading requests
                    LocationButton.setEnabled(false);
                    // Create a Handler to delay enabling the button
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Enable the button after the delay
                            LocationButton.setEnabled(true);
                        }
                    }, 7000);



                    if (PublicData.getLongitude() == 0 && PublicData.getLatitude() == 0) {
                        //location is not set properly
                        Toast.makeText(RestaurantCheckInActivity.this,"location is not set properly : something went wrong try again",Toast.LENGTH_LONG).show();

                    } else{

                            Intent i = new Intent(RestaurantCheckInActivity.this, MapsActivity.class);
                            startActivity(i);
                            finish();

                    }
                } else {
                    // Permissions are not granted, request them

                    ActivityCompat.requestPermissions(RestaurantCheckInActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
            }
        });


         */



        streetNameEt = findViewById(R.id.streetNameEt);
        restaurantNameEt = findViewById(R.id.restaurantNameEt);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                streetName = streetNameEt.getText().toString();
                restaurantName = restaurantNameEt.getText().toString();
                if ( restaurantName.isEmpty() )
                    Toast.makeText(RestaurantCheckInActivity.this,"Provide the restaurant name first !", Toast.LENGTH_SHORT).show();
                else if (wilaya.isEmpty()) {
                    Toast.makeText(RestaurantCheckInActivity.this,"enter your wilaya first !", Toast.LENGTH_SHORT).show();
                }
                    else if (streetName.isEmpty()){
                    Toast.makeText(RestaurantCheckInActivity.this,"Provide the street name first !", Toast.LENGTH_SHORT).show();

                }else if(currentUri == null){
                    Toast.makeText(RestaurantCheckInActivity.this,"Please select an image first !", Toast.LENGTH_SHORT).show();
                }
                else {


                    // Get the FirebaseAuth instance
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();

// Check if a user is currently signed in
                    FirebaseUser currentUser = mAuth.getCurrentUser();


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("managers").child(currentUser.getUid());
                    databaseReference.child("wilaya").setValue(wilaya)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data successfully written
                                    Log.d(TAG, "Data written successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error occurred while writing data
                                    Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                                }
                            });
                    databaseReference.child("streetName").setValue(streetName)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data successfully written
                                    Log.d(TAG, "Data written successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error occurred while writing data
                                    Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                                }
                            });

                    databaseReference.child("restaurantName").setValue(restaurantName)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Data successfully written
                                    Log.d(TAG, "Data written successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error occurred while writing data
                                    Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                                }
                            });
                    Intent intent = new Intent(RestaurantCheckInActivity.this, TimeTbaleRestrauntInput.class);
                    startActivity(intent);
                    //finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
             currentUri = data.getData();
            imageView.setImageURI(currentUri); // Setting the image in the current activity temporarily

            // The logo photos are stored in a subfolder called Logos in my Firestore storage bucket
            storageReference.child("Logos").child("testName").putFile(currentUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // File uploaded successfully
                            Toast.makeText(RestaurantCheckInActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful uploads
                            Toast.makeText(RestaurantCheckInActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (resultCode == RESULT_CANCELED) {//to avoid the crash when the image picking is cancelled
            // User cancelled the picker
            Toast.makeText(this, "Image picking cancelled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    } */
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
                // Handle image picker result
                currentUri currentUri = null;
                if (data != null) {
                    currentUri = data.getData();
                    imageView.setImageURI(currentUri);
                    // TODO: Upload the image to the database
                }
                else{
                    Toast.makeText(RestaurantCheckInActivity.this,"image view request returned null data",Toast.LENGTH_LONG).show();
                }


        } else if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                // Handle location picker result
                getCurrentLocation();
            }
        } else if (requestCode == 100 && resultCode == RESULT_OK) {
            // Here we handle what happens when the request to the autocomplete returns a valid response
            Place place = Autocomplete.getPlaceFromIntent(data); // To get the location data
            // The place contains the name, longitude and latitude values, and the address
            if (googleMap != null) {
                googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName() + " at Address : " + place.getAddress()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 10));
            } else {
                Toast.makeText(RestaurantCheckInActivity.this, "Map is not loaded properly (null)", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Map is not loaded properly (null)", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) { // Handling the exception in case of request error
            // Initializing the status to show the exact error
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

*/



    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(RestaurantCheckInActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(RestaurantCheckInActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(RestaurantCheckInActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double currentLatitude = locationResult.getLocations().get(index).getLatitude();
                                        double currentLongitude = locationResult.getLocations().get(index).getLongitude();

                                        AddressText.setText("Latitude: " + currentLatitude + "\n" + "Longitude: " + currentLongitude);
                                        Log.d("currentLongitude :",String.valueOf(currentLongitude));
                                        Log.d("currentLatitude :",String.valueOf(currentLatitude));
                                        PublicData.setLatitude(currentLatitude);
                                        PublicData.setLongitude(currentLongitude);


                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(RestaurantCheckInActivity.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(RestaurantCheckInActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }


}


