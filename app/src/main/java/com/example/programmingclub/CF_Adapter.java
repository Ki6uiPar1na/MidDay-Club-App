package com.example.programmingclub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CF_Adapter extends RecyclerView.Adapter<CF_Adapter.CF_UserViewHolder> {

    private List<CF_User> cfUserList;
    private Context context;

    public CF_Adapter(Context context, List<CF_User> cfUserList) {
        this.context = context;
        this.cfUserList = cfUserList;
    }

    @Override
    public CF_UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cf_user, parent, false);
        return new CF_UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CF_UserViewHolder holder, int position) {
        CF_User cfUser = cfUserList.get(position);
        holder.handleTextView.setText(cfUser.getHandle());
        holder.ratingTextView.setText("Rating: " + cfUser.getRating());
        holder.countryTextView.setText("Jatiya Kabi Kazi Nazrul Islam University");
    }

    @Override
    public int getItemCount() {
        return cfUserList.size();
    }

    public void updateCFUserList(List<CF_User> newCFUserList) {
        cfUserList = newCFUserList;
        notifyDataSetChanged();
    }

    class CF_UserViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView handleTextView, ratingTextView, countryTextView;

        public CF_UserViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            handleTextView = itemView.findViewById(R.id.handleTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            countryTextView = itemView.findViewById(R.id.countryTextView);
        }
    }
}
