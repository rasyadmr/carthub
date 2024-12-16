package com.dirajarasyad.carthub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.dirajarasyad.carthub.service.NotificationService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bottom_navigation;
    private FrameLayout homeContainerFL;
    private User user;

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

        NotificationService service = new NotificationService(this);
        service.checkPermission(this);
    }

    private void initial() {
        bottom_navigation = findViewById(R.id.bottom_navigation);
        homeContainerFL = findViewById(R.id.homeContainerFL);

        SessionManager sessionManager = new SessionManager(this);
        user = sessionManager.getUser();

        if (user == null) {
            sessionManager.destroySession();
            Intent main = new Intent(this, MainActivity.class);
            this.startActivity(main);
            this.finish();
        }

        this.generateToken();
    }

    private void onBind() {
        bottom_navigation.getMenu().clear();
        bottom_navigation.getMenu().add(0, R.id.navHome, 0, getString(R.string.menu_home)).setIcon(R.drawable.baseline_home_24);
        bottom_navigation.getMenu().add(0, R.id.navCart, 1, getString(R.string.menu_cart)).setIcon(R.drawable.baseline_shopping_cart_24);
        bottom_navigation.getMenu().add(0, R.id.navHistory, 2, getString(R.string.menu_history)).setIcon(R.drawable.baseline_article_24);
        bottom_navigation.getMenu().add(0, R.id.navProfile, 3, getString(R.string.menu_profile)).setIcon(R.drawable.baseline_person_24);

        if (user.getRole() == User.Role.MERCHANT) {
            bottom_navigation.getMenu().add(0, R.id.navRole, 4, getString(R.string.menu_merchant)).setIcon(R.drawable.baseline_business_24);
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
            } else if ((itemId == R.id.navRole) & (user.getRole() == User.Role.MERCHANT)) {
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

    private void generateToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String token = task.getResult();
                    Log.d("FCM", "FCM Token: " + token);
                }
            }
        });
    }
}