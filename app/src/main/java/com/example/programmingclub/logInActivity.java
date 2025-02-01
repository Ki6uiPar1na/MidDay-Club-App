package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class logInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText registrationNumber, password;
    private Button loginButton;
    private TextView signUpText, forgotPassword;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        this.setTitle("Log In");

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Access all the IDs in the login page
        registrationNumber = findViewById(R.id.username); // Change ID to match registration number
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpText);
        forgotPassword = findViewById(R.id.forgotPassword);

        // Set onClick listeners
        loginButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signUpText) {
            // Start the SignUpActivity
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.loginButton) {
            // Perform login action
            performLogin();
        }
    }

    private void performLogin() {
        String regNo = registrationNumber.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (regNo.isEmpty() || pass.isEmpty()) {
            Snackbar.make(loginButton, "Please enter registration number and password", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Query Firebase Realtime Database for registration number
        databaseReference.orderByChild("registrationNumber").equalTo(regNo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                // Get user details
                                String dbPassword = userSnapshot.child("password").getValue(String.class);

                                if (dbPassword != null && dbPassword.equals(pass)) {
                                    // Login successful
                                    Intent intent = new Intent(logInActivity.this, MainActivity.class);
                                    intent.putExtra("userName", userSnapshot.child("name").getValue(String.class));
                                    intent.putExtra("userEmail", userSnapshot.child("email").getValue(String.class));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Snackbar.make(loginButton, "Invalid password", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Snackbar.make(loginButton, "Registration number not found", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Snackbar.make(loginButton, "Database error occurred", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
