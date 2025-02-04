package com.example.programmingclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ContestSiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_site);

        CardView codeforcesCard = findViewById(R.id.codeforcesCard);
        CardView codechefCard = findViewById(R.id.codechefCard);
        CardView leetcodeCard = findViewById(R.id.leetcodeCard);

        codeforcesCard.setOnClickListener(v -> openUrl("https://codeforces.com/"));
        codechefCard.setOnClickListener(v -> openUrl("https://www.codechef.com/"));
        leetcodeCard.setOnClickListener(v -> openUrl("https://leetcode.com/"));
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
