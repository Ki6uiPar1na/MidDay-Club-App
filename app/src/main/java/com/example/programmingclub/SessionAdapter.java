package com.example.programmingclub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {

    private List<String> sessionList;
    private OnSessionClickListener onSessionClickListener;

    public SessionAdapter(List<String> sessionList, OnSessionClickListener onSessionClickListener) {
        this.sessionList = sessionList;
        this.onSessionClickListener = onSessionClickListener;
    }

    @Override
    public SessionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_button_item, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionViewHolder holder, int position) {
        String session = sessionList.get(position);
        holder.sessionButton.setText(session);
        holder.sessionButton.setOnClickListener(v -> onSessionClickListener.onSessionClick(session));
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public static class SessionViewHolder extends RecyclerView.ViewHolder {

        Button sessionButton;

        public SessionViewHolder(View itemView) {
            super(itemView);
            sessionButton = itemView.findViewById(R.id.sessionButton);
        }
    }

    public interface OnSessionClickListener {
        void onSessionClick(String session);
    }
}
