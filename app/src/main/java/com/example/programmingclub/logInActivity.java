package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.android.material.snackbar.Snackbar;

public class logInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password;
    private Button loginButton;
    private TextView signUpText, forgotPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        this.setTitle("Log In");

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Access all the IDs in the login page
        username = findViewById(R.id.username);
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
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Snackbar.make(loginButton, "Please enter email and password", Snackbar.LENGTH_SHORT).show();
            getCurrentFocus();
            return;
        }

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        // Navigate to the main activity after login
                        Intent intent = new Intent(logInActivity.this, profile.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMessage = "Login failed";
                        if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                            errorMessage = "User does not exist";
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            errorMessage = "Invalid password";
                        }
                        Snackbar.make(loginButton, errorMessage, Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
