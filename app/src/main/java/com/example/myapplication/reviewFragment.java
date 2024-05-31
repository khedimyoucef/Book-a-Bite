package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class reviewFragment extends Fragment implements RecyclerViewInterface {
    private View view;
    private ArrayList<ReviewClass> reviewsList;
    private RecyclerView reviewsRecyclerView;
    private reviewsRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review, container, false);

        // Initialize the RecyclerView
        reviewsRecyclerView = view.findViewById(R.id.recycler_view);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        reviewsList = new ArrayList<>();
        adapter = new reviewsRecyclerViewAdapter(requireContext(), reviewsList, this);
        reviewsRecyclerView.setAdapter(adapter);

        // Initial data for testing
        //reviewsList.add(new ReviewClass(1672512000, "user123", "Great service and delicious food!", 5));
        //Toast.makeText(getContext(), "Number of reviews retrieved: " + reviewsList.size(), Toast.LENGTH_SHORT).show();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve data from Firebase
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference()
                .child("managers").child("managerObject1").child("reviews");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewsList.clear(); // Clear the list before adding data to avoid duplication

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        ReviewClass data = snapshot.getValue(ReviewClass.class);
                        if (data != null) {
                            reviewsList.add(data);
                        } else {
                            Log.e(TAG, "ReviewClass is null for snapshot: " + snapshot.toString());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error parsing data snapshot: " + snapshot.toString(), e);
                    }
                }

                // Notify the adapter of data changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving data", databaseError.toException());
                Toast.makeText(getContext(), "Error loading reviews", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(ManagerClass managerObject) {
        // Do nothing here
    }
}
