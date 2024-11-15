package com.dirajarasyad.carthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    ImageView mainLogoIV;
    Button mainLoginBtn, mainRegisterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivityCL), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initial();

        mainLoginBtn.setOnClickListener(this::onClick);
        mainRegisterBtn.setOnClickListener(this::onClick);
    }

    private void initial() {
        mainLogoIV = findViewById(R.id.mainLogoIV);
        mainLoginBtn = findViewById(R.id.mainLoginBtn);
        mainRegisterBtn = findViewById(R.id.mainRegisterBtn);
    }

    private void interaction() {

    }

    private void onClick(View view) {
        if (view == mainLoginBtn) {
            Intent loginPage = new Intent(this, LoginActivity.class);
            startActivity(loginPage);
        } else if (view == mainRegisterBtn) {
            Intent registerPage = new Intent(this, RegisterActivity.class);
            startActivity(registerPage);
        }
    }
}