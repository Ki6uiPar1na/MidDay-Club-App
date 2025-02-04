package com.example.programmingclub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProblemOfTheDayActivity extends AppCompatActivity {
    private WebView problemStatementWebView;
    private Button solvedButton, postButton;
    private EditText discussionInput;
    private RecyclerView discussionRecyclerView;
    private DiscussionAdapter discussionAdapter;
    private List<Discussion> discussionList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mUserDatabase, mDiscussionDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_of_the_day);
        getSupportActionBar().hide();

        problemStatementWebView = findViewById(R.id.problemStatementWebView);
        solvedButton = findViewById(R.id.solvedButton);
        discussionInput = findViewById(R.id.discussionInput);
        postButton = findViewById(R.id.postCommentButton);
        discussionRecyclerView = findViewById(R.id.discussionRecyclerView);

        mDatabase = FirebaseDatabase.getInstance();
        mUserDatabase = mDatabase.getReference("ProblemSolvers");
        mDiscussionDatabase = mDatabase.getReference("ProblemDiscussions");

        discussionList = new ArrayList<>();
        discussionAdapter = new DiscussionAdapter(discussionList);
        discussionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        discussionRecyclerView.setAdapter(discussionAdapter);

        problemStatementWebView.getSettings().setJavaScriptEnabled(true);
        problemStatementWebView.setWebViewClient(new WebViewClient());
        problemStatementWebView.setWebChromeClient(new WebChromeClient());

        deleteOldDiscussionsAndFetchProblem(); // Delete old discussions (older than 24 hours) before loading problem
        solvedButton.setOnClickListener(v -> markAsSolved());
        postButton.setOnClickListener(v -> postDiscussion());
        loadDiscussions();
    }

    private void fetchProblemOfTheDay() {
        String url = "https://codeforces.com/api/problemset.recentStatus?count=1"; // Example API (Modify as needed)

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String problemUrl = "https://codeforces.com/problemset/problem/1/A"; // Replace with actual problem link
                        problemStatementWebView.loadUrl(problemUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> error.printStackTrace()
        );

        queue.add(request);
    }

    private void deleteOldDiscussionsAndFetchProblem() {
        long currentTime = System.currentTimeMillis();
        long twentyFourHoursAgo = currentTime - 86400000; // 24 hours in milliseconds

        mDiscussionDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Discussion discussion = data.getValue(Discussion.class);
                    if (discussion != null && discussion.getTimestamp() < twentyFourHoursAgo) {
                        mDiscussionDatabase.child(discussion.getId()).removeValue();
                    }
                }
                fetchProblemOfTheDay(); // Load new problem after deleting old discussions
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void postDiscussion() {
        String discussionText = discussionInput.getText().toString().trim();
        if (!TextUtils.isEmpty(discussionText)) {
            SharedPreferences userPrefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            String user = userPrefs.getString("username", "Anonymous");
            String id = mDiscussionDatabase.push().getKey();
            long timestamp = System.currentTimeMillis();

            Discussion discussion = new Discussion(id, user, discussionText, timestamp);
            mDiscussionDatabase.child(id).setValue(discussion);
            discussionInput.setText("");
        }
    }

    private void loadDiscussions() {
        mDiscussionDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                discussionList.clear();
                long currentTime = System.currentTimeMillis();
                long twentyFourHoursAgo = currentTime - 86400000;

                for (DataSnapshot data : snapshot.getChildren()) {
                    Discussion discussion = data.getValue(Discussion.class);
                    if (discussion != null && discussion.getTimestamp() >= twentyFourHoursAgo) {
                        discussionList.add(discussion);
                    } else if (discussion != null) {
                        // Remove outdated discussions from database
                        mDiscussionDatabase.child(discussion.getId()).removeValue();
                    }
                }
                discussionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void markAsSolved() {
        SharedPreferences userPrefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String user = userPrefs.getString("username", "Anonymous");

        mUserDatabase.child(user).setValue(true);
        solvedButton.setEnabled(false);
        solvedButton.setText("Solved");
    }
}
