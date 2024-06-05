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

public class cartMenuAdapter extends RecyclerView.Adapter<cartMenuAdapter.MyViewHolder> {

    Context context;
    ArrayList<OrderItem> cartMenuList ;

    private final cartRecyclerViewInterface recyclerViewInterface;

    public cartMenuAdapter(Context context, ArrayList<OrderItem> cartMenuList, cartRecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.cartMenuList = cartMenuList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public cartMenuAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //here we have to inflate the layouts
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item,parent,false);



        return new cartMenuAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cartMenuAdapter.MyViewHolder holder, int position) {


        //this one assigns the values to the views we created in the recycler_view_row layout file
        holder.dishName.setText(cartMenuList.get(position).getName());//cartMenuList.get(position) returns a manager object

        holder.dishQuantity.setText(String.valueOf(cartMenuList.get(position).getQuantity()));
        //holder.dishDescription.setText(String.valueOf(cartMenuList.get(position).getDescription()));
        //TODO : SET THE IMAGE VIEW PROPERLY



        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerViewInterface.onItemClicked(cartMenuList.get(position));//returns a manager object from the foundrestaurant list
            }
        });



        //TODO: add the rest of the recycler view row views here (ex: rating , street name )
    }

    @Override
    public int getItemCount() {
        //here the length of the foundRestaurantList must be returned
        return cartMenuList.size();
    }
   

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            //here the items from the reservationMenuList are binded to the recycler view list
            TextView dishName,dishQuantity,dishCategory,dishDescription,removeBtn;
            ImageView dishImage;
            public CardView cardView2;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                //here we initialize all the desired views in the cardView2
                cardView2 = itemView.findViewById(R.id.container);
                dishName = itemView.findViewById(R.id.dishnameb);
                dishQuantity = itemView.findViewById(R.id.dishPrice);
                dishDescription = itemView.findViewById(R.id.dishdescb);
                dishImage = itemView.findViewById(R.id.dishImageb);
                removeBtn= itemView.findViewById(R.id.removeFromCart);

                //here is the cardWidget



            }
        }
    }
    //for the search feature


