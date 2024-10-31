package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton club_members;
    private ImageButton comitee;
    private ImageButton alumni;
    private ImageButton contest_era;
    private ImageButton streak_leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        club_members = findViewById(R.id.club_members);
        comitee = findViewById(R.id.comitee);
        alumni = findViewById(R.id.alumni);
        contest_era = findViewById(R.id.contest_era);
        streak_leaderboard = findViewById(R.id.streak_leaderboard);

        // Set onClick listeners for the buttons
        club_members.setOnClickListener(this);
        comitee.setOnClickListener(this);
        alumni.setOnClickListener(this);
        contest_era.setOnClickListener(this);
        streak_leaderboard.setOnClickListener(this);

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = null;
                if (item.getItemId() == R.id.nav_home) {
                    // Navigate to home or reload MainActivity if needed
                    return true;
                } else if (item.getItemId() == R.id.nav_profile) {
                    intent = new Intent(MainActivity.this, profile.class);
                } else if (item.getItemId() == R.id.nav_settings) {
                    intent = new Intent(MainActivity.this, profile.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.club_members) {
            intent = new Intent(this, members.class);
        } else if (view.getId() == R.id.comitee) {
            intent = new Intent(this, comitee.class);
        } else if (view.getId() == R.id.alumni) {
            intent = new Intent(this, alumni.class);
        } else if (view.getId() == R.id.contest_era) {
            intent = new Intent(this, contests.class);
        } else if (view.getId() == R.id.streak_leaderboard) {
            intent = new Intent(this, streakLeaderboard.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
