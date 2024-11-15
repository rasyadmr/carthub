package com.dirajarasyad.carthub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    TextView registerUsernameTV, registerPasswordTV, registerEmailTV, registerPhoneTV, registerAddressTV, registerUsernameErrorTV, registerPasswordErrorTV, registerEmailErrorTV, registerPhoneErrorTV, registerAddressErrorTV;
    EditText registerUsernameET, registerPasswordET, registerEmailET, registerPhoneET, registerAddressET;
    Button registerSubmitBtn;

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
}