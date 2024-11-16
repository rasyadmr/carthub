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

import com.dirajarasyad.carthub.auth.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private TextView registerUsernameTV, registerPasswordTV, registerEmailTV, registerPhoneTV, registerAddressTV, registerUsernameErrorTV, registerPasswordErrorTV, registerEmailErrorTV, registerPhoneErrorTV, registerAddressErrorTV;
    private EditText registerUsernameET, registerPasswordET, registerEmailET, registerPhoneET, registerAddressET;
    private Button registerSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initial();
        registerSubmitBtn.setOnClickListener(this::onClick);
    }

    private void initial() {
        registerUsernameTV = findViewById(R.id.registerUsernameTV);
        registerPasswordTV = findViewById(R.id.registerPasswordTV);
        registerEmailTV = findViewById(R.id.registerEmailTV);
        registerPhoneTV = findViewById(R.id.registerPhoneTV);
        registerAddressTV = findViewById(R.id.registerAddressTV);

        registerUsernameET = findViewById(R.id.registerUsernameET);
        registerPasswordET = findViewById(R.id.registerPasswordET);
        registerEmailET = findViewById(R.id.registerEmailET);
        registerPhoneET = findViewById(R.id.registerPhoneET);
        registerAddressET = findViewById(R.id.registerAddressET);

        registerUsernameErrorTV = findViewById(R.id.registerUsernameErrorTV);
        registerPasswordErrorTV = findViewById(R.id.registerPasswordErrorTV);
        registerEmailErrorTV = findViewById(R.id.registerEmailErrorTV);
        registerPhoneErrorTV = findViewById(R.id.registerPhoneErrorTV);
        registerAddressErrorTV = findViewById(R.id.registerAddressErrorTV);

        registerSubmitBtn = findViewById(R.id.registerSubmitBtn);
    }

    private void onClick(View view) {
        String username = registerUsernameET.getText().toString();
        String password = registerPasswordET.getText().toString();
        String email = registerEmailET.getText().toString();
        String phone = registerPhoneET.getText().toString();
        String address = registerAddressET.getText().toString();

        if (this.validateInput(username, password, email, phone)) {
            DBUserManager userManager = new DBUserManager(this);
            userManager.open();
            userManager.addUser(username, password, email, phone, address);
            User newUser = userManager.getUserByUsername(username);
            userManager.close();

            SessionManager sessionManager = new SessionManager(this);
            sessionManager.createSession(newUser);

            Intent homepage = new Intent(this, HomeActivity.class);
            startActivity(homepage);
            finishAffinity();
        }
    }

    private boolean validateInput(String username, String password, String email, String phone) {
        this.clearError();
        DBUserManager userManager = new DBUserManager(this);
        userManager.open();
        boolean flag = true;

        // TODO color red when error
        // Username
        if (username.length() < 4 || username.length() > 20) {
            registerUsernameErrorTV.setText(R.string.auth_username_error_length);
            flag = false;
        } else if (userManager.getUserByUsername(username) != null) {
            registerUsernameErrorTV.setText(R.string.auth_username_error_unique);
            flag = false;
        } else if (!Pattern.matches("^[a-zA-Z0-9]+$", username)) {
            registerUsernameErrorTV.setText(R.string.auth_username_error_regex);
            flag = false;
        }

        // Password
        if (password.length() < 8 || password.length() > 50) {
            registerPasswordErrorTV.setText(R.string.auth_password_error_length);
            flag = false;
        } else if (!Pattern.matches("^[a-zA-Z0-9]+$", password)) {
            registerPasswordErrorTV.setText(R.string.auth_password_error_regex);
            flag = false;
        }

        // Email
        if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
            registerEmailErrorTV.setText(R.string.auth_email_error_regex);
            flag = false;
        }

        // Phone
        if (!phone.startsWith("0")) {
            registerPhoneErrorTV.setText(R.string.auth_phone_error_start);
            flag = false;
        } else if (phone.length() < 10 || phone.length() > 13) {
            registerPhoneErrorTV.setText(R.string.auth_phone_error_length);
            flag = false;
        }

        userManager.close();
        return flag;
    }

    private void clearError() {
        registerUsernameErrorTV.setText("");
        registerPasswordErrorTV.setText("");
        registerEmailErrorTV.setText("");
        registerPhoneErrorTV.setText("");
        registerAddressErrorTV.setText("");
    }
}