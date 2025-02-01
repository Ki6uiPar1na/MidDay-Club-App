package com.example.programmingclub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class CF_stats extends AppCompatActivity {

    private LinearLayout userContainer;
    private OkHttpClient client;
    private String[] handles = {"JFR_Rahat", "DividedByZero_", "marufhussain745",
            "tamjid_hossen", "ai_nobin", "Sadman_Ishtiak", "Z3R0_IQ", "Omayer_", "ShazidMashrafi",
            "shafinkun", "prantic", "Alamin917", "avishek_sarkar", "hussain_anjum", "arman_12", "sharif100110",
            "Ki6ui-Par1na", "the_lazydot", "SakibFahad", "hamidulrifat", "ishtiyak911", "Nightfurybooy",
            "sanzidtonu", "mdshakibsami", "Rakin_JKKNIU", "mohiulhaquerabby1432", "Alif_", "rahatgreyrat",
            "Zasmin_Zarin", "_Jahid", "Irteja", "mdshihab", "supty_zaman", "Evan_Shareef", "sakib3201",
            "RafiulIslamRafi", "cseian_arman", "one_aNd_zer0", "AhsanShantanur", "biplobdas", "0rebaba",
            "Fayjur_Rafi", "iammahmudul", "shariful19102036", "afridashams", "mehedi_hasan151061",
            "nayeemtalukder1", "19_31", "Ahasan_ullah", "MFahim12", "aninditamumu4928", "Jim202", "MG_Rahul",
            "liton14", "jahidhasansabbir", "Reyajul_islam", "Isratzaman1234", "somon_bappi"};
    // List of handles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cf_stats);

        // Initialize userContainer
        userContainer = findViewById(R.id.userContainer);

        // Initialize OkHttpClient
        client = new OkHttpClient();

        // Fetch data from the API for each user handle
        for (String handle : handles) {
            fetchCodeforcesData(handle);
        }
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

            // Create CardView to display the user data
            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(8, 8, 8, 8); // Optional margin for spacing
            cardView.setLayoutParams(cardParams);
            cardView.setRadius(8f);
            cardView.setCardElevation(6f);
            cardView.setUseCompatPadding(true);
            cardView.setContentPadding(16, 16, 16, 16);

            // Create TextView for user data
            TextView userTextView = new TextView(this);
            userTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            userTextView.setText("Handle: " + handle + "\n" +
                    "Rating: " + rating + "\n" +
                    "Country: " + country);
            userTextView.setTextSize(16f);

            // Add TextView to CardView, then add CardView to the container
            cardView.addView(userTextView);
            userContainer.addView(cardView);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
