package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, email, session, registrationNumber, password;
    private Button signUpButton;
    private TextView logInText;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        this.setTitle("Sign Up");

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        //initialize database reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        // Access all the IDs in the signup page
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);  // Ensure R.id.email exists in the XML layout
        session = findViewById(R.id.session);  // Ensure R.id.session exists in the XML layout
        registrationNumber = findViewById(R.id.registrationNumber);  // Ensure R.id.registrationNumber exists in the XML layout
        password = findViewById(R.id.password);
        signUpButton = findViewById(R.id.signUpButton);
        logInText = findViewById(R.id.logInText);

        // Set onClick listeners
        signUpButton.setOnClickListener(this);
        logInText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logInText) {
            Intent intent = new Intent(getApplicationContext(), logInActivity.class);
            startActivity(intent); // Start the logInActivity
        } else if (view.getId() == R.id.signUpButton) {
            userRegister();
        }
    }

    private void userRegister() {
        // Get user inputs
        String nameInput = name.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String sessionInput = session.getText().toString().trim();
        String regNumberInput = registrationNumber.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        // Basic validation for input fields
        if (TextUtils.isEmpty(nameInput) || TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(sessionInput) || TextUtils.isEmpty(regNumberInput) || TextUtils.isEmpty(passwordInput)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordInput.length() < 4) {
            Toast.makeText(this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        //if registration number already present then show a message
        

        //send data to database
        storeUserData userData = new storeUserData(nameInput, emailInput, passwordInput, regNumberInput, sessionInput);
        databaseReference.child(regNumberInput).setValue(userData);

        // Create a new user with Firebase Authentication
        auth.createUserWithEmailAndPassword(emailInput, passwordInput)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, logInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Log the error message
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            Toast.makeText(SignUpActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
