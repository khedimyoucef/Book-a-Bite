package com.example.myapplication;

import android.net.Uri;

import java.util.ArrayList;

public class ManagerClass {

    private String email,username,phoneNumber,password,userId,restaurantName;
    private Uri logo;


    private ArrayList<Dish> dishList;

    public ManagerClass() {
        // Default constructor required for Firebase deserialization
    }
    public ManagerClass(String username, String email,String phoneNumber, String password, String userId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;

    }
    // separate constructer with the logo uri added because the logo is not mandatory to set
    public ManagerClass(String username, String email,String phoneNumber, String password, String userId,Uri logo) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public ManagerClass(String username, String email, String phoneNumber, String password, String userId, Uri logo, String restaurantName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.logo = logo;
        this.restaurantName = restaurantName;
    }

    public ManagerClass(String username, String email,String phoneNumber, String password, String userId,String restaurantName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;

        this.restaurantName = restaurantName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumber(){return phoneNumber;}

    public Uri getLogo() {
        return logo;
    }

    public void setLogo(Uri logo) {
        this.logo = logo;
    }

    public ArrayList<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<Dish> dishList) {
        this.dishList = dishList;
    }
}
