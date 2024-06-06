package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingViewHolder extends RecyclerView.ViewHolder {
    TextView ClientName;
    TextView numPeople;
    TextView startTime;
    TextView endTime;
    TextView bookedTable;


    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);
        ClientName=itemView.findViewById(R.id.ClientName);
        numPeople=itemView.findViewById(R.id.numPeople);
        startTime=itemView.findViewById(R.id.startTime);
        endTime=itemView.findViewById(R.id.endTime);
        bookedTable=itemView.findViewById(R.id.bookedTable);
    }
}
