package com.example.bookabiteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TableReservationAcivity extends AppCompatActivity {
    private TextView dateTextView;
    private TextView startHourTextView;
    private TextView endHourTextView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reservation_acivity);
        dateTextView = findViewById(R.id.date);
        startHourTextView = findViewById(R.id.starthour);
        endHourTextView = findViewById(R.id.endhour);

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
              //  openEndTimeDialog();
            }
        });
    }

    private void openStartTimeDialog() {
      //  TimePickerDialog dialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
         //   @Override
         //   public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
           //     startHourTextView.setText(String.format("%02d:%02d", hours, minutes));
        //    }
      //  }, 15, 00, true);
       // dialog.show();
   // }

   // private void openEndTimeDialog() {
      //  TimePickerDialog dialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
       //     @Override
         //   public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
          //      endHourTextView.setText(String.format("%02d:%02d", hours, minutes));
            }
      //  }, 15, 00, true);
       // dialog.show();
   // }
}