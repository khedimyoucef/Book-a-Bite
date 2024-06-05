package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SummaryActivity extends AppCompatActivity {

    private TextView tableReservation,foodReservation,reservedSeats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);



        tableReservation = findViewById(R.id.tableReservation);
        foodReservation = findViewById(R.id.foodReservation);
        reservedSeats = findViewById(R.id.reservedSeats);



        if (PublicData.currentOrder != null) {
            String tableReservationText = PublicData.currentOrder.getStartHour() + ":" +
                    PublicData.currentOrder.getStartMinute() + ";" +
                    PublicData.currentOrder.getDay() + "/" +
                    PublicData.currentOrder.getMonth();
            tableReservation.setText(tableReservationText);

            int reservedSeatsValue = PublicData.currentOrder.getReservedSeats();
            reservedSeats.setText(String.valueOf(reservedSeatsValue));
        } else {
            // Handle the case when PublicData.currentOrder is null
            // You can display a message or take appropriate action
        }




        StringBuilder stringBuilder = new StringBuilder();

        for (OrderItem orderItem : PublicData.currentOrder.getOrderedItemsList()) {
            stringBuilder.append(orderItem.getQuantity())
                    .append(" ")
                    .append(orderItem.getName())
                    .append("\nTotal price : ")
                    .append(orderItem.getTotalPrice())
                    .append(" DA\n\n"); // Adding extra newline for spacing
        }

        foodReservation.setText(stringBuilder.toString());



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SummaryActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);


    }
}