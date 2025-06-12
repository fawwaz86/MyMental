package com.example.mymental;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // XML with username/password fields

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(v -> {
            // You can add real login logic here
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // prevent going back to login
        });
    }
}
