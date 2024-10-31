package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity implements View.OnClickListener {
    // Initialize Firebase Auth
    private FirebaseAuth auth;

    // Find the show all student button
    private Button allStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.setTitle("Profile");

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Get the all student button and set an onClick listener
        allStudent = findViewById(R.id.show_all_student);
        allStudent.setOnClickListener(this);
    }

    // Inflate the menu layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return true; // Indicate the menu is ready to display
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            auth.signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(), logInActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.settings) {
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
            // Start a new activity if needed
            return true;
        } else if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "About selected", Toast.LENGTH_SHORT).show();
            // Start a new activity if needed
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.show_all_student) {
            Intent intent = new Intent(getApplicationContext(), members.class);
            startActivity(intent);
        }
    }
}
