package com.example.bookabiteapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class mealsFragment extends Fragment {
    private Button bookNowbtn;
View view;
private ArrayList<menuItem> menuItemArrayList;
private String dishName;
private String dishDesc;
private int dishImg;

private RecyclerView recyclerView;

    public mealsFragment() {
        // Required empty public constructor
    }

    public static mealsFragment newInstance() {
        return new mealsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_meals, container, false);
        recyclerView = view.findViewById(R.id.mealsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        return view;

    }
    public void init(View view) {
        bookNowbtn = view.findViewById(R.id.bookNowbtn);
        bookNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DetailsFragment.class));
            }
        });
    }

}