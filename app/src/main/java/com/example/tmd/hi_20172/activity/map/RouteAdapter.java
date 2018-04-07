package com.example.tmd.hi_20172.activity.map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.StopOver;
import com.example.tmd.hi_20172.model.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    private List<StopOver> mList;
    private Context context;

    public RouteAdapter(Map<String, StopOver> list) {
        mList = new ArrayList<>(list.values());
    }

    public void updateTour(Map<String, StopOver> newList) {
        this.mList.clear();
        mList.addAll(newList.values());
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_stop_over, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StopOver itemFriendSuggest = mList.get(position);
        holder.blindData(itemFriendSuggest);
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_view_stop_over_name);

        }

        public void blindData(StopOver stopOver) {
            txtName.setText(stopOver.getName());
        }
    }
}
