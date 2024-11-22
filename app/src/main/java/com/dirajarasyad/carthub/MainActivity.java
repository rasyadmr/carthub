package com.dirajarasyad.carthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.seeder.DBCategorySeeder;
import com.dirajarasyad.carthub.database.seeder.DBItemSeeder;
import com.dirajarasyad.carthub.database.seeder.DBUserSeeder;

public class MainActivity extends AppCompatActivity {
    private ImageView mainLogoIV;
    private Button mainLoginBtn, mainRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.checkAuthPref();
        this.initial();

        mainLogoIV.setOnClickListener(this::onClick);
        mainLoginBtn.setOnClickListener(this::onClick);
        mainRegisterBtn.setOnClickListener(this::onClick);
    }

    private void checkAuthPref() {
        SessionManager sessionManager = new SessionManager(this);

        if (sessionManager.getStatus()) {
            Intent homepage = new Intent(this, HomeActivity.class);
            startActivity(homepage);
            finish();
        }
    }

    private void initial() {
        mainLogoIV = findViewById(R.id.mainLogoIV);
        mainLoginBtn = findViewById(R.id.mainLoginBtn);
        mainRegisterBtn = findViewById(R.id.mainRegisterBtn);
    }

    private void onClick(View view) {
        if (view == mainLogoIV) {
            new DBUserSeeder(this);
            new DBCategorySeeder(this);
            new DBItemSeeder(this);

            Toast.makeText(this, "Seeding successfully", Toast.LENGTH_LONG).show();
        } else if (view == mainLoginBtn) {
            Intent loginPage = new Intent(this, LoginActivity.class);
            startActivity(loginPage);
        } else if (view == mainRegisterBtn) {
            Intent registerPage = new Intent(this, RegisterActivity.class);
            startActivity(registerPage);
        }
    }
}