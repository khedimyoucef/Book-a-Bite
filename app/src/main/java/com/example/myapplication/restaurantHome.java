package com.example.myapplication;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class restaurantHome extends AppCompatActivity implements dishRecyclerViewInterface{
    private TextView restrauntNameTv;
    private ImageView restrauntPictureIv;
    private RecyclerView menuRecyclerView;
    public ArrayList<Dish> receivedDishesList = new ArrayList<>();
    public restaurantMenuRecyclerViewAdapter adapter;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_restaurant_home);

        restrauntPictureIv=findViewById(R.id.restrauntPictureIv);
        restrauntNameTv=findViewById(R.id.restrauntNameTv);
        menuRecyclerView=findViewById(R.id.menuRv);
        searchView = findViewById(R.id.searchView);


        // Initialize Firebase Authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

// Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();





        // Get a reference to your database
        DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference().child("managers");

// Attach a ValueEventListener to the reference
        databaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Loop through each child node in the collection
                    String key = snapshot.getKey(); // Get the key of the current child node

                    ManagerClass signedInManager = snapshot.getValue(ManagerClass.class); // Parse the data into your custom class

                    if(signedInManager.getUserId() == currentUser.getUid()) {

                        restrauntNameTv.setText(signedInManager.getUsername());
                        Picasso.get().load("https://picsum.photos/id/237/200/300").into(restrauntPictureIv);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // This method will be triggered in case of any error
                Log.e(TAG, "Error fetching data", databaseError.toException());
            }
        });








        

        //TODO : replace .child("managerObject1"); with the proper current signed in manager id
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("managers").child("managerObject1").child("menu");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dish data = snapshot.getValue(Dish.class);
                    receivedDishesList.add(data);
                }

               
                    /*
                    // Add spacing decorator
                    SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10); // 10px spacing
                    recyclerView.addItemDecoration(itemDecorator);
                    recyclerView.setPadding(0, 10, 0, 0); // Set padding to 0
                    recyclerView.setClipToPadding(false); // Adjust clipToPadding

                    */

                // Set the Layout Manager
                menuRecyclerView.setLayoutManager(new LinearLayoutManager(restaurantHome.this));
                adapter = new restaurantMenuRecyclerViewAdapter(restaurantHome.this, receivedDishesList, restaurantHome.this);
                menuRecyclerView.setAdapter(adapter);



                //for a horizontal recyclerview we can use :
                    /*
                    RecyclerView recyclerView = findViewById(R.id.horizontal_recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    */


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving data", databaseError.toException());
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        

    }



    private void filterList(String text) {
        ArrayList<Dish> filteredList = new ArrayList<>();
        for (Dish dishObject : receivedDishesList) {
            if (dishObject.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(dishObject);
            }
        }

        if (filteredList.isEmpty() && text.isEmpty()) {
            Toast.makeText(restaurantHome.this, "no matching items found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }
    @Override
    public void onItemClicked(Dish dishObject) {
        //handle click events
        //TODO: add the onclick handling
    }
}
