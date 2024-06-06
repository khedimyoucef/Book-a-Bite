package com.example.myapplication;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;





public class RegisterPageActivity extends AppCompatActivity {



    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();//getting the reference of the database to perform read and write operations


    public int getEmailLength(String email) {
        // Check if email is null or empty
        if (email == null || email.isEmpty()) {
            return 0;
        } else {
            int i = 0;
            // Iterate over the characters of the email
            while (i < email.length() && email.charAt(i) != '@') {
                i++;
            }
            return i;
        }
    }


    EditText editTextEmail,editTextPassword,editTextPasswordConfirmation,editTextUsername,editTextPhoneNumber;
    Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.register_page);
        editTextUsername = findViewById(R.id.username_input);
        editTextEmail = findViewById(R.id.email_input);
        editTextPhoneNumber = findViewById(R.id.phone_number_input);
        editTextPassword = findViewById(R.id.password_input);
        editTextPasswordConfirmation = findViewById(R.id.password_confirmation);
        buttonReg = findViewById(R.id.register);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();//initializing mAuth
        ProgressBar progressBar = findViewById(R.id.progressBar);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String username = String.valueOf(editTextUsername.getText());
                String email = String.valueOf(editTextEmail.getText());
                String phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                String password = String.valueOf(editTextPassword.getText());
                String passwordConfirmation = String.valueOf(editTextPasswordConfirmation.getText());
                if (TextUtils.isEmpty(email)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterPageActivity.this,"Enter email first",Toast.LENGTH_LONG).show();
                    return;// To exit the method prematurly and not open the activity (return to void)
                }
                if (TextUtils.isEmpty(phoneNumber)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterPageActivity.this,"Enter your Phone number first",Toast.LENGTH_LONG).show();
                    return;// To exit the method prematurly and not open the activity (return to void)
                }
                if (TextUtils.isEmpty(username)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterPageActivity.this,"Enter username first",Toast.LENGTH_LONG).show();
                    return;// To exit the method prematurly and not open the activity (return to void)
                }

                if (TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterPageActivity.this,"Enter password first",Toast.LENGTH_LONG).show();
                    return;
                }
                String subEmail = email.substring(0,getEmailLength(email));
                if (password.equals(subEmail)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterPageActivity.this,"Password must be different from email",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!(password.equals(passwordConfirmation))){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterPageActivity.this,"Password confirmation doesn't match",Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterPageActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();//the user is added to the authentication database right here

                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterPageActivity.this, "Account created with success a verification email has been sent",
                                                        Toast.LENGTH_SHORT).show();

                                                    /*
                                                    mDatabase.child("users").child(user.getUid()).setValue(clientObject);*/

                                                    //initializing the shared preferences getter
                                                    SharedPreferences prefs = getSharedPreferences(SharedPrefsFile, MODE_PRIVATE);
                                                    boolean isClient = prefs.getBoolean(CLIENT_KEY, true);
                                                    if(isClient) {
                                                        //pushing the clientObject created from ClientClass to the realtime database

                                                        ClientClass clientObject = new ClientClass(username, email,phoneNumber,password,user.getUid());//here we create a new client object using its constructor to pass it to the database
                                                        mDatabase.child("clients").child(clientObject.getUserId()).setValue(clientObject);
                                                    }else {//the user is a manager therefore we add it to a separate mangers collection
                                                        ManagerClass managerObject = new ManagerClass(username,email,phoneNumber,password,user.getUid(),true);
                                                        mDatabase.child("managers").child(managerObject.getUserId()).setValue(managerObject);
                                                    }


                                                Intent i = new Intent(RegisterPageActivity.this, LoginActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                            else{
                                                Toast.makeText(RegisterPageActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                                } else {
                                    // If sign in fails, check if it's due to an existing user
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        progressBar.setVisibility(View.GONE);
                                        // User already exists, show a toast notification
                                        Toast.makeText(RegisterPageActivity.this, "User already exists go to LoginActivity.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        // Other authentication failures, display a generic message
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterPageActivity.this, "Authentication failed : wrong credentials.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

            }
        });
    }
    public void LaunchLoginPage(View v){

            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);


    }
}