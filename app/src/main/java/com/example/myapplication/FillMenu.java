package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FillMenu extends AppCompatActivity {
    private FirebaseStorage mStorage;
    public Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Spinner CategoryListSp;
    private EditText dishNameEt;
    private EditText dishDescriptionEt;
    private EditText dishPriceEt;
    private ImageView uploadDishImageBtn;
    private Button addDishBtn;
    private TextView nextButtonTv;
    public String imagePath;
    private String dishCategory;
    private String dishKey;
    public ArrayList<Dish> dishArrayList = new ArrayList<>();
    private String downloadUrl;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_menu);

        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        init();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Initialize FirebaseAuth and get current user
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Initialize Firebase Storage and Database Reference
        mStorage = FirebaseStorage.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Fill the spinner
        String[] categories = {"Mediterranean", "Seafood", "Sandwiches", "Burgers", "Pizza", "Salads", "Desserts", "Drinks", "Gratin", "Pasta"};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(categories));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getBaseContext(), R.layout.spinner_text_style, arrayList);
        CategoryListSp.setAdapter(arrayAdapter);

        // Pick the Category chosen by user
        dishCategory = CategoryListSp.getSelectedItem().toString();

        // Set onClick listener for the upload button
        uploadDishImageBtn.setOnClickListener(v -> chooseImage());

        // Set onClick listener for the add dish button
        addDishBtn.setOnClickListener(v -> addDish());
        // nextButtonTv.setOnClickListener(v -> sendData());
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                // Get the selected image URI
                selectedImageUri = data.getData();
                imagePath = getRealPathFromURI(selectedImageUri);
                Toast.makeText(getBaseContext(), "Image Added Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addDish() {
        String dishName = dishNameEt.getText().toString();
        String dishDescription = dishDescriptionEt.getText().toString();

        if (!dishName.isEmpty() && !dishDescription.isEmpty() && !dishCategory.isEmpty() && isValidPositiveDouble(dishPriceEt.getText().toString()) && selectedImageUri != null) {
            double dishPrice = Double.parseDouble(dishPriceEt.getText().toString());
            Dish dish = new Dish(dishName, dishCategory, dishDescription, dishPrice, imagePath);
            dishArrayList.add(dish);

            if (currentUser != null) {
                // Store image in Firebase Storage
                StorageReference storageReference = mStorage.getReference().child("MenuImages").child(currentUser.getUid()).child(dishName + ".jpg");
                storageReference.putFile(selectedImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            Toast.makeText(FillMenu.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                            // Get the download URL of the uploaded image
                            taskSnapshot.getStorage().getDownloadUrl()
                                    .addOnSuccessListener(downloadUri -> {
                                        downloadUrl = downloadUri.toString();
                                        dishKey = mDatabase.child("managers").child(currentUser.getUid()).child("menu").push().getKey();
                                        dish.setImg(downloadUrl);

                                        // Save dish to Realtime Database
                                        mDatabase.child("managers").child(currentUser.getUid()).child("menu").child(dishKey).setValue(dish);

                                        // Save the download URL to MenuImageIDs collection
                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put(dishKey, downloadUrl);
                                        mDatabase.child("MenuImageIDs").child(currentUser.getUid()).updateChildren(userData);

                                        Toast.makeText(FillMenu.this, "Dish added successfully, keep adding or continue", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(FillMenu.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "Failed to get download URL", e);
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(FillMenu.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Failed to upload image", e);
                        });
            } else {
                Toast.makeText(FillMenu.this, "No user signed in", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "No user signed in");
            }

            // Clear the EditText fields
            dishNameEt.getText().clear();
            dishPriceEt.getText().clear();
            dishDescriptionEt.getText().clear();
        } else {
            validateInputs(dishName, dishDescription);
        }
    }

    private void validateInputs(String dishName, String dishDescription) {
        if (dishName.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please Enter The Dish Name", Toast.LENGTH_SHORT).show();
        } else if (dishDescription.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please Enter The Dish Description", Toast.LENGTH_SHORT).show();
        } else if (selectedImageUri == null) {
            Toast.makeText(getBaseContext(), "Please choose an image for your dish", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Please Select A Category", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    public void sendData() {
        Intent intent = new Intent(getBaseContext(), homeFragment.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dishArrayList", dishArrayList);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    */

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        }
        return null;
    }

    public boolean isValidPositiveDouble(String textInput) {
        if (textInput.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please Enter a Price!", Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            double doubleValue = Double.parseDouble(textInput);
            return doubleValue > 0;
        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(), "Please Enter a Valid Price", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void init() {
        CategoryListSp = findViewById(R.id.CategoryListSp);
        dishDescriptionEt = findViewById(R.id.dishDescriptionEt);
        dishNameEt = findViewById(R.id.dishNameEt);
        uploadDishImageBtn = findViewById(R.id.uploadDishImageBtn);
        addDishBtn = findViewById(R.id.addDishBtn);
        nextButtonTv = findViewById(R.id.nextButtonTv);
        dishPriceEt = findViewById(R.id.dishPriceEt);
    }
}
