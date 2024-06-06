package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder> {

    Context context;
    ArrayList<Order> myList ;

    private final orderInterface orderInterface;

    public orderAdapter(Context context, ArrayList<Order> myList, orderInterface orderInterface){
        this.context = context;
        this.myList = myList;
        this.orderInterface = orderInterface;
    }


    @NonNull
    @Override
    public orderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here we have to inflate the layouts
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_booking_layout,parent,false);



        return new orderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull orderAdapter.MyViewHolder holder, int position) {


        //this one assigns the values to the views we created in the recycler_view_row layout file
        holder.clientNameTv.setText(myList.get(position).getClientName());//myList.get(position) returns a manager object

        holder.numberOfPeopleTv.setText(String.valueOf(myList.get(position).getReservedSeats()));
        holder.startTimeTv.setText(String.valueOf(myList.get(position).getStartHour()+":"+myList.get(position).getStartMinute()));
        holder.startTimeTv.setText(String.valueOf(myList.get(position).getEndHour()+":"+myList.get(position).getEndMinute()));
        // Set the random number to the bookedTable TextView
        Random random = new Random();
        int randomNumber = random.nextInt(20) + 1;
        holder.bookedTable.setText(String.valueOf(randomNumber));
        //TODO : SET THE IMAGE VIEW PROPERLY


        /*
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderInterface.onItemClicked(myList.get(position));//returns a manager object from the foundrestaurant list
            }
        });


         */


        //TODO: add the rest of the recycler view row views here (ex: rating , street name )
    }

    @Override
    public int getItemCount() {
        //here the length of the foundRestaurantList must be returned
        return myList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //here the items from the cartMenuList are binded to the recycler view list
        TextView clientNameTv, numberOfPeopleTv, startTimeTv, endTimeTv,bookedTable;
        
        public CardView cardView3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //here we initialize all the desired views in the cardView3
            cardView3 = itemView.findViewById(R.id.container);
            clientNameTv = itemView.findViewById(R.id.ClientName);
            numberOfPeopleTv = itemView.findViewById(R.id.numPeople);
            startTimeTv = itemView.findViewById(R.id.startTime);
            endTimeTv = itemView.findViewById(R.id.endTime);
            bookedTable = itemView.findViewById(R.id.bookedTable);

            //here is the cardWidget


        }
        //for the search feature


    }
}
