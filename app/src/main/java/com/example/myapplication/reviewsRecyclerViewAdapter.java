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

public class reviewsRecyclerViewAdapter extends RecyclerView.Adapter<reviewsRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ReviewClass> reviewsList ;

    private final RecyclerViewInterface recyclerViewInterface;

    public reviewsRecyclerViewAdapter(Context context, ArrayList<ReviewClass> reviewsList, RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.reviewsList = reviewsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }


    @NonNull
    @Override
    public reviewsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //here we have to inflate the layouts
        LayoutInflater inflater = LayoutInflater.from(context);
        //here we link the single view design
        View view = inflater.inflate(R.layout.review_item,parent,false);



        return new reviewsRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull reviewsRecyclerViewAdapter.MyViewHolder holder, int position) {
    //this one assigns the values to the views we created in the recycler_view_row layout file
        holder.assignedUser.setText(reviewsList.get(position).getAssignedUser());//reviewsList.get(position) returns a manager object
        holder.reviewComment.setText(reviewsList.get(position).getComment());
        //holder.reviewImage.setText(reviewsList.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        //here the length of the reviewsList must be returned
        return reviewsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //here the items from the reviewsList are binded to the recycler view list
        TextView assignedUser,dateAdded,reviewComment;
        int rating;
        ImageView reviewImage;
        public CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //here we initialize all the desired views in the cardView

            assignedUser = itemView.findViewById(R.id.assigned_user);
            reviewComment = itemView.findViewById(R.id.review_comment);
            //TODO: add the rating field and assign it here
           // rating = itemView.findViewById(R.id.dishPriceCv);
           // dateAdded = itemView.findViewById(R.id.date_added);



            //here is the cardWidget

            cardView = itemView.findViewById(R.id.review_container);

        }
    }

}
