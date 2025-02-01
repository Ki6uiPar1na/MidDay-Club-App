package com.example.programmingclub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programmingclub.R;
import com.example.programmingclub.logInActivity;

import java.io.ByteArrayOutputStream;

public class test extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etUsername, etEmail, etPassword;
    private ImageView ivProfileImage;
    private Button btnUploadImage, btnSignup;
    private Bitmap profileBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnSignup = findViewById(R.id.btnSignup);

        btnUploadImage.setOnClickListener(view -> selectImage());

        btnSignup.setOnClickListener(view -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (profileBitmap == null || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields and upload an image", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert image to Base64
            String imageHash = convertImageToBase64(profileBitmap);

            // Send data to a new activity
            Intent intent = new Intent(test.this, logInActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            intent.putExtra("imageHash", imageHash);
            startActivity(intent);
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                profileBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivProfileImage.setImageBitmap(profileBitmap);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String convertImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
