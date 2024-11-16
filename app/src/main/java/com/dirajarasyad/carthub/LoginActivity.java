package com.dirajarasyad.carthub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

public class LoginActivity extends AppCompatActivity {
    TextView loginUsernameTV, loginPasswordTV, loginErrorTV;
    EditText loginUsernameET, loginPasswordET;
    Button loginSubmitBtn;
    SharedPreferences authPrefData;

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
        String username = loginUsernameET.getText().toString();
        String password = loginPasswordET.getText().toString();

        DBUserManager userManager = new DBUserManager(this);
        userManager.open();
        User user = userManager.getUserByUsername(username);
        userManager.close();

        if (user == null) {
            loginErrorTV.setText(R.string.auth_credential_error);
        } else if (!password.equals(user.getPassword())) {
            loginErrorTV.setText(R.string.auth_credential_error);
        } else {
            authPrefData = getSharedPreferences("auth_preference", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = authPrefData.edit();
            editor.putString("user_id", user.getId());
            editor.apply();

            Intent homepage = new Intent(this, HomeActivity.class);
            startActivity(homepage);
            finishAffinity();
        }
    }
}