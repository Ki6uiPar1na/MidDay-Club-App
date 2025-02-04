package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, session, registrationNumber, password;
    private EditText codeforcesHandle, codechefHandle, leetcodeHandle;
    private Button signUpButton;
    private TextView logInText;
    private TextView login;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().hide();

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI components
        initializeViews();
    }

    private void initializeViews() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        session = findViewById(R.id.session);
        registrationNumber = findViewById(R.id.registrationNumber);
        password = findViewById(R.id.password);
        codeforcesHandle = findViewById(R.id.codeforcesHandle);
        codechefHandle = findViewById(R.id.codechefHandle);
        leetcodeHandle = findViewById(R.id.leetcodeHandle);

        signUpButton = findViewById(R.id.signUpButton);
        logInText = findViewById(R.id.logInText);

        signUpButton.setOnClickListener(v -> registerUser());
        logInText.setOnClickListener(v -> startActivity(new Intent(this, logInActivity.class)));
    }

    private void registerUser() {
        String nameStr = name.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        String sessionStr = session.getText().toString().trim();
        String regNumStr = registrationNumber.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        String cfHandle = codeforcesHandle.getText().toString().trim();
        String ccHandle = codechefHandle.getText().toString().trim();
        String lcHandle = leetcodeHandle.getText().toString().trim();

        if (!validateInputs(nameStr, emailStr, sessionStr, regNumStr, passwordStr)) {
            return;
        }

        createFirebaseUser(nameStr, emailStr, sessionStr, regNumStr, passwordStr, cfHandle, ccHandle, lcHandle);
    }

    private boolean validateInputs(String name, String email, String session, String regNum, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(session)
                || TextUtils.isEmpty(regNum) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createFirebaseUser(String name, String email, String session, String regNumber,
                                    String password, String cfHandle, String ccHandle, String lcHandle) {

        // Create a user in Firebase Realtime Database (Instead of Firebase Authentication)
        storeUserData userData = new storeUserData(name, email, session, regNumber, cfHandle, ccHandle, lcHandle, password);

        databaseReference.child(regNumber).setValue(userData)
                .addOnCompleteListener(dbTask -> {
                    if (dbTask.isSuccessful()) {
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, logInActivity.class));
                        finish();
                    } else {
                        showErrorToast("Database Error: " + dbTask.getException().getMessage());
                    }
                });
    }



    private void showErrorToast(String message) {
        Log.e("SignUpActivity", message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
