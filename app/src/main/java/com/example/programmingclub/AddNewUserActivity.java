package com.example.programmingclub;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewUserActivity extends AppCompatActivity {

    private EditText emailInput, nameInput, passwordInput, registrationNumberInput, sessionInput;
    private Button addButton;
    private DatabaseReference usersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        // Initialize Firebase reference for users
        usersDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Initialize the input fields and button
        emailInput = findViewById(R.id.emailInput);
        nameInput = findViewById(R.id.nameInput);
        passwordInput = findViewById(R.id.passwordInput);
        registrationNumberInput = findViewById(R.id.registrationNumberInput);
        sessionInput = findViewById(R.id.sessionInput);
        addButton = findViewById(R.id.addButton);

        // Set up the button click listener to add a new user
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser();
            }
        });
    }

    // Method to add a new user to the Firebase database
    private void addNewUser() {
        // Get the user input values
        String email = emailInput.getText().toString().trim();
        String name = nameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String registrationNumber = registrationNumberInput.getText().toString().trim();
        String session = sessionInput.getText().toString().trim();

        // Validate the input fields
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(registrationNumber) || TextUtils.isEmpty(session)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new User object
        String userId = registrationNumber; // Using registration number as the unique key
        User newUser = new User(email, name, password, registrationNumber, session);

        // Save the new user in Firebase
        usersDatabase.child(userId).setValue(newUser)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddNewUserActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } else {
                        Toast.makeText(AddNewUserActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
