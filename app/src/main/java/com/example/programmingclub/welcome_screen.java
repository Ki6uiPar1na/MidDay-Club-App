package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;

public class welcome_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        //remove title
        getSupportActionBar().hide();

        // Find buttons by ID
        final Button adminLoginButton = findViewById(R.id.admin_login_button);
        final Button memberLoginButton = findViewById(R.id.member_login_button);

        // Set click listeners for buttons
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Admin Login Activity
                Intent intent = new Intent(welcome_screen.this, adminLogin.class);
                startActivity(intent);
            }
        });

        memberLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Member Login Activity
                Intent intent = new Intent(welcome_screen.this, logInActivity.class);
                startActivity(intent);
            }
        });
    }
}