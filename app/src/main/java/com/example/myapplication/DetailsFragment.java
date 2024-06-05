package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailsFragment extends Fragment implements dishRecyclerViewInterface {

    private RecyclerView mealItemRV;
    public ArrayList<Dish> reservationMenuList = new ArrayList<>();
    private reservationMenuAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mealItemRV = view.findViewById(R.id.mealItemRV);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("managers").child(PublicData.currentRestaurantId).child("menu");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("managers").child(PublicData.currentRestaurantId).child("menu");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {// not for realtime updates
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dish data = snapshot.getValue(Dish.class);
                    reservationMenuList.add(data);
                }

                if (reservationMenuList.isEmpty()){
                    Toast.makeText(getContext(),"no menu items found ",Toast.LENGTH_LONG).show();
                }
                else {
                    mealItemRV.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new reservationMenuAdapter(requireContext(), reservationMenuList, DetailsFragment.this);
                    mealItemRV.setAdapter(adapter);
                }



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving data", databaseError.toException());
            }

        });




    }


    @Override
    public void onItemClicked(Dish dishObject) {
        // Handle item click
        Intent intent = new Intent(getContext(),PlateDetails.class);
        intent.putExtra("dishObject",dishObject);
        startActivity(intent);


    }
}
