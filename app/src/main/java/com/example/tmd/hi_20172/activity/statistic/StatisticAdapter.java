package com.example.tmd.hi_20172.activity.statistic;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.activity.map.MapsActivity;
import com.example.tmd.hi_20172.model.Statistic;
import com.example.tmd.hi_20172.model.StopOver;

import java.util.List;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.ViewHolder> {
    private List<Statistic> mList;
    private Context context;

    public StatisticAdapter(Context context, List<Statistic> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_statistic, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Statistic item = mList.get(position);
        holder.blindData(item);
    }


    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtTime;
        private TextView txtMl;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_view_statistic_username);
            txtTime = itemView.findViewById(R.id.text_view_statistic_time);
            txtMl = itemView.findViewById(R.id.text_view_statistic_ml);
        }

        public void blindData(Statistic item) {
            txtName.setText(item.getUsername());
            txtTime.setText(item.getTime());
            txtMl.setText(String.valueOf(item.getMl_water()) + " ml");
        }
    }
}
