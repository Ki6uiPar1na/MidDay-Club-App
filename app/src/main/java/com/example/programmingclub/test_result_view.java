package com.example.programmingclub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programmingclub.R;

public class test_result_view extends AppCompatActivity {

    private TextView tvUsername, tvEmail;
    private ImageView ivProfileImageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_view);

        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        ivProfileImageDisplay = findViewById(R.id.ivProfileImageDisplay);

        // Retrieve data from intent
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String imageHash = getIntent().getStringExtra("imageHash");

        tvUsername.setText("Username: " + username);
        tvEmail.setText("Email: " + email);

        // Decode Base64 image
        byte[] imageBytes = Base64.decode(imageHash, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        ivProfileImageDisplay.setImageBitmap(bitmap);
    }
}
