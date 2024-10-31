package com.example.programmingclub;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class members extends AppCompatActivity {
    // Initialize Firebase Auth
    private FirebaseAuth auth;

    //find list view
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_students);
        setTitle("All Students");


    }
}