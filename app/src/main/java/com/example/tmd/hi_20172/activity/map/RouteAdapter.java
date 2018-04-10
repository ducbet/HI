package com.example.tmd.hi_20172.activity.map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    public RouteAdapter(Context context, List<StopOver> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    public void updateList() {
        notifyDataSetChanged();
        ((MapsActivity) context).getTxtNumberStopover().setText(mList.size() + " điểm dừng");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private StopOver mStopOver;
        private TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_view_stop_over_name);
            itemView.findViewById(R.id.image_view_remove_stopover).setOnClickListener(this);
        }

        public void blindData(StopOver stopOver) {
            mStopOver = stopOver;
            txtName.setText(stopOver.getName());
        }

        @Override
        public void onClick(View view) {
            mStopOver.setChoose(!mStopOver.isChoose());
            ((MapsActivity) context).updateTour(mStopOver);
        }
    }
}
