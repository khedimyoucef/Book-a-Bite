package com.example.bookabiteapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class DetailsFragment extends Fragment {
    public  View view;
    private RecyclerView mealItemRV;
    public ArrayList<mealItem> mealsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_details, container, false);
       mealsList=new ArrayList<>();
       mealItemRV.setLayoutManager(new LinearLayoutManager(getContext()));
       mealItemAdapter adapter=new mealItemAdapter(mealsList);
       mealItemRV.setAdapter(adapter);
       adapter.setOnItemClickListener(new mealItemAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {
               mealItem clickedMeal=mealsList.get(position);
               Intent intent=new Intent(getContext(),PlateDetails.class);
               intent.putExtra("MEAL_PRICE",clickedMeal.getDishPrice());
               startActivity(intent);

           }
       });
       return view;
    }
    public  void init(){
        mealItemRV=view.findViewById(R.id.mealsRV);
    }
}