package com.example.myapplication;

import static android.content.ContentValues.TAG;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/* after add to favourites is clicked we assign the restaurant id to the favourites field in the cient collection





/**
* A simple {@link Fragment} subclass.
* Use the {@link favoriteFragment#newInstance} factory method to
* create an instance of this fragment.
*/
public class favoriteFragment extends Fragment implements RecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public favoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment favoriteFragment.
     */
    // TODO: Rename and change types and number of parameters




    private RecyclerView recyclerView;
    public ArrayList<ManagerClass> favoriteRestaurantsList = new ArrayList<>();

    public fdRestaurantsRecyclerViewAdapter adapter;
    public static favoriteFragment newInstance(String param1, String param2) {
        favoriteFragment fragment = new favoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        return view;
    }



     @Override
     public void onViewCreated(View view, Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         FirebaseAuth mAuth = FirebaseAuth.getInstance();
         FirebaseUser currentUser = mAuth.getCurrentUser();



         DatabaseReference favoritesRef = FirebaseDatabase.getInstance().getReference().child("clients").child(currentUser.getUid()).child("favorites");
         DatabaseReference managersRef = FirebaseDatabase.getInstance().getReference().child("managers");

        Set<String> favoritesSet = new HashSet<>();

            favoritesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {



                        favoritesSet.add(String.valueOf(snapshot1.getValue()));//the returned element is the restaurant id that is then used to match

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


         managersRef.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {

                 for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                     ManagerClass data = snapshot.getValue(ManagerClass.class);
                     if (favoritesSet.contains(data.getUserId())) {
                         favoriteRestaurantsList.add(data);
                     }

                 }

                 if (favoriteRestaurantsList.isEmpty()){
                     Toast.makeText(getContext(),"No favorites found",Toast.LENGTH_SHORT).show();
                 }

                 recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    /*
                    // Add spacing decorator
                    SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10); // 10px spacing
                    recyclerView.addItemDecoration(itemDecorator);
                    recyclerView.setPadding(0, 10, 0, 0); // Set padding to 0
                    recyclerView.setClipToPadding(false); // Adjust clipToPadding

                    */
                 adapter = new fdRestaurantsRecyclerViewAdapter(requireContext(), favoriteRestaurantsList, favoriteFragment.this);
                 recyclerView.setAdapter(adapter);



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
     }
     @Override
     public void onItemClicked(ManagerClass managerObject) {
         //do nothing here
     }
 }