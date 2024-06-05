package com.example.myapplication;

    public class OrderItem extends Dish{
    private int quantity;

    private double totalPrice;

    public OrderItem(){

    }

    public OrderItem(String name, String category, String description, double price, String img,int quantity){
        super(name,category,description,price,img);
        this.quantity = quantity;
    }


        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
