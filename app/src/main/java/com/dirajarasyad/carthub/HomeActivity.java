package com.dirajarasyad.carthub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;


import com.dirajarasyad.carthub.fragment.AdminFragment;
import com.dirajarasyad.carthub.fragment.SellerFragment;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.fragment.CartFragment;
import com.dirajarasyad.carthub.fragment.HistoryFragment;
import com.dirajarasyad.carthub.fragment.HomeFragment;
import com.dirajarasyad.carthub.fragment.ProfileFragment;
import com.dirajarasyad.carthub.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
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
        this.onBind();

    }

    private void initial() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        homeContainerFL = findViewById(R.id.homeContainerFL);

        sessionManager = new SessionManager(this);
        User user = sessionManager.getUser();

        if (user == null) {
            sessionManager.destroySession();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        }
    }

    private void onBind() {
        User user = sessionManager.getUser();

        bottom_navigation.getMenu().clear();
        bottom_navigation.getMenu().add(0, R.id.navHome, 0, getString(R.string.menu_home)).setIcon(R.drawable.baseline_home_24);
        bottom_navigation.getMenu().add(0, R.id.navCart, 1, getString(R.string.menu_cart)).setIcon(R.drawable.baseline_shopping_cart_24);
        bottom_navigation.getMenu().add(0, R.id.navHistory, 2, getString(R.string.menu_history)).setIcon(R.drawable.baseline_article_24);
        bottom_navigation.getMenu().add(0, R.id.navProfile, 3, getString(R.string.menu_profile)).setIcon(R.drawable.baseline_person_24);

        if (user.getRole() == User.Role.SELLER) {
            bottom_navigation.getMenu().add(0, R.id.navRole, 4, getString(R.string.menu_seller)).setIcon(R.drawable.baseline_business_24);
        } else if (user.getRole() == User.Role.ADMIN) {
            bottom_navigation.getMenu().add(0, R.id.navRole, 4, getString(R.string.menu_admin)).setIcon(R.drawable.baseline_admin_panel_settings_24);
        }

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
            } else if ((itemId == R.id.navRole) & (user.getRole() == User.Role.SELLER)) {
                selectedFragment = new SellerFragment();
            } else if ((itemId == R.id.navRole) & (user.getRole() == User.Role.ADMIN)) {
                selectedFragment = new AdminFragment();
            } else {
                return false;
            }

            getSupportFragmentManager().beginTransaction().replace(homeContainerFL.getId(), selectedFragment).commit();
            return true;
        });
    }
}