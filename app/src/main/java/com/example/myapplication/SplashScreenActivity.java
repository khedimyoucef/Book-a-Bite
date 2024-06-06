package com.example.myapplication;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {


public static final String SharedPrefsFile = "SharedPrefsFile";
public static final String CLIENT_KEY = "isClient";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        /*
        // Set immersive mode
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

         */
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        setContentView(R.layout.activity_splash_screen);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //for testing the Recycler View
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



        /*
        //ManagerClass managerObject = new ManagerClass(username, email, phoneNumber, password, user.getUid(), restaurantName);
        ManagerClass managerObject1 = new ManagerClass("JohnDoe", "johndoe@example.com", "+1234567890", "password123", "uid1", "Restaurant A");
        ManagerClass managerObject2 = new ManagerClass("AliceSmith", "alicesmith@example.com", "+1987654321", "pass456", "uid2", "Restaurant B");
        ManagerClass managerObject3 = new ManagerClass("BobJohnson", "bjohnson@example.com", "+1122334455", "test123", "uid3", "Restaurant C");
        ManagerClass managerObject4 = new ManagerClass("EmilyDavis", "emilyd@example.com", "+1555666777", "password456", "uid4", "Restaurant D");
        ManagerClass managerObject5 = new ManagerClass("MichaelWilson", "mwilson@example.com", "+1444412345", "pass789", "uid5", "Restaurant E");
        ManagerClass managerObject6 = new ManagerClass("SarahBrown", "sarah@example.com", "+1888777666", "test456", "uid6", "Restaurant F");
        ManagerClass managerObject7 = new ManagerClass("DavidJones", "djones@example.com", "+1777345678", "password789", "uid7", "Restaurant G");
        ManagerClass managerObject8 = new ManagerClass("OliviaTaylor", "olivia@example.com", "+1999888777", "pass987", "uid8", "Restaurant H");
        ManagerClass managerObject9 = new ManagerClass("EthanClark", "ethan@example.com", "+1888555777", "test987", "uid9", "Restaurant I");



                mDatabase.child("managers").child("managerObject1").setValue(managerObject1);
mDatabase.child("managers").child("managerObject2").setValue(managerObject2);
mDatabase.child("managers").child("managerObject3").setValue(managerObject3);
mDatabase.child("managers").child("managerObject4").setValue(managerObject4);
mDatabase.child("managers").child("managerObject5").setValue(managerObject5);
mDatabase.child("managers").child("managerObject6").setValue(managerObject6);
mDatabase.child("managers").child("managerObject7").setValue(managerObject7);
mDatabase.child("managers").child("managerObject8").setValue(managerObject8);
mDatabase.child("managers").child("managerObject9").setValue(managerObject9);




        // Example instances
        ReviewClass review1 = new ReviewClass(1672512000, "user123", "Great service and delicious food!", 5);
        ReviewClass review2 = new ReviewClass(1672608400, "user456", "Average experience, food was okay.", 3);
        ReviewClass review3 = new ReviewClass(1672704800, "user789", "Terrible service, not coming back.", 1);
        ReviewClass review4 = new ReviewClass(1672801200, "user101", "Loved the ambiance and the food was great!", 4);
        ReviewClass review5 = new ReviewClass(1672897600, "user102", "Good place, but the service was slow.", 3);
        ReviewClass review6 = new ReviewClass(1672994000, "user103", "Amazing food, will definitely return!", 5);
        ReviewClass review7 = new ReviewClass(1673090400, "user104", "Food was undercooked and service was rude.", 2);
        ReviewClass review8 = new ReviewClass(1673186800, "user105", "Best dining experience ever!", 5);
        ReviewClass review9 = new ReviewClass(1673283200, "user106", "Nice place, but a bit too pricey.", 3);
        ReviewClass review10 = new ReviewClass(1673379600, "user107", "Friendly staff and great food.", 4);


        // Push each review directly to the database
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review1);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review2);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review3);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review4);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review5);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review6);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review7);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review8);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review9);
        mDatabase.child("managers").child("managerObject1").child("reviews").push().setValue(review10);

    /*


// Create some Dish objects
        Dish dish1 = new Dish("Pizza", "Main Course", "Delicious cheese pizza", 9.99, "url_to_image1");
        Dish dish2 = new Dish("Burger", "Main Course", "Juicy beef burger", 5.99, "url_to_image2");
        Dish dish3 = new Dish("Pasta", "Main Course", "Creamy Alfredo pasta", 7.99, "url_to_image3");
        Dish dish4 = new Dish("Salad", "Appetizer", "Fresh garden salad", 4.99, "url_to_image4");
        Dish dish5 = new Dish("Soup", "Appetizer", "Hot and sour soup", 3.99, "url_to_image5");
        Dish dish6 = new Dish("Steak", "Main Course", "Grilled ribeye steak", 15.99, "url_to_image6");
        Dish dish7 = new Dish("Ice Cream", "Dessert", "Vanilla ice cream with chocolate syrup", 2.99, "url_to_image7");
        Dish dish8 = new Dish("Apple Pie", "Dessert", "Classic apple pie with a flaky crust", 3.99, "url_to_image8");
        Dish dish9 = new Dish("Chicken Wings", "Appetizer", "Spicy buffalo wings", 6.99, "url_to_image9");
        Dish dish10 = new Dish("Fish Tacos", "Main Course", "Crispy fish tacos with fresh salsa", 8.99, "url_to_image10");
        Dish dish11 = new Dish("Cheesecake", "Dessert", "Rich and creamy cheesecake", 4.99, "url_to_image11");
        Dish dish12 = new Dish("Sandwich", "Main Course", "Turkey club sandwich", 7.49, "url_to_image12");
        Dish dish13 = new Dish("Pancakes", "Breakfast", "Fluffy pancakes with maple syrup", 5.49, "url_to_image13");
        Dish dish14 = new Dish("Omelette", "Breakfast", "Cheese and ham omelette", 6.49, "url_to_image14");
        Dish dish15 = new Dish("Coffee", "Beverage", "Freshly brewed coffee", 1.99, "url_to_image15");
        Dish dish16 = new Dish("Smoothie", "Beverage", "Strawberry banana smoothie", 3.49, "url_to_image16");


        */

        // Create some Dish objects and push them to Firebase
        mDatabase.child("managers").child("managerObject1").child("menu").push().setValue(new Dish("Pizza", "Main Course", "Delicious cheese pizza", 9.99, "url_to_image1"));
        mDatabase.child("managers").child("managerObject1").child("menu").push().setValue(new Dish("Burger", "Main Course", "Juicy beef burger", 5.99, "url_to_image2"));
        mDatabase.child("managers").child("managerObject1").child("menu").push().setValue(new Dish("Pasta", "Main Course", "Creamy Alfredo pasta", 7.99, "url_to_image3"));
        mDatabase.child("managers").child("managerObject1").child("menu").push().setValue(new Dish("Pizza", "Main Course", "Delicious cheese pizza", 9.99, "url_to_image1"));
        mDatabase.child("managers").child("managerObject1").child("menu").push().setValue(new Dish("Burger", "Main Course", "Juicy beef burger", 5.99, "url_to_image2"));
        mDatabase.child("managers").child("managerObject1").child("menu").push().setValue(new Dish("Pasta", "Main Course", "Creamy Alfredo pasta", 7.99, "url_to_image3"));


        //initializing the shared preferences getter and setter
        SharedPreferences prefs = getSharedPreferences(SharedPrefsFile, MODE_PRIVATE);//private is mandatory so the file is overwritten on writing
        SharedPreferences.Editor editor = prefs.edit();


        // Check if the app is being run for the first time
        boolean firstRun = prefs.getBoolean("isFirstRun", true);
        // Retrieve the isClient variable













        // Delay for 2 seconds before starting LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (firstRun) {
                    // App is being run for the first time implies no user is signed in

                    Intent intent = new Intent(SplashScreenActivity.this, SeparatorActivity.class);
                    startActivity(intent);
                    finish();

                    // Update SharedPreferences to indicate that the app has been launched
                    editor.putBoolean("isFirstRun", false);
                    editor.apply();
                } else {
                    // App has been launched before and the isClient has been set
                    // Perform regular initialization tasks here
                    boolean isClient = prefs.getBoolean(CLIENT_KEY, true); // 0 is the default value if "IS_CLIENT-KEY : isClient" doesn't exist
                    if (user != null) {
                        if (isClient) {// the user is a client
                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                            startActivity(intent); // Start LoginActivity
                            finish();
                        } else if (!(isClient)) {//the user is a manager

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("managers").child(user.getUid()).child("newUser");

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method will be called once with the value from the database.
                                    Boolean newUser = dataSnapshot.getValue(Boolean.class);
                                    if(newUser == null){
                                        Toast.makeText(SplashScreenActivity.this,"error",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                         // Convert Boolean to boolean
                                        if (!newUser) {
                                            Intent intent = new Intent(SplashScreenActivity.this, RestrauntMain.class);
                                            startActivity(intent); // Start LoginActivity
                                            finish();
                                        } else {
                                            Intent intent = new Intent(SplashScreenActivity.this, RestaurantCheckInActivity.class);
                                            startActivity(intent); // Start LoginActivity
                                            finish();

                                        }
                                    }
                                }


                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            });


                        }
                    } else {
                        // skipping separator activity and running LoginActivity directly
                        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                        startActivity(intent); // Start LoginActivity
                        finish(); // Finish SplashScreenActivity to prevent returning to it when pressing back button
                    }
                }



            }
        }, 1000); // 2000 milliseconds (2 seconds) delay



    }



}
