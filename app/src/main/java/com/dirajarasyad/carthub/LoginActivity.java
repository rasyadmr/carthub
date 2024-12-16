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

import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

public class LoginActivity extends AppCompatActivity {
    private TextView loginUsernameTV, loginPasswordTV, loginErrorTV;
    private EditText loginUsernameET, loginPasswordET;
    private Button loginSubmitBtn;

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
        String username = loginUsernameET.getText().toString();
        String password = loginPasswordET.getText().toString();

        DBUserManager userManager = new DBUserManager(this);
        userManager.open();
        User user = userManager.getUserByUsername(username);
        userManager.close();

        if (user == null) {
            loginUsernameTV.setTextColor(getResources().getColor(R.color.danger, null));
            loginPasswordTV.setTextColor(getResources().getColor(R.color.danger, null));
            loginErrorTV.setVisibility(View.VISIBLE);
            loginErrorTV.setText(R.string.auth_credential_error);
        } else if (!password.equals(user.getPassword())) {
            loginUsernameTV.setTextColor(getResources().getColor(R.color.danger, null));
            loginPasswordTV.setTextColor(getResources().getColor(R.color.danger, null));
            loginErrorTV.setVisibility(View.VISIBLE);
            loginErrorTV.setText(R.string.auth_credential_error);
        } else {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.createSession(user);

            Intent homepage = new Intent(this, HomeActivity.class);
            startActivity(homepage);
            finishAffinity();
        }
    }
}