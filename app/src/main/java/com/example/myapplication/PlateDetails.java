package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class PlateDetails extends AppCompatActivity {
    private Button keepOrderingBtn;
    private Button thatsItBtn;
    private Button minusBtn;
    private Button plusBtn;
    private TextView PLateQuantityTv;
    private TextView mealPriceTv, dishName;
    private ImageView plateImage;
    public double mealPrice;
    public int numberOfPlates;
    public double totalUnitPrice;
    Button addToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_details);

        // Initialize the TextViews and Buttons
        mealPriceTv = findViewById(R.id.price);
        plateImage = findViewById(R.id.plateImage);
        dishName = findViewById(R.id.dishName);
        PLateQuantityTv = findViewById(R.id.quantity); // Initialize this TextView

        keepOrderingBtn = findViewById(R.id.keepOrderingBtn);
        thatsItBtn = findViewById(R.id.thatsItBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        addToCart = findViewById(R.id.addToCart);
        // Get the Intent extras
        Intent intent = getIntent();
        Dish dish = intent.getParcelableExtra("dishObject");

        String mealPriceStr = String.valueOf(dish.getPrice());
        String mealName = dish.getName();

        // Set the dish name and meal price
        dishName.setText(mealName);
        mealPriceTv.setText(mealPriceStr);

        // Ensure mealPriceStr is not null before parsing
        if (mealPriceStr != null) {
            mealPrice = Double.parseDouble(mealPriceStr);
        } else {
            mealPrice = 0; // Default value if mealPriceStr is null
        }

        numberOfPlates = 1; // Default number of plates


        mealPriceTv.setText(String.valueOf(mealPrice));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfPlates++;
                PLateQuantityTv.setText(String.valueOf(numberOfPlates));
                totalUnitPrice = numberOfPlates * mealPrice;
                mealPriceTv.setText(String.valueOf(totalUnitPrice));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfPlates > 1) {
                    numberOfPlates--;
                    PLateQuantityTv.setText(String.valueOf(numberOfPlates));
                    totalUnitPrice = numberOfPlates * mealPrice;
                    mealPriceTv.setText(String.valueOf(totalUnitPrice));
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    PublicData.currentOrder.addItem(mealName,numberOfPlates);// the hashmap does not support duplicates so an override will happen if the user tries to order the same meal again

                OrderItem orderItem = new OrderItem(dish.getName(),dish.getCategory(),dish.getDescription(),dish.getPrice(),dish.getImg(),numberOfPlates);
                orderItem.setTotalPrice(totalUnitPrice);
                PublicData.currentOrder.addItemToList(orderItem);
                Toast.makeText(PlateDetails.this,"added to cart",Toast.LENGTH_SHORT).show();

            }
        });

        keepOrderingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlateDetails.this, FoodReservationActivity.class));
            }
        });

        thatsItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicData.currentOrder.getOrderedItemsList().isEmpty()){
                    Toast.makeText(PlateDetails.this,"book something first",Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(PlateDetails.this, SummaryActivity.class));
                    finish();
                }
            }
        });
    }
}
