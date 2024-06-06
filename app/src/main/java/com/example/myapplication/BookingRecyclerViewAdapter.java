package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingViewHolder> {
    ArrayList<Booking> bookings;

    public BookingRecyclerViewAdapter(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout   .custom_booking_layout,null,false);
     BookingViewHolder viewHolder=new BookingViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking b=bookings.get(position);
        holder.ClientName.setText(b.getUserName());
        holder.numPeople.setText(b.getNumberOfPeople());
        holder.startTime.setText(b.getStartTime());
        holder.endTime.setText(b.getEndTime());
        holder.bookedTable.setText(b.getBookedTable());

    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public void removeBooking(int position) {
   bookings.remove(position);
        notifyItemRemoved(position);

    }
}
