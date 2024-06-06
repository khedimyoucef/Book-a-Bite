package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class restaurantProfileFragment extends Fragment {
      public View view;
    TextView editPhotoTv;
    TextView editLocationTv;
    TextView editMenuTv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view= inflater.inflate(R.layout.fragment_restaurant_profile, container, false);
        editPhotoTv=view.findViewById(R.id.editPhotoTv);
        editLocationTv=view.findViewById(R.id.editLocationTv);
        editMenuTv=view.findViewById(R.id.editMenuTv);
        editPhotoTv.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(),EditPhoto.class);
            startActivity(intent);
        });
        editLocationTv.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(),EditPhoto.class);
            startActivity(intent);
        });
        editMenuTv.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(),FillMenu.class);
            startActivity(intent);
        });



      return view;
    }
}