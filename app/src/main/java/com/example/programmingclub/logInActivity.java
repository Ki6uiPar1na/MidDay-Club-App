package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class logInActivity extends AppCompatActivity {

    private EditText regNumberInput, passwordInput;
    private Button loginButton;
    private TextView signUpText;
    private DatabaseReference databaseReference;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        getSupportActionBar().hide();

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI Components
        regNumberInput = findViewById(R.id.registrationNumber);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpText);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        loginButton.setOnClickListener(v -> loginUser());

        // Redirect to Sign-Up Page when clicking "Don't have an account? Sign Up"
        signUpText.setOnClickListener(v -> {
            startActivity(new Intent(logInActivity.this, SignUpActivity.class));
            finish();
        });

        // Handle navigation clicks
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_sign_up) {
                    startActivity(new Intent(logInActivity.this, SignUpActivity.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    private void loginUser() {
        String regNumber = regNumberInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(regNumber) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to the specific registration number
        DatabaseReference userRef = databaseReference.child(regNumber);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve the stored password
                    String storedPassword = snapshot.child("password").getValue(String.class);

                    if (storedPassword != null && storedPassword.equals(password)) {
                        Toast.makeText(logInActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                        // Navigate to ProfileActivity
                        Intent intent = new Intent(logInActivity.this, MainActivity.class);
                        intent.putExtra("regNumber", regNumber); // Pass user reg number
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(logInActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(logInActivity.this, "Registration Number Not Found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(logInActivity.this, "Database Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
