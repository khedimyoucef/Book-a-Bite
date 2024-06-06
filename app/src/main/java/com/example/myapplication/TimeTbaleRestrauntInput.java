package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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

import java.util.Locale;

public class TimeTbaleRestrauntInput extends AppCompatActivity {
 private EditText startTimeEt;
 private EditText endTimeEt;
 private EditText numberTablesEt;
 private TextView nextButtonTv;
 private String startTime,endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tbale_restraunt_input);

        // Initialize views
        init();

        // Set onClickListeners for startTimeEt and endTimeEt
        startTimeEt.setFocusable(false);
        startTimeEt.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                startTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                startTimeEt.setText(startTime);
            }, 15, 00, true);
            dialog.show();
        });

        endTimeEt.setFocusable(false);
        endTimeEt.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                endTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                endTimeEt.setText(endTime);
            }, 15, 00, true);
            dialog.show();
        });

        // Set onClickListener for nextButtonTv
        nextButtonTv.setOnClickListener(v -> {
            // Get number of tables from EditText
            int numberOfTables = Integer.valueOf(numberTablesEt.getText().toString());

            if (startTime.isEmpty() || endTime.isEmpty() || numberOfTables == 0){
                Toast.makeText(TimeTbaleRestrauntInput.this,"Please fill in all the info",Toast.LENGTH_SHORT).show();
            }else{
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

// Check if a user is currently signed in
                FirebaseUser currentUser = mAuth.getCurrentUser();


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("managers").child(currentUser.getUid());
                databaseReference.child("numberOfTables").setValue(numberOfTables)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully written
                                Log.d(TAG, "Data written successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while writing data
                                Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                            }
                        });

                databaseReference.child("openTime").setValue(startTime)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully written
                                Log.d(TAG, "Data written successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while writing data
                                Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                            }
                        });

                databaseReference.child("closeTime").setValue(endTime)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully written
                                Log.d(TAG, "Data written successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while writing data
                                Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                            }
                        });
                databaseReference.child("newUser").setValue(false)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully written
                                Log.d(TAG, "Data written successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error occurred while writing data
                                Log.e(TAG, "Error writing data to Firebase: " + e.getMessage());
                            }
                        });


                Intent intent = new Intent(TimeTbaleRestrauntInput.this, FillMenu.class);
                startActivity(intent);

            }



        });
    }





    public void init(){
        startTimeEt=findViewById(R.id.startTimeEt);
        endTimeEt=findViewById(R.id.endTimeEt);
        numberTablesEt=findViewById(R.id.numberTablesEt);
        nextButtonTv=findViewById(R.id.nextButtonTv);



    }
}