package com.example.programmingclub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private List<Notice> noticeList;
    private DatabaseReference noticeRef;
    private BottomNavigationView bottomNavigationView; // Fixed: Declared bottomNavigationView here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView = findViewById(R.id.noticeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noticeList = new ArrayList<>();
        adapter = new NoticeAdapter(this, noticeList);
        recyclerView.setAdapter(adapter);

        // Firebase reference
        noticeRef = FirebaseDatabase.getInstance().getReference("Notices");

        fetchNotices();

        // Bottom Navigation Setup (Fixed Placement)
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                intent = new Intent(NoticeActivity.this, MainActivity.class);
            } else if (itemId == R.id.nav_profile) {
                intent = new Intent(NoticeActivity.this, ProfileActivity.class);
            } else if (itemId == R.id.nav_notice) {
                intent = new Intent(NoticeActivity.this, DisplayNoticeActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void fetchNotices() {
        noticeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noticeList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Notice notice = data.getValue(Notice.class);
                    if (notice != null) {
                        noticeList.add(notice);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(NoticeActivity.this, "Failed to load notices", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }
}
