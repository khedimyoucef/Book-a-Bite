package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment implements cartRecyclerViewInterface {

    private RecyclerView mealItemRV;
    public ArrayList<OrderItem> cartMenuList = new ArrayList<>();
    private cartMenuAdapter adapter;
    private Button confirmOrderBtn;
    public CardFragment() {
        // Required empty public constructor
    }


    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        mealItemRV = view.findViewById(R.id.mealItemRV);
        confirmOrderBtn = view.findViewById(R.id.confirmOrderBtn);
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



                cartMenuList = PublicData.currentOrder.getOrderedItemsList();

                if (cartMenuList.isEmpty()){
                    Toast.makeText(getContext(),"no menu items found ",Toast.LENGTH_LONG).show();
                }
                else {
                    mealItemRV.setLayoutManager(new LinearLayoutManager(requireContext()));
                    adapter = new cartMenuAdapter(requireContext(), cartMenuList, CardFragment.this);
                    mealItemRV.setAdapter(adapter);
                }



            confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (PublicData.currentOrder.getOrderedItemsList().isEmpty()){
                        Toast.makeText(getContext(),"book something first",Toast.LENGTH_SHORT).show();
                    }else {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ordersRef = database.getReference().child("clients").child(currentUser.getUid()).child("bookings");



// Convert Order object to JSON
                        Gson gson = new Gson();
                        String jsonOrder = gson.toJson(PublicData.currentOrder);

// Push JSON data to Firebase
                        String orderId = ordersRef.push().getKey();
                        ordersRef.child(orderId).setValue(jsonOrder)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Order successfully pushed to Firebase
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to push order to Firebase
                                    }
                                });
                        Intent intent = new Intent(getContext(),SummaryActivity.class);
                        startActivity(intent);

                    }

                }
            });




    }


    @Override
    public void onItemClicked(OrderItem orderItem) {
        // Handle remove from cart
        PublicData.currentOrder.removeItemFromList(orderItem);

        PublicData.currentOrder.removeItem(orderItem.getName());

        adapter.notifyDataSetChanged();


    }
}
