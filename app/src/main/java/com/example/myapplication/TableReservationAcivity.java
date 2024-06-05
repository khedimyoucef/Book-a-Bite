package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TableReservationAcivity extends AppCompatActivity {
    private TextView dateTextView;
    private TextView startHourTextView;
    private TextView endHourTextView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private int startHours, startMinutes, endHours, endMinutes;

    private Button nextButton;

    private EditText reservedSeatsEt;

    private int reservedSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation_acivity);

        nextButton = findViewById(R.id.nextButton);
        dateTextView = findViewById(R.id.date);
        startHourTextView = findViewById(R.id.starthour);
        endHourTextView = findViewById(R.id.endhour);
        reservedSeatsEt = findViewById(R.id.reservedSeats);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1; // Month is zero-based
                String date = day + "/" + month + "/" + year;
                dateTextView.setText(date);
            }
        };

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TableReservationAcivity.this,
                        dateSetListener,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        startHourTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStartTimeDialog();
            }
        });

        endHourTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEndTimeDialog();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:add input validation (type and availability )
                reservedSeats = Integer.parseInt(reservedSeatsEt.getText().toString());

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("clients");

                // Add a ValueEventListener to retrieve data from the database
                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey(); // Get the key of the child node
                            if (key.equals(currentUser.getUid())) {
                                ClientClass client = snapshot.getValue(ClientClass.class);

                                PublicData.currentOrder = new Order(currentUser.getUid(), client.getUsername(), reservedSeats, startHours, startMinutes, endHours, endMinutes, day, month);
                                Intent intent = new Intent(TableReservationAcivity.this, FoodReservationActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle potential errors
                    }
                });
            }
        });
    }

    private void openStartTimeDialog() {
        // Inflate custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.custom_time_picker_dialog, null);

        // Find views in the custom layout
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        // Create AlertDialog.Builder and set custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Create AlertDialog
        AlertDialog dialog = builder.create();

        // Set OnClickListener for the confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve selected time from TimePicker
                startHours = timePicker.getCurrentHour();
                startMinutes = timePicker.getCurrentMinute();

                // Update UI with selected time
                startHourTextView.setText(String.format("%02d:%02d", startHours, startMinutes));

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    private void openEndTimeDialog() {
        // Inflate custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.custom_time_picker_dialog, null);

        // Find views in the custom layout
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);

        // Create AlertDialog.Builder and set custom view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Create AlertDialog
        AlertDialog dialog = builder.create();

        // Set OnClickListener for the confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve selected time from TimePicker
                endHours = timePicker.getCurrentHour();
                endMinutes = timePicker.getCurrentMinute();

                // Update UI with selected time
                endHourTextView.setText(String.format("%02d:%02d", endHours, endMinutes));

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    /*
    private void openStartTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, R.style.CustomTimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                startHourTextView.setText(String.format("%02d:%02d", hours, minutes));
            }
        }, 15, 0, true);
        dialog.show();
    }

    private void openEndTimeDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this, R.style.CustomTimePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                endHourTextView.setText(String.format("%02d:%02d", hours, minutes));
            }
        }, 15, 0, true);
        dialog.show();
    }


     */
}
