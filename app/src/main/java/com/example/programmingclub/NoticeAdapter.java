package com.example.programmingclub;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    private Context context;
    private List<Notice> noticeList;

    public NoticeAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        Notice notice = noticeList.get(position);

        holder.noticeTitle.setText(notice.getHeadline());
        holder.noticeDescription.setText(notice.getDescription().length() > 50 ?
                notice.getDescription().substring(0, 50) + "..." : notice.getDescription());

        // Convert timestamp to a readable date format
        holder.noticeTimestamp.setText(formatTimestamp(notice.getTimestamp()));

        holder.itemView.setOnClickListener(v -> showFullNoticeDialog(notice));
    }

    /**
     * Convert timestamp (milliseconds) to a human-readable date format.
     */
    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView noticeTitle, noticeTimestamp, noticeDescription;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeTimestamp = itemView.findViewById(R.id.noticeTimestamp);
            noticeDescription = itemView.findViewById(R.id.noticeDescription);
        }
    }

    /**
     * Show a popup with the full notice details.
     */
    private void showFullNoticeDialog(Notice notice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_full_notice, null);
        builder.setView(dialogView);

        TextView fullTitle = dialogView.findViewById(R.id.fullNoticeTitle);
        TextView fullTimestamp = dialogView.findViewById(R.id.fullNoticeTimestamp);
        TextView fullDescription = dialogView.findViewById(R.id.fullNoticeDescription);
        TextView fullLink = dialogView.findViewById(R.id.fullNoticeLink);

        fullTitle.setText(notice.getHeadline());
        fullTimestamp.setText(formatTimestamp(notice.getTimestamp()));
        fullDescription.setText(notice.getDescription());

        if (notice.getLink() != null && !notice.getLink().isEmpty()) {
            fullLink.setText("Read more");
            fullLink.setVisibility(View.VISIBLE);
            fullLink.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notice.getLink()));
                context.startActivity(browserIntent);
            });
        } else {
            fullLink.setVisibility(View.GONE);
        }

        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
