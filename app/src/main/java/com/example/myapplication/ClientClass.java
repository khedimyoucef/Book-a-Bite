package com.example.myapplication;

import java.util.ArrayList;

public class ClientClass {
   private String name,email,username,phoneNumber,password,userId;

   private ArrayList<Order> bookings = new ArrayList<>();



    public void setName(String name) {
        this.name = name;
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

    public ArrayList<Order> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Order> bookings) {
        this.bookings = bookings;
    }

    public ClientClass(){

   }

    public ClientClass(String username, String email,String phoneNumber, String password, String userId) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
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


}

