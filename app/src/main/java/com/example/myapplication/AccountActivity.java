package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.play.core.review.ReviewManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.play.core.review.ReviewInfo;


public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.account_page);
        /*
        Intent i = getIntent();//this has to be inside the onCreate as it executes when the new activity is launched
        String message = i.getStringExtra("NameOfPassedData");
        ((TextView)findViewById(R.id.SetterTest)).setText(message);
        */


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in

            ((TextView)findViewById(R.id.emailinfo)).setText(user.getEmail());// finding a view by id then casting it to TextView and setting it's text tp the signed in email
            String name;
            name = user.getDisplayName();
            if (name != null) {
                ((TextView) findViewById(R.id.nameInfo)).setText(name);
            }else{
                //name not set in the register activity
                Toast.makeText(this,"Username not set", Toast.LENGTH_SHORT).show();
            }
        } else {
            // No user is signed in
        }
    }

    public void SignOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(AccountActivity.this, LoginActivity.class);//this is there to refer to the current activity
        startActivity(i);
        finish();
    }






    //handling the google play rating api
    private ReviewInfo reviewInfo;
    private ReviewManager reviewManager;
    /*
    void activateReviewInfo(){
        reviewManager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> managerInfoTask = reviewManager.requestReviewFlow();// automated task to generate the review popup after the user has been using the app for a certain amount of time
        managerInfoTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                reviewInfo = task.getResult();
            }else{
                Toast.makeText(this,"Review failed",Toast.LENGTH_SHORT).show();
            }
        });

    }
    */

}