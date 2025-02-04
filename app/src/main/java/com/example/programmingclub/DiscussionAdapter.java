package com.example.programmingclub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {
    private List<Discussion> discussionList;

    public DiscussionAdapter(List<Discussion> discussionList) {
        this.discussionList = (discussionList != null) ? discussionList : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discussion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discussion discussion = discussionList.get(position);
        holder.userName.setText(discussion.getUser() != null ? discussion.getUser() : "Unknown User");
        holder.message.setText(discussion.getMessage() != null ? discussion.getMessage() : "No Message");

        // Format timestamp safely
        if (discussion.getTimestamp() > 0) { // Check if timestamp is valid
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                holder.time.setText(sdf.format(discussion.getTimestamp()));
            } catch (Exception e) {
                holder.time.setText("Invalid Time");
            }
        } else {
            holder.time.setText("Unknown Time");
        }
    }

    @Override
    public int getItemCount() {
        return discussionList.size();
    }

    public void updateDiscussions(List<Discussion> newDiscussions) {
        if (newDiscussions == null) return;
        discussionList.clear();
        discussionList.addAll(newDiscussions);
        notifyItemRangeChanged(0, discussionList.size()); // More efficient than notifyDataSetChanged()
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, message, time;

        public ViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.userName);
            message = view.findViewById(R.id.message);
            time = view.findViewById(R.id.time);
        }
    }
}
