package com.example.programmingclub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProgrammerAdapter extends RecyclerView.Adapter<ProgrammerAdapter.ProgrammerViewHolder> {

    private List<Programmer> programmerList;

    public ProgrammerAdapter(List<Programmer> programmerList) {
        this.programmerList = programmerList;
    }

    @Override
    public ProgrammerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.programmer_item, parent, false);
        return new ProgrammerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgrammerViewHolder holder, int position) {
        Programmer programmer = programmerList.get(position);
        holder.nameTextView.setText("Name: " + programmer.getName());
        holder.sessionTextView.setText("Session: " + programmer.getSession());
    }

    @Override
    public int getItemCount() {
        return programmerList.size();
    }

    public static class ProgrammerViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView sessionTextView;

        public ProgrammerViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.programmerName);
            sessionTextView = itemView.findViewById(R.id.programmerSession);
        }
    }
}
