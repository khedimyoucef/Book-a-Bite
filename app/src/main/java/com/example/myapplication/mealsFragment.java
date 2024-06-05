package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class mealsFragment extends Fragment implements dishRecyclerViewInterface {
    private Button bookNowbtn;

    private String currentRestaurantId;
View view;
public ArrayList<Dish> menuItemArrayList = new ArrayList<>();
private String dishName;
private String dishDesc;
private int dishImg;

private RecyclerView recyclerView;

private restaurantMenuRecyclerViewAdapter adapter;

    public mealsFragment() {
        // Required empty public constructor
    }

    public static mealsFragment newInstance() {
        return new mealsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentRestaurantId= getArguments().getString("restaurantIdKey");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_meals, container, false);
        bookNowbtn = view.findViewById(R.id.bookNowbtn);
        recyclerView = view.findViewById(R.id.mealsRV);




        return view;

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn1 = view.findViewById(R.id.cat1btn);
        Button btn2 = view.findViewById(R.id.cat2btn);
        Button btn3 = view.findViewById(R.id.cat3btn);
        Button btn4 = view.findViewById(R.id.cat4btn);
        Button btn5 = view.findViewById(R.id.cat5btn);
        Button btn6 = view.findViewById(R.id.cat6btn);
        Button btn7 = view.findViewById(R.id.cat7btn);
        Button btn8 = view.findViewById(R.id.cat8btn);
        Button btn9 = view.findViewById(R.id.cat9btn);
        Button btn10 = view.findViewById(R.id.cat10btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn1.getText().toString());
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn2.getText().toString());
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn3.getText().toString());
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn4.getText().toString());
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn5.getText().toString());
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn6.getText().toString());
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn7.getText().toString());
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn8.getText().toString());
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn9.getText().toString());
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterList(btn10.getText().toString());
            }
        });



        //progressBar.setVisibility(View.VISIBLE);
        menuItemArrayList.clear();

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("managers").child(currentRestaurantId).child("menu");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {// not for realtime updates
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Dish data = snapshot.getValue(Dish.class);
                    menuItemArrayList.add(data);
                }
                if (menuItemArrayList.isEmpty()){
                    Toast.makeText(getContext(),"no menu items found ",Toast.LENGTH_LONG).show();
                }
                //progressBar.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                adapter = new restaurantMenuRecyclerViewAdapter(requireContext(), menuItemArrayList, mealsFragment.this);
                recyclerView.setAdapter(adapter);





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error retrieving data", databaseError.toException());
            }

        });






        bookNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PublicData.sharedMenuList = menuItemArrayList;
                Intent intent = new Intent(getContext(), TableReservationAcivity.class);

                startActivity(intent);
            }
        });

}


    private void filterList(String text) {
        ArrayList<Dish> filteredList = new ArrayList<>();
        for (Dish dishObject : menuItemArrayList) {
            if (dishObject.getCategory().toLowerCase().equals(text)) {
                filteredList.add(dishObject);
            }
        }

        if (filteredList.isEmpty() && text.isEmpty()) {
            Toast.makeText(getContext(), "no matching items found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilteredList(filteredList);
        }
    }

    @Override
    public void onItemClicked(Dish dishObject) {

    }
}