package com.dirajarasyad.carthub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    TextView loginUsernameTV, loginPasswordTV, loginErrorTV;
    EditText loginUsernameET, loginPasswordET;
    Button loginSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initial();
        loginSubmitBtn.setOnClickListener(this::onClick);
    }

    private void initial() {
        loginUsernameTV = findViewById(R.id.loginUsernameTV);
        loginPasswordTV = findViewById(R.id.loginPasswordTV);
        loginErrorTV = findViewById(R.id.loginErrorTV);

        loginUsernameET = findViewById(R.id.loginUsernameET);
        loginPasswordET = findViewById(R.id.loginPasswordET);

        loginSubmitBtn = findViewById(R.id.loginSubmitBtn);
    }

    private void onClick(View view) {
        // TODO Database Validation

        Intent homepage = new Intent(this, HomeActivity.class);
        startActivity(homepage);
        finishAffinity();
    }
}