package com.example.programmingclub;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTeacherActivity extends AppCompatActivity {

    private EditText teacherName, designation, phoneNumber, email, department;  // Added department
    private Button saveTeacherButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        // Initializing Views
        teacherName = findViewById(R.id.teacherName);
        designation = findViewById(R.id.designation);
        phoneNumber = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        department = findViewById(R.id.department);  // Initialize department EditText
        saveTeacherButton = findViewById(R.id.saveTeacherButton);

        // Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");

        saveTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTeacherInfo();
            }
        });
    }

    private void saveTeacherInfo() {
        String name = teacherName.getText().toString().trim();
        String desig = designation.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String dept = department.getText().toString().trim();  // Getting department value

        if (TextUtils.isEmpty(name)) {
            teacherName.setError("Enter Teacher's Name");
            return;
        }

        if (TextUtils.isEmpty(desig)) {
            designation.setError("Enter Designation");
            return;
        }

        if (TextUtils.isEmpty(dept)) {  // Checking if department is empty
            department.setError("Enter Department");
            return;
        }

        // Creating Teacher Object with department
        String Tname = teacherName.getText().toString().trim();
        int randomNumber = (int)(Math.random() * 101);  // Generates a random number between 0 and 100
        String id = Tname + randomNumber;  // Concatenating the name and random number

        Teacher teacher = new Teacher(name, desig, phone, mail, dept);  // Passing department value

        // Storing in Firebase
        if (id != null) {
            databaseReference.child(id).setValue(teacher).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddTeacherActivity.this, "Teacher Info Saved", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(AddTeacherActivity.this, "Failed to Save", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearFields() {
        teacherName.setText("");
        designation.setText("");
        phoneNumber.setText("");
        email.setText("");
        department.setText("");  // Clear department field
    }
}
