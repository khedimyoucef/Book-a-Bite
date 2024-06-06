package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestaurantActivity extends AppCompatActivity {
    Button mealsFragmentbtn,reviewFragmentbtn;
    Button addToFavorite;
    TextView restaurantName ;

    private String restaurantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_reservation);
        mealsFragmentbtn = findViewById(R.id.mealsbtn);
        reviewFragmentbtn = findViewById(R.id.reviewbtn);
        restaurantName = findViewById(R.id.restaurant_name);
        addToFavorite = findViewById(R.id.addToFavortie);



        restaurantName.setText(getIntent().getStringExtra("restaurantName"));

        restaurantID = getIntent().getStringExtra("restaurantID");

        //by default the when the activity launches the reviewFragment is opened first
        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new reviewFragment()).commit();
        //or

        replaceFragment(new reviewFragment());
        //TODO: add the appropriate checks for the fragment replacment
        mealsFragmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            
            public void onClick(View view) {
                Fragment mealsFragment = new mealsFragment();
                // Create a bundle to pass the string
                Bundle bundle = new Bundle();
                bundle.putString("restaurantIdKey", restaurantID);

                // Set the arguments on the fragment
                mealsFragment.setArguments(bundle);
                replaceFragment(mealsFragment);

            }
        });
        reviewFragmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new reviewFragment());

            }
        });

        // Get a reference to your database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseRef.child("clients")
                        .child(currentUser.getUid())
                        .child("favorites")
                        .push()
                        .setValue(restaurantID)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RestaurantActivity.this,"added to favorites",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}