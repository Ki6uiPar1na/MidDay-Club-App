package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout; // Import LinearLayout

public class ProgrammingOptionsActivity extends AppCompatActivity {

    LinearLayout contestSite, currentProgrammer, allProgrammer; // Change CardView to LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming_options);

        // Initialize UI elements (Use LinearLayout instead of CardView)
        contestSite = findViewById(R.id.contestSiteCard);
        currentProgrammer = findViewById(R.id.currentProgrammerCard);
        allProgrammer = findViewById(R.id.allProgrammerCard);

        // Click Listeners
        contestSite.setOnClickListener(v -> startActivity(new Intent(this, ContestSiteActivity.class)));
        currentProgrammer.setOnClickListener(v -> startActivity(new Intent(this, CurrentProgrammerActivity.class)));
        allProgrammer.setOnClickListener(v -> startActivity(new Intent(this, AllProgrammerActivity.class)));
    }
}
