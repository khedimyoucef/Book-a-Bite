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
import java.util.List;

public class fdRestaurantsRecyclerViewAdapter extends RecyclerView.Adapter<fdRestaurantsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ManagerClass> foundRestaurantsList ;

    private final RecyclerViewInterface recyclerViewInterface;

    public fdRestaurantsRecyclerViewAdapter(Context context, ArrayList<ManagerClass> foundRestaurantsList, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.foundRestaurantsList = foundRestaurantsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public fdRestaurantsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //here we have to inflate the layouts
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_item_cardview,parent,false);



        return new fdRestaurantsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull fdRestaurantsRecyclerViewAdapter.MyViewHolder holder, int position) {
    //this one assigns the values to the views we created in the recycler_view_row layout file
        holder.restaurantName.setText(foundRestaurantsList.get(position).getRestaurantName());//foundRestaurantsList.get(position) returns a manager object
        holder.rating.setText(foundRestaurantsList.get(position).getUserId());





        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerViewInterface.onItemClicked(foundRestaurantsList.get(position));//returns a manager object from the foundrestaurant list
            }
        });
    //TODO: add the rest of the recycler view row views here (ex: rating , street name )
    }

    @Override
    public int getItemCount() {
        //here the length of the foundRestaurantList must be returned
        return foundRestaurantsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //here the items from the foundRestaurantsList are binded to the recycler view list
        TextView restaurantName,rating,streetName;
        ImageView restaurantImage;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //here we initialize all the desired views in the cardView

            restaurantName = itemView.findViewById(R.id.text_title);
            rating = itemView.findViewById(R.id.text_rating);
            streetName = itemView.findViewById(R.id.text_subtitle);
            restaurantImage = itemView.findViewById(R.id.image_logo);

            //here is the cardWidget

            cardView = itemView.findViewById(R.id.main_container);

        }
    }
    //for the search feature

    public void setFilteredList(ArrayList<ManagerClass> filteredList){
        this.foundRestaurantsList = filteredList; // here we update the recycler view to only show the matching elements as the user types
        notifyDataSetChanged();
    }

}
