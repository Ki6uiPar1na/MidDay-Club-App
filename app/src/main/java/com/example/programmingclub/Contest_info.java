package com.example.programmingclub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Contest_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_info);

        // Find the TextView and Button from the layout
        TextView dataTextView = findViewById(R.id.data);
        Button fetchButton = findViewById(R.id.fetch_contest_data);

        // Initialize the Volley RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://codeforces.com/api/user.rating?handle=Ki6ui_Par1na";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                    try {
                        // Parse the JSON response
                        JSONArray contests = response.getJSONArray("result");
                        StringBuilder dataBuilder = new StringBuilder();

                        // Iterate over each contest in the "result" array
                        for (int i = 0; i < contests.length(); i++) {
                            JSONObject contest = contests.getJSONObject(i);
                            String contestName = contest.getString("contestName");
                            int rank = contest.getInt("rank");
                            int oldRating = contest.getInt("oldRating");
                            int newRating = contest.getInt("newRating");

                            // Append contest details to the StringBuilder
                            dataBuilder.append("Contest Name: ").append(contestName).append("\n")
                                    .append("Rank: ").append(rank).append("\n")
                                    .append("Old Rating: ").append(oldRating).append("\n")
                                    .append("New Rating: ").append(newRating).append("\n\n");
                        }

                        // Display the parsed data in the TextView
                        dataTextView.setText(dataBuilder.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Contest_info.this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    Toast.makeText(Contest_info.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                });

                // Add the request to the Volley RequestQueue
                queue.add(request);
            }
        });
    }
}
