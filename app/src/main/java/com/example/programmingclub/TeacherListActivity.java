package com.example.programmingclub;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class TeacherListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TeacherAdapter adapter;
    private List<Teacher> teacherList, filteredList;
    private DatabaseReference teacherRef;
    private EditText searchTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        recyclerView = findViewById(R.id.teacherRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchTeacher = findViewById(R.id.searchTeacher);

        teacherList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new TeacherAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);

        // Firebase reference
        teacherRef = FirebaseDatabase.getInstance().getReference("Teachers");

        fetchTeachers();

        // Implement search functionality
        searchTeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTeachers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchTeachers() {
        teacherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                teacherList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Teacher teacher = data.getValue(Teacher.class);
                    if (teacher != null) {
                        teacherList.add(teacher);
                    }
                }
                filteredList.clear();
                filteredList.addAll(teacherList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherListActivity.this, "Failed to load teachers", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", error.getMessage());
            }
        });
    }

    private void filterTeachers(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(teacherList);
        } else {
            for (Teacher teacher : teacherList) {
                if (teacher.getTeacherName().toLowerCase().contains(query.toLowerCase()) ||
                        teacher.getDesignation().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(teacher);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
