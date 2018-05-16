package com.example.tmd.hi_20172.activity.map;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;

import java.util.List;

/**
 * Created by thanh.tv on 8/7/2017.
 */
public class HashTagsAdapter extends RecyclerView.Adapter<HashTagsAdapter.ViewHolder> {
    private List<String> mList;
    private Context context;

    public HashTagsAdapter(Context context, List<String> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_hashtag, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String string = mList.get(position);
        holder.blindData(string);
    }


    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtHashTag;
        private String string;

        public ViewHolder(View itemView) {
            super(itemView);
            txtHashTag = itemView.findViewById(R.id.text_view_hashtag);
            txtHashTag.setOnClickListener(this);
        }

        public void blindData(String string) {
            this.string = string;
            txtHashTag.setText("#" + string.replace(' ', '_'));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.text_view_hashtag:
                    mList.remove(string);
                    notifyDataSetChanged();
                    ((MapsActivity) context).filterTree();
                    break;
            }
        }
    }
}
