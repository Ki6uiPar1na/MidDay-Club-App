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
        getSupportActionBar().hide();
    }

    public void updateMembers(View view) {
        Intent intent = new Intent(this, UpdateMembersActivity.class);
        startActivity(intent);
    }

    public void updateTeachers(View view) {
//        Intent intent = new Intent(this, UpdateTeachersActivity.class);
//        startActivity(intent);
    }

    public void updateExecutives(View view) {
//        Intent intent = new Intent(this, UpdateExecutivesActivity.class);
//        startActivity(intent);
    }
}
