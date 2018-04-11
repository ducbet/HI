package com.example.tmd.hi_20172.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.StopOver;
import com.example.tmd.hi_20172.model.Tree;

import static com.example.tmd.hi_20172.activity.map.MapsActivity.TREE;

public class TreeDetail extends AppCompatActivity implements View.OnClickListener {

    private Tree tree;
    private TextView txtName;
    private ImageView imgChoose, imgTree;
    private Button btnSendComment;
    private EditText editTextComment;
    private TextView txtHowToWater;

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
        imgTree = (ImageView) findViewById(R.id.image_view_tree_image);
        imgChoose.setOnClickListener(this);
        btnSendComment = findViewById(R.id.button_send_comment);
        btnSendComment.setOnClickListener(this);
        editTextComment = findViewById(R.id.edit_text_comment);
        txtHowToWater = (TextView)findViewById(R.id.text_view_how_to_water);
    }

    public void getTreeId() {
        Intent intent = getIntent();
        tree = intent.getParcelableExtra(TREE);
        if(tree == null) {
            return;
        }
        txtName.setText(tree.getName());
        txtHowToWater.setText("Cách tưới: " +tree.getHowToWater());
        imgTree.setImageResource(tree.getImage());
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
            case R.id.button_send_comment:
                Toast.makeText(this, R.string.sent_comment, Toast.LENGTH_SHORT).show();
                editTextComment.setText("");
                break;
        }
    }
}
