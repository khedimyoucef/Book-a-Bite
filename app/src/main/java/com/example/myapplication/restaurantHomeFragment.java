package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class restaurantHomeFragment extends Fragment implements dishRecyclerViewInterface {
    private static final String TAG = "restaurantHomeFragment";
    private TextView restrauntNameTv;
    private ImageView restrauntPictureIv;
    private RecyclerView menuRecyclerView;
    private ArrayList<Dish> receivedDishesList = new ArrayList<>();
    private restaurantMenuRecyclerViewAdapter adapter;
    private SearchView searchView;
    private View view;

    public restaurantHomeFragment() {
    }

    public static restaurantHomeFragment newInstance() {
        return new restaurantHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        restrauntPictureIv = view.findViewById(R.id.restrauntPictureIv);
        restrauntNameTv = view.findViewById(R.id.restrauntNameTv);
        menuRecyclerView = view.findViewById(R.id.menuRv);
        searchView = view.findViewById(R.id.searchView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: View is ready");


        // Initialize Firebase Authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Log.e(TAG, "onViewCreated: User not logged in");
            return;
        }

        Log.d(TAG, "onViewCreated: User logged in with UID: " + currentUser.getUid());

        // Get a reference to your database
        DatabaseReference databaseRef1 = FirebaseDatabase.getInstance().getReference().child("managers");

        // Attach a ValueEventListener to the reference
        databaseRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Data snapshot received");
                // This method is called once with the initial value and again whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Loop through each child node in the collection
                    String key = snapshot.getKey(); // Get the key of the current child node

                    ManagerClass signedInManager = snapshot.getValue(ManagerClass.class); // Parse the data into your custom class

                    if (signedInManager != null && signedInManager.getUserId().equals(currentUser.getUid())) {
                        restrauntNameTv.setText(signedInManager.getUsername());
                        Picasso.get().load("https://picsum.photos/id/237/200/300").into(restrauntPictureIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // This method will be triggered in case of any error
                Log.e(TAG, "Error fetching data", databaseError.toException());
            }
        });

        //TODO : replace .child("managerObject1"); with the proper current signed in manager id
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("managers").child(currentUser.getUid()).child("menu");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: Menu data snapshot received");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dish data = snapshot.getValue(Dish.class);
                    receivedDishesList.add(data);
                }

                // Set the Layout Manager
                menuRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new restaurantMenuRecyclerViewAdapter(requireContext(), receivedDishesList, restaurantHomeFragment.this);
                menuRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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
            Toast.makeText(getActivity(), "No matching items found", Toast.LENGTH_SHORT).show();
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
