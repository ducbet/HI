package com.example.tmd.hi_20172.activity.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<History> items;

    public HistoryAdapter(List<History> items) {
        this.items = items;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private TextView tvTreeName;
        private TextView tvTreeId;
        private TextView tvWater;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.text_view_history_time);
            tvTreeName = itemView.findViewById(R.id.text_view_history_tree_name);
            tvTreeId = itemView.findViewById(R.id.text_view_history_tree_id);
            tvWater = itemView.findViewById(R.id.text_view_history_water);
        }

        public void bindData(History history) {
            tvTime.setText(history.getDate().toString());
            tvTreeName.setText(history.getTreeName());
            tvTreeId.setText(String.valueOf(history.getTreeId()));
            tvWater.setText(String.valueOf(history.getWater()));
        }
    }
}
