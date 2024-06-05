package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Dish implements Parcelable {

    private String name;
    private String category;
    private String description;
    private double price;
    private String img;

    public Dish() {
        // Default constructor required for Firebase deserialization
    }

    public Dish(String name, String category, String description, double price) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public Dish(String name, String category, String description, double price, String img) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(description);
        dest.writeDouble(price);
        dest.writeString(img);
    }

    protected Dish(Parcel in) {
        name = in.readString();
        category = in.readString();
        description = in.readString();
        price = in.readDouble();
        img = in.readString();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };
}
