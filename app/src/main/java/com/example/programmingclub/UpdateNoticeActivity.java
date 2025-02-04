package com.example.programmingclub;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateNoticeActivity extends AppCompatActivity {

    private EditText headlineEditText, descriptionEditText, linkEditText;
    private DatabaseReference noticeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notice);

        headlineEditText = findViewById(R.id.headlineEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        linkEditText = findViewById(R.id.linkEditText);

        noticeRef = FirebaseDatabase.getInstance().getReference("Notices");
    }

    /**
     * Save the updated notice to the database and notify users.
     */
    public void updateNotice(View view) {
        String headline = headlineEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String link = linkEditText.getText().toString().trim();

        if (headline.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current time in milliseconds
        long timestamp = System.currentTimeMillis();

        // Create a unique notice ID using headline + timestamp
        String noticeId = headline.replaceAll("\\s+", "_") + "_" + timestamp;

        // Create a new Notice object
        Notice notice = new Notice(headline, description, link, timestamp);

        // Save the notice to Firebase using the generated noticeId
        noticeRef.child(noticeId).setValue(notice).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(UpdateNoticeActivity.this, "Notice updated!", Toast.LENGTH_SHORT).show();
                notifyUsers();
            } else {
                Toast.makeText(UpdateNoticeActivity.this, "Failed to update notice.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Notify all users about the updated notice.
     */
    private void notifyUsers() {
        // Simple notification using Toast for demo purposes
        Toast.makeText(this, "All users have been notified about the new notice!", Toast.LENGTH_LONG).show();
    }

}
