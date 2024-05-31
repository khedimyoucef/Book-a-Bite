package com.example.myapplication;

import android.net.Uri;

public class ReviewClass {
    private double dateAdded;//the date is store as a double to be able to push it and perform firebase sorting operations on it later to display the review by order (most recent appear first)
    private String assignedUser;//we get the username of the user who posted the review to assing it to the propper restaurant
    private String comment;
    private int rating;


    // Default constructor required for calls to DataSnapshot.getValue(ReviewClass.class) !!!!!!!!!!!!!!
    public ReviewClass() {
    }


    //private Uri review_image;
    public ReviewClass(double dateAdded, String assignedUser, String comment, int rating) {
        this.dateAdded = dateAdded;
        this.assignedUser = assignedUser;
        this.comment = comment;
        this.rating = rating;
    }
    /*

    public ReviewClass(double dateAdded, String assignedUser, String comment, int rating,Uri review_image) {
        this.dateAdded = dateAdded;
        this.assignedUser = assignedUser;
        this.comment = comment;
        this.rating = rating;
        this.review_image = review_image;
    }


     */
    public double getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(double dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    /*
    public Uri getReview_image() {
        return review_image;
    }

    public void setReview_image(Uri review_image) {
        this.review_image = review_image;
    }

     */
}

