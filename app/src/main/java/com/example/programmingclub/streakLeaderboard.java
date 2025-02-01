package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class streakLeaderboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streak_leaderboard);
        setTitle("Streak Leaderboard");

        Button button = findViewById(R.id.hit);
        button.setOnClickListener(view -> {
            //if click then go to Contest_info.java class
            Intent intent = new Intent(this, Contest_info.class);
            startActivity(intent);
            // Handle the button click here
        });

    }
}