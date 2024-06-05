    package com.example.myapplication;

    import static android.content.ContentValues.TAG;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ProgressBar;
    import android.widget.Toast;

    import androidx.appcompat.widget.SearchView;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;

    public class homeFragment extends Fragment implements RecyclerViewInterface {

        private SearchView searchView;
        private RecyclerView recyclerView;
        public ArrayList<ManagerClass> foundRestaurantsList = new ArrayList<>();
        private fdRestaurantsRecyclerViewAdapter adapter;

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        private String mParam1;
        private String mParam2;
        private  ProgressBar progressBar;

        public homeFragment() {
            // Required empty public constructor
        }

        public static homeFragment newInstance(String param1, String param2) {
            homeFragment fragment = new homeFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            searchView = view.findViewById(R.id.searchView);
            recyclerView = view.findViewById(R.id.recyclerView);
            progressBar = view.findViewById(R.id.progress_bar);
            return view;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
           progressBar.setVisibility(View.VISIBLE);

            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("managers");
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ManagerClass data = snapshot.getValue(ManagerClass.class);
                        foundRestaurantsList.add(data);
                    }

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    /*
                    // Add spacing decorator
                    SpacingItemDecorator itemDecorator = new SpacingItemDecorator(10); // 10px spacing
                    recyclerView.addItemDecoration(itemDecorator);
                    recyclerView.setPadding(0, 10, 0, 0); // Set padding to 0
                    recyclerView.setClipToPadding(false); // Adjust clipToPadding

                    */
                    adapter = new fdRestaurantsRecyclerViewAdapter(requireContext(), foundRestaurantsList, homeFragment.this);
                    recyclerView.setAdapter(adapter);



                    //for a horizontal recyclerview we can use :
                    /*
                    RecyclerView recyclerView = findViewById(R.id.horizontal_recycler_view);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    */


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "Error retrieving data", databaseError.toException());
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterList(newText);
                    return true;
                }
            });
        }

        private void filterList(String text) {
            ArrayList<ManagerClass> filteredList = new ArrayList<>();
            for (ManagerClass managerObject : foundRestaurantsList) {
                if (managerObject.getRestaurantName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(managerObject);
                }
            }

            if (filteredList.isEmpty() && text.isEmpty()) {
                Toast.makeText(getContext(), "no matching items found", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setFilteredList(filteredList);
            }
        }

        @Override
        public void onItemClicked(ManagerClass managerObject) {
            //here the onItemClick behaviour for the rows is handled
            Intent intent = new Intent(getActivity(),RestaurantActivity.class);
            //now we pass all the required data using intent.putExtra();

            intent.putExtra("restaurantName",managerObject.getRestaurantName()); // the position of the manager object in the list is set in the adapter class
            intent.putExtra("restaurantID",managerObject.getUserId());

            startActivity(intent);
            // we can't kill the fragment using finish(); here cause it's not an activity
        }
    }
