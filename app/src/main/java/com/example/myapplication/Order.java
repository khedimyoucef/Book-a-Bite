package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {

    private String orderId;
    private String clientId;
    private String clientName;
    private int reservedSeats;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    private int day,month;
    private HashMap<String, Integer> orderedItems;
    private ArrayList<OrderItem> orderedItemsList;

    public Order(){

    }

    public Order(String clientId, String clientName, int reservedSeats, int startHour, int startMinute, int endHour, int endMinute,int day,int month) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.reservedSeats = reservedSeats;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.day = day;
        this.month = month;
        orderedItems = new HashMap<>();
        orderedItemsList = new ArrayList<>();
    }

    // Getters and setters for orderId
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // Getters and setters for clientId
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    // Getters and setters for clientName
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    // Getters and setters for reservedSeats
    public int getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(int reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    // Getters and setters for startHour
    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    // Getters and setters for startMinute
    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    // Getters and setters for endHour
    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    // Getters and setters for endMinute
    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    // Getters and setters for orderedItems
    public HashMap<String, Integer> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(HashMap<String, Integer> orderedItems) {
        this.orderedItems = orderedItems;
    }

    // Method to add an item to orderedItems
    public void addItem(String dishName, int quantity) {
        orderedItems.put(dishName, quantity);
    }

    // Method to remove an item from orderedItems
    public void removeItem(String dishName) {
        orderedItems.remove(dishName);
    }


    public void addItemToList(OrderItem orderItem){
        orderedItemsList.add(orderItem);
    }

    public void removeItemFromList(OrderItem orderItem){
        orderedItemsList.remove(orderItem);
    }

    public ArrayList<OrderItem> getOrderedItemsList() {
        return orderedItemsList;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
