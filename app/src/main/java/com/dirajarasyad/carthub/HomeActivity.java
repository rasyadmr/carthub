package com.dirajarasyad.carthub;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.dirajarasyad.carthub.adapter.CategoryAdapter;
import com.dirajarasyad.carthub.fragment.CartFragment;
import com.dirajarasyad.carthub.fragment.HomeFragment;
import com.dirajarasyad.carthub.fragment.ProfileFragment;
import com.dirajarasyad.carthub.model.Category;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ImageView homeLogoIV;
    private BottomNavigationView bottom_navigation;
    private FrameLayout homeContainerFL;

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

        initial();

        getSupportFragmentManager().beginTransaction().replace(homeContainerFL.getId(), new HomeFragment()).commit();
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.navHome) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.navCart) {
                    selectedFragment = new CartFragment();
                } else if (itemId == R.id.navProfile) {
                    selectedFragment = new ProfileFragment();
                } else {
                    return false;
                }

                getSupportFragmentManager().beginTransaction().replace(homeContainerFL.getId(), selectedFragment).commit();
                return true;
            }
        });


    }

    private void initial() {
        homeLogoIV = findViewById(R.id.homeLogoIV);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        homeContainerFL = findViewById(R.id.homeContainerFL);
    }
}