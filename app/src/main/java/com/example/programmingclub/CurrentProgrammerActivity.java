package com.example.programmingclub;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CurrentProgrammerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgrammerAdapter adapter;
    private List<Programmer> programmerList;
    private List<Programmer> programmerListFiltered;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private SearchView searchView;
    private RecyclerView sessionRecyclerView;
    private SessionAdapter sessionAdapter;
    private List<String> sessionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_programmer);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");

        // Initialize RecyclerView and Programmer List
        recyclerView = findViewById(R.id.programmersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        programmerList = new ArrayList<>();
        programmerListFiltered = new ArrayList<>();
        adapter = new ProgrammerAdapter(programmerListFiltered);
        recyclerView.setAdapter(adapter);

        // Initialize SearchView
        searchView = findViewById(R.id.searchView);

        // Initialize Session RecyclerView
        sessionRecyclerView = findViewById(R.id.sessionRecyclerView);
        sessionRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sessionList = new ArrayList<>();
        sessionAdapter = new SessionAdapter(sessionList, this::filterBySession);
        sessionRecyclerView.setAdapter(sessionAdapter);

        // Fetch all users and populate RecyclerView
        fetchAllProgrammers();

        // Search bar filter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void fetchAllProgrammers() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> sessionSet = new HashSet<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String name = userSnapshot.child("name").getValue(String.class);
                    String session = userSnapshot.child("session").getValue(String.class);

                    if (name != null && session != null) {
                        // Create a new Programmer object and add it to the list
                        Programmer programmer = new Programmer(name, session);
                        programmerList.add(programmer);

                        // Add session to session list for filtering
                        sessionSet.add(session);
                    }
                }

                // Add sessions to the session RecyclerView
                sessionList.addAll(sessionSet);
                sessionAdapter.notifyDataSetChanged();

                // Initially, show all programmers
                programmerListFiltered.addAll(programmerList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CurrentProgrammerActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String query) {
        programmerListFiltered.clear();

        if (query.isEmpty()) {
            programmerListFiltered.addAll(programmerList);
        } else {
            for (Programmer programmer : programmerList) {
                if (programmer.getName().toLowerCase().contains(query.toLowerCase()) ||
                        programmer.getSession().toLowerCase().contains(query.toLowerCase())) {
                    programmerListFiltered.add(programmer);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void filterBySession(String session) {
        programmerListFiltered.clear();
        for (Programmer programmer : programmerList) {
            if (programmer.getSession().equals(session)) {
                programmerListFiltered.add(programmer);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
