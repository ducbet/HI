package com.example.tmd.hi_20172.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.StopOver;
import com.example.tmd.hi_20172.model.Tree;

import static com.example.tmd.hi_20172.activity.map.MapsActivity.TREE;

public class TreeDetail extends AppCompatActivity implements View.OnClickListener {

    private Tree tree;
    private TextView txtName;
    private ImageView imgChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_detail);

        getControl();
        getTreeId();
    }

    private void getControl() {
        txtName = (TextView) findViewById(R.id.text_view_name);
        imgChoose = (ImageView) findViewById(R.id.image_view_choose);
        imgChoose.setOnClickListener(this);
    }

    public void getTreeId() {
        Intent intent = getIntent();
        tree = intent.getParcelableExtra(TREE);
        txtName.setText(tree.getName());
//        Toast.makeText(this, "" + tree.isChoose(), Toast.LENGTH_SHORT).show();
        if (tree.isChoose()) {
            imgChoose.setImageResource(R.mipmap.ic_checked);
        } else {
            imgChoose.setImageResource(R.mipmap.ic_water);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_choose:
                tree.setChoose(!tree.isChoose());
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", tree);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                break;
        }
    }
}
