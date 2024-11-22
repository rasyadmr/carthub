package com.dirajarasyad.carthub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;


import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.fragment.CartFragment;
import com.dirajarasyad.carthub.fragment.HistoryFragment;
import com.dirajarasyad.carthub.fragment.HomeFragment;
import com.dirajarasyad.carthub.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private ImageView homeLogoIV;
    private BottomNavigationView bottom_navigation;
    private FrameLayout homeContainerFL;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initial();

        getSupportFragmentManager().beginTransaction().replace(homeContainerFL.getId(), new HomeFragment()).commit();
        bottom_navigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment;
            int itemId = item.getItemId();

            if (itemId == R.id.navHome) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.navCart) {
                selectedFragment = new CartFragment();
            } else if (itemId == R.id.navHistory) {
                selectedFragment = new HistoryFragment();
            } else if (itemId == R.id.navProfile) {
                selectedFragment = new ProfileFragment();
            } else {
                return false;
            }

            getSupportFragmentManager().beginTransaction().replace(homeContainerFL.getId(), selectedFragment).commit();
            return true;
        });


    }

    private void initial() {
        homeLogoIV = findViewById(R.id.homeLogoIV);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        homeContainerFL = findViewById(R.id.homeContainerFL);

        sessionManager = new SessionManager(this);

        if (sessionManager.getUser() == null) {
            sessionManager.destroySession();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        }
    }
}