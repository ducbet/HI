package com.example.tmd.hi_20172.activity.statistic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.Statistic;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private StatisticAdapter statisticAdapter;
    private List<Statistic> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        findViewById(R.id.image_statistic).setOnClickListener(this);
        fakeData();
        setUpRecyclerView();

    }

    private void fakeData() {
        list.add(new Statistic("Triệu Minh Đức", "15:00 12/04/2018"));
        list.add(new Statistic("Phạm Gia Cường", "12:30 12/04/2018"));
        list.add(new Statistic("Nguyễn Văn Hãnh", "10:15 12/04/2018"));
        list.add(new Statistic("Giáp Minh Cương", "08:05 12/04/2018"));
        list.add(new Statistic("Triệu Minh Đức", "15:00 12/04/2018"));
        list.add(new Statistic("Phạm Gia Cường", "12:30 12/04/2018"));
        list.add(new Statistic("Nguyễn Văn Hãnh", "10:15 12/04/2018"));
        list.add(new Statistic("Giáp Minh Cương", "08:05 12/04/2018"));
        list.add(new Statistic("Triệu Minh Đức", "15:00 12/04/2018"));
        list.add(new Statistic("Phạm Gia Cường", "12:30 12/04/2018"));
        list.add(new Statistic("Nguyễn Văn Hãnh", "10:15 12/04/2018"));
        list.add(new Statistic("Giáp Minh Cương", "08:05 12/04/2018"));
        list.add(new Statistic("Triệu Minh Đức", "15:00 12/04/2018"));
        list.add(new Statistic("Phạm Gia Cường", "12:30 12/04/2018"));
        list.add(new Statistic("Nguyễn Văn Hãnh", "10:15 12/04/2018"));
        list.add(new Statistic("Giáp Minh Cương", "08:05 12/04/2018"));
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_statistic);
        statisticAdapter = new StatisticAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(statisticAdapter);

    }

    @Override
    public void onClick(View view) {

    }
}
