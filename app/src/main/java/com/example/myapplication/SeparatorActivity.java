package com.example.myapplication;



import static com.example.myapplication.SplashScreenActivity.CLIENT_KEY;
import static com.example.myapplication.SplashScreenActivity.SharedPrefsFile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SeparatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_seperator);

        SharedPreferences.Editor editor = getSharedPreferences(SharedPrefsFile, MODE_PRIVATE).edit();
        Button clientBtn = findViewById(R.id.clientBtn);

        Intent intent = new Intent(this, LoginActivity.class);

       clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when the button is clicked perform the following


                editor.putBoolean(CLIENT_KEY, true);//the reference is used to locate the shared variable in the storage
                editor.apply();
                Log.d("isClient","isClient set to true in separator activity");
                startActivity(intent); // Start LoginActivity
                finish();

            }
        });

        Button managerBtn = findViewById(R.id.managerBtn);

        managerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedPreferences is used to store the variable locally in the phone's storage


                editor.putBoolean(CLIENT_KEY, false);// the user is a manager
                editor.apply();
                Log.d("isClient","isClient set to false in separator activity");
                startActivity(intent); // Start LoginActivity
                finish();

            }
        });
    }

}