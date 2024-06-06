package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class restaurantBookingFragment extends Fragment implements orderInterface{

    private ArrayList<Order> ordersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private orderAdapter adapter;

    private orderInterface orderInterface;

    public restaurantBookingFragment() {
        // Required empty public constructor
    }

    public static restaurantBookingFragment newInstance() {
        return new restaurantBookingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_booking, container, false);
        recyclerView = view.findViewById(R.id.bookingRv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Order order1 = new Order("client1", "John Doe", 4, 12, 0, 13, 0, 6, 6);
        Order order2 = new Order("client2", "Jane Smith", 3, 10, 30, 11, 30, 15, 7);
        Order order3 = new Order("client3", "Alice Johnson", 2, 15, 0, 16, 0, 22, 8);
        Order order4 = new Order("client4", "Bob Brown", 5, 17, 30, 18, 30, 10, 9);
        Order order5 = new Order("client5", "Emily Davis", 3, 14, 45, 15, 45, 5, 10);
        Order order6 = new Order("client6", "Michael Wilson", 6, 19, 0, 20, 0, 18, 11);
        Order order7 = new Order("client7", "Sarah Martinez", 4, 11, 15, 12, 15, 30, 12);



        DatabaseReference clientsRef = FirebaseDatabase.getInstance().getReference().child("clients");

        clientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Order> ordersList = new ArrayList<>();
                for (DataSnapshot clientSnapshot : dataSnapshot.getChildren()) {
                    String clientId = clientSnapshot.getKey();
                    // Get reference to the orders node under this client
                    DatabaseReference ordersRef = clientSnapshot.child("bookings").getRef();

                    // Iterate over the orders under this client
                    ordersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                                String orderId = orderSnapshot.getKey();
                                String orderJson = orderSnapshot.getValue(String.class);
                                Order order = convertJsonToOrder(orderJson);
                                ordersList.add(order1);
                                ordersList.add(order2);
                                ordersList.add(order3);
                                ordersList.add(order4);
                                ordersList.add(order5);
                                ordersList.add(order6);
                                ordersList.add(order7);

                                ordersList.add(order);
                                // Do something with the order object
                            }

                            // Set the adapter after all orders are retrieved
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            adapter = new orderAdapter(requireContext(), ordersList, restaurantBookingFragment.this);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle potential errors
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
            }
        });

        // Initialize views and set up any necessary event listeners
    }

    private Order convertJsonToOrder(String orderJson) {
        // Use Gson library or any other JSON parsing library to convert JSON string to Order object
        Gson gson = new Gson();
        return gson.fromJson(orderJson, Order.class);
    }


    @Override
    public void onItemClicked(Order order) {

    }






































/*


    public  static View view;
    private TextView restrauntNameTv;
    private ImageView restrauntPictureIv;
    private RecyclerView bookingRv;
    private GridLayout gridLayout;
    private int numberOfTables;
    private boolean[] tableAvailability;
    public List<Booking> bookingList;
    public BookingRecyclerViewAdapter adapter;



    private Handler handler = new Handler();








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
  view= inflater.inflate(R.layout.fragment_restaurant_booking, container, false);
  init();
  String restrauntName;
  restrauntName = "";
  restrauntNameTv.setText(restrauntName);
  ///////////
      Bundle arguments=getArguments();
 numberOfTables=arguments.getInt("NUMBER_OF_TABLES");
        //remeber to fetch the image from the data base:
        bookingList= new ArrayList<>();
     adapter =new BookingRecyclerViewAdapter((ArrayList<Booking>) bookingList);
       bookingRv.setLayoutManager(new LinearLayoutManager(getContext()));//here put the arraylist of the booking from the data base.
        bookingRv.setAdapter(adapter);
        //the logic of tables:
        tableAvailability = new boolean[numberOfTables];
        for (int i = 0; i < numberOfTables; i++) {
            tableAvailability[i] = true;  // All tables are available
        }
        gridLayout.setColumnCount(3);
        displayTables();

        // Fetch bookings from the database
        fetchBookingsFromDatabase();





        return view;
    }
    public void init(){

        restrauntPictureIv=view.findViewById(R.id.restrauntPictureIv);
        restrauntNameTv=view.findViewById(R.id.restrauntNameTv);
        bookingRv=view.findViewById(R.id.bookingRv);
        gridLayout = view.findViewById(R.id.gridLayout);
    }
    private void displayTables() {
        gridLayout.removeAllViews();
        for (int i = 0; i < numberOfTables; i++) {
            TextView tableView = new TextView(getContext());
            tableView.setText(String.valueOf(i + 1));
            tableView.setTextSize(24);
            tableView.setGravity(android.view.Gravity.CENTER);
            tableView.setBackgroundColor(tableAvailability[i] ? Color.GREEN : Color.RED);
            tableView.setLayoutParams(new GridLayout.LayoutParams());

            // Add the TextView to the GridLayout
            gridLayout.addView(tableView);
        }
    }
    private void fetchBookingsFromDatabase() {
        // Simulate fetching bookings from a database
        // You need to replace this with actual database code
        List<Booking> bookingsFromDb = new ArrayList<>(); // Replace with actual database query

        // Update the booking list and table availability
        for (Booking booking : bookingsFromDb) {
            bookingList.add(booking);
            tableAvailability[booking.getBookedTable() - 1] = false;
            scheduleBookingEnd(booking);
        }

    adapter.notifyDataSetChanged();
        displayTables();
    }
    private void scheduleBookingEnd(Booking booking) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date startTime = dateFormat.parse(booking.getStartTime());
            Date endTime = dateFormat.parse(booking.getEndTime());
            if (startTime != null && endTime != null) {
                long duration = endTime.getTime() - startTime.getTime();
                handler.postDelayed(() -> endBooking(booking), duration);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void endBooking(Booking booking) {
        tableAvailability[booking.getBookedTable() - 1] = true;

        // Find and remove the booking from the list
        int position = bookingList.indexOf(booking);
        if (position != -1) {
            bookingList.remove(position);
        adapter.removeBooking(position);
        }

        displayTables();
    }

    */


}





