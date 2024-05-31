package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    Vibrator vibrator;
    BottomNavigationView bottomNavigationView;
    Fragment currentFragment = null;
    boolean isNavigating = false; // Flag to check if navigation is in progress
    //the user is not navigating be default when the activity launches

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout flags to force display over the notch (it has to be declared in the onCreate before setting the content view)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        currentFragment = new homeFragment();
        replaceFragment(currentFragment);

        // By default, the app opens the home fragment where we can search for a restaurant
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (isNavigating) {
                    return false; // Ignore if a navigation is already in progress
                }
                isNavigating = true; // Set navigating flag

                Fragment fragment = null;
                // Vibrate for 100 milliseconds
                vibrator.vibrate(100);
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    fragment = new homeFragment();
                } else if (itemId == R.id.nav_favorite) {
                    fragment = new favoriteFragment();
                } else if (itemId == R.id.nav_reservations) {
                    fragment = new reservationsFragment();
                } else if (itemId == R.id.nav_profile) {
                    fragment = new profileFragment();
                }

                // Check if the selected fragment is different from the current one
                if (fragment != null && !fragment.getClass().equals(currentFragment.getClass())) {
                    currentFragment = fragment;
                    replaceFragment(fragment);
                }

                // Use a Handler to re-enable navigation after 1 second
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isNavigating = false; // Re-enable navigation
                    }
                }, 250);

                return true;
            }
        });
    }


    private synchronized void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Avoid performing fragment transactions if the state is saved
        if (!fragmentManager.isStateSaved()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.replace(R.id.mainContainer, fragment);
            transaction.commit();
        }
    }
}
