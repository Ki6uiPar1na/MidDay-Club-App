package com.example.programmingclub;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class codeforces_profile extends AppCompatActivity {

    private TextView userName, userHandle, userRating, userMaxRating, userRank, userFriends;
    private ImageView userTitlePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // Initialize views
        userName = findViewById(R.id.userName);
        userHandle = findViewById(R.id.userHandle);
        userRating = findViewById(R.id.userRating);
        userMaxRating = findViewById(R.id.userMaxRating);
        userRank = findViewById(R.id.userRank);
        userFriends = findViewById(R.id.userFriends);
        userTitlePhoto = findViewById(R.id.userTitlePhoto);

        // Fetch user info
        fetchUserInfo("Ki6ui_Par1na");
    }

    private void fetchUserInfo(String handle) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://codeforces.com/api/user.info?handles=" + handle)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle network error
                runOnUiThread(() -> {
                    // Show error message
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // Handle unsuccessful response
                    return;
                }

                try {
                    String jsonData = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray resultArray = jsonObject.getJSONArray("result");
                    JSONObject userInfo = resultArray.getJSONObject(0);

                    runOnUiThread(() -> {
                        // Set user information
                        String fullName = userInfo.optString("firstName", "") + " " +
                                userInfo.optString("lastName", "");
                        userName.setText(fullName.trim());
                        userHandle.setText("@" + userInfo.optString("handle", ""));
                        userRating.setText(String.valueOf(userInfo.optInt("rating", 0)));
                        userMaxRating.setText(String.valueOf(userInfo.optInt("maxRating", 0)));
                        userRank.setText(userInfo.optString("rank", "N/A"));
                        userFriends.setText(String.valueOf(userInfo.optInt("friendOfCount", 0)));

                        // Load title photo
                        Glide.with(codeforces_profile.this)
                                .load(userInfo.optString("titlePhoto", ""))
                                .into(userTitlePhoto);
                    });
                } catch (Exception e) {
                    // Handle JSON parsing error
                }
            }
        });
    }
}