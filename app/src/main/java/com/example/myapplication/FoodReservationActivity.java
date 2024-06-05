package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FoodReservationActivity extends AppCompatActivity {


    private Button detailsButton,cardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_reservation);


        detailsButton = findViewById(R.id.detailsBtn);
        cardButton = findViewById(R.id.cardBtn);

        replaceFragment(new DetailsFragment());
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DetailsFragment());
            }
        });


        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CardFragment());
            }
        });
    }



    private synchronized void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Avoid performing fragment transactions if the state is saved
        if (!fragmentManager.isStateSaved()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.frameLayout, fragment);
            transaction.commit();
        }
    }
}