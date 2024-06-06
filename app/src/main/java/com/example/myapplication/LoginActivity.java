package com.example.myapplication;
//testing git history control
import static android.content.ContentValues.TAG;

import static com.example.myapplication.SplashScreenActivity.CLIENT_KEY;
import static com.example.myapplication.SplashScreenActivity.SharedPrefsFile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    /*
    private boolean isValidEmail(String email) {
        // Check if the email is valid using Firebase Authentication method
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    FirebaseAuth mAuth;



    @Override
    public void onStart() {
        mAuth = FirebaseAuth.getInstance();
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && user.isEmailVerified() ){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.login_activity);
        EditText editTextEmail = findViewById(R.id.email_input);
        EditText editTextPassword = findViewById(R.id.password_input);
        Button forgotPassword = findViewById(R.id.forgotPassword);
        SharedPreferences prefs = getSharedPreferences(SharedPrefsFile, MODE_PRIVATE);



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText());//email is initialized every time a login or register button is clicked so that it is taken after an email has been enterd not immediately after creating the activity
                    if (isValidEmail(email)) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        // Password reset email sent successfully
                                        Toast.makeText(LoginActivity.this,"Password Reset Sent successfully , check your mail",Toast.LENGTH_LONG).show();
                                    } else {
                                        // Error occurred, handle accordingly
                                        Toast.makeText(LoginActivity.this,"Something went wrong try again",Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        // Invalid email format, handle accordingly
                        Toast.makeText(LoginActivity.this,"Invalid email :" + email,Toast.LENGTH_LONG).show();
                    }

            }
        });

        ProgressBar progressBar = findViewById(R.id.progressBar);


        Button buttonLogin;
        buttonLogin = (Button) findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());
                progressBar.setVisibility(View.VISIBLE);


                if (TextUtils.isEmpty(email)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Enter email first",Toast.LENGTH_LONG).show();
                    return;// To exit the method prematurly and not open the activity
                }

                if (TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Enter password first",Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    assert user != null;
                                    boolean isEmailVerified = user.isEmailVerified();

                                    if (isEmailVerified) {


                                        Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                                        if (prefs.getBoolean(CLIENT_KEY,true)){//if the user is a client launch the client side
                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(i);
                                            finish();}
                                        else if (!(prefs.getBoolean(CLIENT_KEY,true))){//launch the manager side

                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("managers").child(user.getUid()).child("newUser");

                                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    // This method will be called once with the value from the database.
                                                    Boolean newUser = dataSnapshot.getValue(Boolean.class);
                                                    if(newUser == null){
                                                        Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else {
                                                        // Convert Boolean to boolean
                                                        if (!newUser) {
                                                            Intent intent = new Intent(LoginActivity.this, RestrauntMain.class);
                                                            startActivity(intent); // Start LoginActivity
                                                            finish();
                                                        } else {
                                                            Intent intent = new Intent(LoginActivity.this, RestaurantCheckInActivity.class);
                                                            startActivity(intent); // Start LoginActivity
                                                            finish();

                                                        }
                                                    }
                                                }


                                                @Override
                                                public void onCancelled(DatabaseError error) {
                                                    // Failed to read value
                                                    Log.w(TAG, "Failed to read value.", error.toException());
                                                }
                                            });
                                            }
                                        }

                                    else{
                                        Toast.makeText(LoginActivity.this, "Verify your email first !", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If sign in fails, handle the failure based on the specific exception
                                    Exception e = task.getException();
                                    if (e instanceof FirebaseAuthInvalidUserException) {
                                        progressBar.setVisibility(View.GONE);
                                        // The user doesn't exist, handle accordingly (e.g., show relevant message)
                                        Log.w(TAG, "signInWithEmail:failure: invalid user", e);
                                        Toast.makeText(LoginActivity.this, "User doesn't exist.", Toast.LENGTH_SHORT).show();
                                    } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        progressBar.setVisibility(View.GONE);
                                        // Invalid credentials, handle accordingly (e.g., show relevant message)
                                        Log.w(TAG, "signInWithEmail:failure: invalid credentials", e);
                                        Toast.makeText(LoginActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        // Other exceptions, handle accordingly (e.g., show generic error message)
                                        Log.w(TAG, "signInWithEmail:failure", e);
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }                            }
                            }
                        });

            }
        });
    }


    /* public void LaunchHomePage(View v){
        Intent i = new Intent(this,AccountActivity.class);//this is there to refer to the current activity
        //intents are used for opening activities
        //we will pass the value of the entered email to the home page
        String Email = ((EditText)findViewById(R.id.email_input)).getText().toString();
        if (Email.length() <15) {
            Toast.makeText(this, "Invalid Email  please Try again  ", Toast.LENGTH_LONG).show();
        }else {
            String EmailEnd = Email.substring(Email.length() - 10);
            Log.d("EmailEnd", EmailEnd);
            if (EmailEnd.equals("@gmail.com")) {
                i.putExtra("NameOfPassedData", Email);
                startActivity(i);
                finish();
            } else {
                //exception goes here
                Toast.makeText(this, "Invalid Email  please Try again ", Toast.LENGTH_LONG).show();
            }
        }



    }
    */

    public void LaunchRegisterPage(View v){
        Intent i = new Intent(this,RegisterPageActivity.class);
        startActivity(i);
        finish();
    }



}