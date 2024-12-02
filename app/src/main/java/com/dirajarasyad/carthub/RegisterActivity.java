package com.dirajarasyad.carthub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dirajarasyad.carthub.manager.ImageManager;
import com.dirajarasyad.carthub.manager.PickerManager;
import com.dirajarasyad.carthub.manager.SessionManager;
import com.dirajarasyad.carthub.database.manager.DBUserManager;
import com.dirajarasyad.carthub.model.User;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private TextView registerUsernameTV, registerPasswordTV, registerEmailTV, registerPhoneTV, registerAddressTV, registerImageTV, registerUsernameErrorTV, registerPasswordErrorTV, registerEmailErrorTV, registerPhoneErrorTV, registerAddressErrorTV;
    private EditText registerUsernameET, registerPasswordET, registerEmailET, registerPhoneET, registerAddressET;
    private ImageView registerImageIV;
    private Button registerSubmitBtn;
    private ActivityResultLauncher<PickVisualMediaRequest> pickLauncher;
    private Uri uri;

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
        registerImageIV.setOnClickListener(this::onClick);
    }

    private void initial() {
        registerUsernameTV = findViewById(R.id.registerUsernameTV);
        registerPasswordTV = findViewById(R.id.registerPasswordTV);
        registerEmailTV = findViewById(R.id.registerEmailTV);
        registerPhoneTV = findViewById(R.id.registerPhoneTV);
        registerAddressTV = findViewById(R.id.registerAddressTV);
        registerImageTV = findViewById(R.id.registerImageTV);

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

        registerImageIV = findViewById(R.id.registerImageIV);
        registerImageIV.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_person_100));
        registerSubmitBtn = findViewById(R.id.registerSubmitBtn);

        pickLauncher = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(),
            uri -> {
                this.uri = uri;
                if (uri != null) {
                    registerImageIV.setImageURI(uri);
                }
            }
        );
    }

    private void onClick(View view) {
        if (view == registerImageIV) {
            PickerManager pickerManager = new PickerManager(pickLauncher);
            pickerManager.pickImageOnly();
        } else if (view == registerSubmitBtn) {
            String username = registerUsernameET.getText().toString();
            String password = registerPasswordET.getText().toString();
            String email = registerEmailET.getText().toString();
            String phone = registerPhoneET.getText().toString();
            String address = registerAddressET.getText().toString();
            Drawable image;

            if (uri == null) {
                image = AppCompatResources.getDrawable(this, R.drawable.baseline_person_100);
            } else {
                image = new ImageManager(this.uri, this).getImage();
            }

            if (this.validateInput(username, password, email, phone)) {
                DBUserManager userManager = new DBUserManager(this);
                userManager.open();
                userManager.addUser(username, password, email, phone, address, image, User.Role.NORMAL);
                User newUser = userManager.getUserByUsername(username);
                userManager.close();

                SessionManager sessionManager = new SessionManager(this);
                sessionManager.createSession(newUser);

                Intent homepage = new Intent(this, HomeActivity.class);
                startActivity(homepage);
                finishAffinity();
            }
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