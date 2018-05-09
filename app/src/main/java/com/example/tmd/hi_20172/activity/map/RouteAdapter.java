package com.example.tmd.hi_20172.activity.map;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.activity.TreeDetail;
import com.example.tmd.hi_20172.model.StopOver;
import com.example.tmd.hi_20172.model.Tree;
import com.example.tmd.hi_20172.model.Water;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.tmd.hi_20172.activity.map.MapsActivity.REQUEST_TREE_DETAIL_ACT;
import static com.example.tmd.hi_20172.activity.map.MapsActivity.TREE;

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
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_view_stop_over_name);
            cardView = itemView.findViewById(R.id.card_view_item_list);
            itemView.findViewById(R.id.image_view_remove_stopover).setOnClickListener(this);
            itemView.findViewById(R.id.image_view_show_detail).setOnClickListener(this);
        }

        public void blindData(StopOver stopOver) {
            mStopOver = stopOver;
            txtName.setText(stopOver.getName());
            if (stopOver instanceof Water) {
                cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorBlue));
            } else {
                if (((Tree) stopOver).getStatus() == Tree.RED) {
                    cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorRed));
                } else if (((Tree) stopOver).getStatus() == Tree.YELLOW) {
                    cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                } else if (((Tree) stopOver).getStatus() == Tree.GREEN) {
                    cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                }
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_view_remove_stopover:
                    mStopOver.setChoose(!mStopOver.isChoose());
                    ((MapsActivity) context).updateTour(mStopOver);
                    break;
                case R.id.image_view_show_detail:
                    Intent intent = new Intent(context, TreeDetail.class);
                    intent.putExtra(TREE, mStopOver);
                    ((MapsActivity) context).startActivityForResult(intent, REQUEST_TREE_DETAIL_ACT);
                    break;
            }
        }
    }
}
