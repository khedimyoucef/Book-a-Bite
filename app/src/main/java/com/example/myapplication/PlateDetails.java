package com.example.bookabiteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlateDetails extends AppCompatActivity {
private Button keepOrderBtn;
private Button thatsItBtn;
private Button minusBtn;
private Button plusBtn;
private TextView PlateNbrTv;
private TextView mealPriceTv;
public int mealPrice;
public   int numberOfPlates;
public double totalUnitPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_details);
       init();
       Intent intent=getIntent();
       String mealPriceStr=intent.getStringExtra("MEAL_PRICE");
       mealPriceTv.setText(mealPriceStr);
        mealPrice=Integer.parseInt(mealPriceStr);
        numberOfPlates=Integer.parseInt(PlateNbrTv.getText().toString());

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               numberOfPlates++;
               PlateNbrTv.setText(numberOfPlates);
               totalUnitPrice=numberOfPlates*mealPrice;
               mealPriceTv.setText((int) totalUnitPrice);




            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfPlates--;
                if (numberOfPlates>=1){
                PlateNbrTv.setText(numberOfPlates);
                totalUnitPrice=numberOfPlates*mealPrice;
                mealPriceTv.setText((int) totalUnitPrice);

                }

            }
        });


       keepOrderBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(PlateDetails.this,DetailsFragment.class));
           }
       });
       thatsItBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(PlateDetails.this,SummaryActivity.class));
           }
       });




    }
    public void init(){
        keepOrderBtn = findViewById(R.id.keepOrderBtn);
        thatsItBtn = findViewById(R.id.thatsItBtn);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        mealPriceTv=findViewById(R.id.mealPriceTv);
        PlateNbrTv=findViewById(R.id.plateNbrTv);
    }

}