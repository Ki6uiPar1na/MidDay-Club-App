package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton club_members, comitee, alumni, contest_era, streak_leaderboard, problem_of_the_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        club_members = findViewById(R.id.club_members);
        comitee = findViewById(R.id.comitee);
        alumni = findViewById(R.id.alumni);
        contest_era = findViewById(R.id.contest_era);
        streak_leaderboard = findViewById(R.id.streak_leaderboard);
        problem_of_the_day = findViewById(R.id.problem_of_the_day);

        // Set click listeners
        club_members.setOnClickListener(this);
        comitee.setOnClickListener(this);
        alumni.setOnClickListener(this);
        contest_era.setOnClickListener(this);
        streak_leaderboard.setOnClickListener(this);
        problem_of_the_day.setOnClickListener(this);  // ✅ Fixed issue here

        // Bottom Navigation Setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                intent = new Intent(MainActivity.this, ProfileActivity.class);
            } else if (itemId == R.id.nav_notice) {  // ✅ Handling settings option
                intent = new Intent(MainActivity.this, DisplayNoticeActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        int viewId = view.getId();

        if (viewId == R.id.club_members) {
            intent = new Intent(this, ShowClubMembers.class);
        } else if (viewId == R.id.problem_of_the_day) {  // ✅ Fixed issue here
            intent = new Intent(this, ProblemOfTheDayActivity.class);
        } else if (viewId == R.id.comitee) {
            intent = new Intent(this, comitee.class);
        } else if (viewId == R.id.alumni) {
            intent = new Intent(this, TeacherListActivity.class);
        } else if (viewId == R.id.contest_era) {
            intent = new Intent(this, ProgrammingOptionsActivity.class);
        } else if (viewId == R.id.streak_leaderboard) {
            intent = new Intent(this, streakLeaderboard.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
