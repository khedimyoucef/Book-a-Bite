package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class reservationMenuAdapter extends RecyclerView.Adapter<reservationMenuAdapter.MyViewHolder> {

    Context context;
    ArrayList<Dish> reservationMenuList ;

    private final dishRecyclerViewInterface recyclerViewInterface;

    public reservationMenuAdapter(Context context, ArrayList<Dish> reservationMenuList, dishRecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.reservationMenuList = reservationMenuList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public reservationMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here we have to inflate the layouts
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_item,parent,false);



        return new reservationMenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reservationMenuAdapter.MyViewHolder holder, int position) {


        //this one assigns the values to the views we created in the recycler_view_row layout file
        holder.dishName.setText(reservationMenuList.get(position).getName());//reservationMenuList.get(position) returns a manager object

        holder.dishPrice.setText(String.valueOf(reservationMenuList.get(position).getPrice()));
        holder.dishDescription.setText(String.valueOf(reservationMenuList.get(position).getDescription()));
        //TODO : SET THE IMAGE VIEW PROPERLY



        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerViewInterface.onItemClicked(reservationMenuList.get(position));//returns a manager object from the foundrestaurant list
            }
        });



        //TODO: add the rest of the recycler view row views here (ex: rating , street name )
    }

    @Override
    public int getItemCount() {
        //here the length of the foundRestaurantList must be returned
        return reservationMenuList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //here the items from the cartMenuList are binded to the recycler view list
        TextView dishName, dishPrice, dishCategory, dishDescription, addBtn;
        ImageView dishImage;
        public CardView cardView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //here we initialize all the desired views in the cardView2
            cardView2 = itemView.findViewById(R.id.container);
            dishName = itemView.findViewById(R.id.dishnameb);
            dishPrice = itemView.findViewById(R.id.dishPrice);
            dishDescription = itemView.findViewById(R.id.dishdescb);
            dishImage = itemView.findViewById(R.id.dishImageb);
            addBtn = itemView.findViewById(R.id.add);

            //here is the cardWidget


        }
        //for the search feature


    }
}
