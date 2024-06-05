package com.example.myapplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;
import android.content.SharedPreferences;

import java.util.ArrayList;

public abstract class PublicData {





    private static String loggedEmail;
    private static double longitude;
    private static double latitude;

    public static String currentRestaurantId;

    public static ArrayList<Dish> sharedMenuList;
    public static GoogleMap googleMap;//google map instance to be used throughout the app

    public static GoogleMap getGoogleMap(){
        return googleMap;
    }
    public static void setLongitude(double longitude) {
        PublicData.longitude = longitude;
    }

    public static void setLatitude(double latitude) {
        PublicData.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static double getLatitude() {
        return latitude;
    }


    public static String getLoggedEmail() {
        return loggedEmail;
    }

    public static void setLoggedEmail(String loggedEmail) {
        PublicData.loggedEmail = loggedEmail;
    }

    public static Order currentOrder;





}

