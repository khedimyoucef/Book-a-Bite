package com.example.myapplication;

public class ClientClass {
   private String name,email,username,phoneNumber,password,userId;

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

