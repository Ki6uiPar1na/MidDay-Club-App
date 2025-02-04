package com.example.programmingclub;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName, userRegNumber;
    private LinearLayout dataContainer;
    private Button updateProfileButton;
    private ImageView profileImage;
    private DatabaseReference databaseReference;
    private String regNumber; // Store the actual registration number

    private HashMap<String, EditText> fieldInputs = new HashMap<>(); // Store fields for updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI Components
        userName = findViewById(R.id.userName);
        userRegNumber = findViewById(R.id.userRegNumber);
        dataContainer = findViewById(R.id.dataContainer);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        profileImage = findViewById(R.id.profile_image);

        // Get registration number from Intent
        regNumber = getIntent().getStringExtra("regNumber");

        // Handle null case (default to some valid value)
        if (regNumber == null || regNumber.isEmpty()) {
            Toast.makeText(this, "Registration number not found!", Toast.LENGTH_SHORT).show();
            finish(); // Exit activity if regNumber is missing
            return;
        }

        // Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(regNumber);

        // Fetch and display user info
        loadUserProfile();

        // Update profile button click listener
        updateProfileButton.setOnClickListener(v -> updateUserInfo());
    }

    private void loadUserProfile() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(ProfileActivity.this, "User data not found!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Clear previous views before adding new ones
                dataContainer.removeAllViews();

                String name = snapshot.child("name").getValue(String.class);
                String regNo = snapshot.child("registrationNumber").getValue(String.class);

                if (name != null) userName.setText(name);
                if (regNo != null) userRegNumber.setText("Reg No: " + regNo);

                // Loop through each field in the database
                for (DataSnapshot child : snapshot.getChildren()) {
                    String key = child.getKey();
                    String value = child.getValue(String.class);

                    // Exclude keys that should not be edited
                    if (key != null && !key.equals("name") && !key.equals("registrationNumber") && value != null) {
                        addDynamicField(key, value);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDynamicField(String key, String value) {
        TextView label = new TextView(this);
        label.setText(formatKey(key));
        label.setTextSize(16);
        label.setTextColor(getResources().getColor(android.R.color.black));
        dataContainer.addView(label);

        EditText inputField = new EditText(this);
        inputField.setText(value);
        inputField.setTextSize(16);
        inputField.setPadding(10, 10, 10, 10);
        inputField.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));

        dataContainer.addView(inputField);
        fieldInputs.put(key, inputField);
    }

    private void updateUserInfo() {
        Map<String, Object> updates = new HashMap<>();

        for (Map.Entry<String, EditText> entry : fieldInputs.entrySet()) {
            String key = entry.getKey();
            String newValue = entry.getValue().getText().toString().trim();
            if (!TextUtils.isEmpty(newValue)) {
                updates.put(key, newValue);
            }
        }

        if (updates.isEmpty()) {
            Toast.makeText(this, "No changes to update!", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.updateChildren(updates)
                .addOnSuccessListener(unused -> Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show());
    }

    private String formatKey(String key) {
        return key.replace("Handle", " Handle").replace("email", "Email").replace("session", "Session");
    }
}
