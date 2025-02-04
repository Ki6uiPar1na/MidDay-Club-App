package com.example.programmingclub;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllProgrammerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CF_Adapter cfAdapter;
    private List<CF_User> cfUserList;
    private OkHttpClient client;
    private String[] handles = {"JFR_Rahat", "DividedByZero_", "marufhussain745", // Add your handles
            "tamjid_hossen", "ai_nobin", "Sadman_Ishtiak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_programmer);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cfUserList = new ArrayList<>();
        cfAdapter = new CF_Adapter(this, cfUserList);
        recyclerView.setAdapter(cfAdapter);

        // Initialize OkHttpClient
        client = new OkHttpClient();

        // Fetch data from the API for each user handle
        for (String handle : handles) {
            fetchCodeforcesData(handle);
        }

        // Initialize SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return false;
            }
        });
    }

    private void fetchCodeforcesData(String handle) {
        String url = "https://codeforces.com/api/user.info?handles=" + handle;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    runOnUiThread(() -> parseAndDisplayData(jsonData));
                }
            }
        });
    }

    private void parseAndDisplayData(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray resultArray = jsonObject.getJSONArray("result");
            JSONObject userInfo = resultArray.getJSONObject(0);

            // Extract data for the user
            String handle = userInfo.getString("handle");
            int rating = userInfo.optInt("rating", 0); // Default to 0 if no rating
            String country = userInfo.has("country") ? userInfo.getString("country") : "N/A";

            // Add user to the list
            CF_User cfUser = new CF_User(handle, rating, country);
            cfUserList.add(cfUser);

            // Update the RecyclerView
            runOnUiThread(() -> cfAdapter.notifyDataSetChanged());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void filterUsers(String query) {
        List<CF_User> filteredList = new ArrayList<>();
        for (CF_User cfUser : cfUserList) {
            if (cfUser.getHandle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(cfUser);
            }
        }
        cfAdapter.updateCFUserList(filteredList);
    }
}
