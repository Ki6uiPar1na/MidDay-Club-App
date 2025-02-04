package com.example.programmingclub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private Context context;
    private List<Teacher> teacherList;

    public TeacherAdapter(Context context, List<Teacher> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacher_card, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teacherList.get(position);
        holder.teacherName.setText(teacher.getTeacherName());
        holder.teacherDesignation.setText(teacher.getDesignation());
        holder.teacherEmail.setText(teacher.getEmail());
        holder.teacherPhone.setText(teacher.getPhoneNumber());

        // Set click listener for each card
        holder.itemView.setOnClickListener(v -> showContactDialog(teacher));
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public static class TeacherViewHolder extends RecyclerView.ViewHolder {
        TextView teacherName, teacherDesignation, teacherEmail, teacherPhone;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            teacherName = itemView.findViewById(R.id.teacherName);
            teacherDesignation = itemView.findViewById(R.id.teacherDesignation);
            teacherEmail = itemView.findViewById(R.id.teacherEmail);
            teacherPhone = itemView.findViewById(R.id.teacherPhone);
        }
    }

    // Method to show contact options dialog
    private void showContactDialog(Teacher teacher) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Contact " + teacher.getTeacherName())
                .setItems(new CharSequence[]{"Call", "Email"}, (dialog, which) -> {
                    if (which == 0) {
                        // Make a phone call
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + teacher.getPhoneNumber()));
                        context.startActivity(callIntent);
                    } else if (which == 1) {
                        // Send an email
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:" + teacher.getEmail()));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
                        context.startActivity(emailIntent);
                    }
                });
        builder.show();
    }
}
