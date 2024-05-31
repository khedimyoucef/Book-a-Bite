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

public class restaurantMenuRecyclerViewAdapter extends RecyclerView.Adapter<restaurantMenuRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Dish> receivedDishesList ;

    private final dishRecyclerViewInterface recyclerViewInterface;

    public restaurantMenuRecyclerViewAdapter(Context context, ArrayList<Dish> receivedDishesList, dishRecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.receivedDishesList = receivedDishesList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public restaurantMenuRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here we have to inflate the layouts
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dish_layout,parent,false);



        return new restaurantMenuRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull restaurantMenuRecyclerViewAdapter.MyViewHolder holder, int position) {
        //this one assigns the values to the views we created in the recycler_view_row layout file
        holder.dishName.setText(receivedDishesList.get(position).getName());//receivedDishesList.get(position) returns a manager object
        holder.dishCategory.setText(receivedDishesList.get(position).getCategory());
        holder.dishPrice.setText(String.valueOf(receivedDishesList.get(position).getPrice()));

        //TODO : SET THE IMAGE VIEW PROPERLY



        holder.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerViewInterface.onItemClicked(receivedDishesList.get(position));//returns a manager object from the foundrestaurant list
            }
        });
        //TODO: add the rest of the recycler view row views here (ex: rating , street name )
    }

    @Override
    public int getItemCount() {
        //here the length of the foundRestaurantList must be returned
        return receivedDishesList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //here the items from the receivedDishesList are binded to the recycler view list
        TextView dishName,dishPrice,dishCategory;
        ImageView dishImage;
        public CardView cardView1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //here we initialize all the desired views in the cardView1

            dishName = itemView.findViewById(R.id.dishNameCv);
            dishPrice = itemView.findViewById(R.id.dishPriceCv);
            dishCategory = itemView.findViewById(R.id.dishCategoryCv);
            dishImage = itemView.findViewById(R.id.dishImage);

            //here is the cardWidget

            cardView1 = itemView.findViewById(R.id.main_container);

        }
    }
    //for the search feature

    public void setFilteredList(ArrayList<Dish> filteredList){
        this.receivedDishesList = filteredList; // here we update the recycler view to only show the matching elements as the user types
        notifyDataSetChanged();
    }

}
