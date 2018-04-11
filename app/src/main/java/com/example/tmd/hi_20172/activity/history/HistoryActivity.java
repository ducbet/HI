package com.example.tmd.hi_20172.activity.history;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.History;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    HistoryAdapter mHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = findViewById(R.id.recycler_view_history);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<History> items = new ArrayList<>();
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));
        items.add(new History(new Date(), "Hoa hồng", 3, 200));

        mHistoryAdapter = new HistoryAdapter(items);
        mRecyclerView.setAdapter(mHistoryAdapter);

    }
}
