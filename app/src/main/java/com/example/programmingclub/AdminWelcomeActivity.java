package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class AdminWelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    /**
     * Navigate to UpdateMembersActivity.
     */
    public void updateMembers(View view) {
        Intent intent = new Intent(this, UpdateMembersActivity.class);
        startActivity(intent);
    }

    /**
     * Navigate to AddTeacherActivity.
     */
    public void updateTeachers(View view) {
        Intent intent = new Intent(this, AddTeacherActivity.class);
        startActivity(intent);
    }

    /**
     * Navigate to UpdateNoticeActivity.
     */
    public void updateNotice(View view) {
        Intent intent = new Intent(this, UpdateNoticeActivity.class);
        startActivity(intent);
    }

    /**
     * Navigate back to the previous screen.
     */
    public void goBack(View view) {
        finish();
    }
}
