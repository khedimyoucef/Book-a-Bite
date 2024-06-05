package com.example.myapplication;

public class OrderItem extends Dish{
    private int quantity;

    public OrderItem(){

    }

    public OrderItem(String name, String Category, String description, double price, String img ,int quantity){
        super(name,Category,description,price,img);
        this.quantity = quantity;

    }




}
