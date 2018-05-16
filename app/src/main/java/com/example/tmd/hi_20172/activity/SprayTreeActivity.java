package com.example.tmd.hi_20172.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.Tree;

import static com.example.tmd.hi_20172.activity.map.MapsActivity.TREE;

public class SprayTreeActivity extends AppCompatActivity {

    private TextView textViewTenCay;
    private TextView textViewThieuNuoc;
    private TextView textViewNuocConLai;
    private TextView textViewNuocCanTuoi;
    private Button btnTuoiNuoc;
    private ImageView imageViewTree;

    private Tree tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spray_tree);

        getControls();
        setEvents();
        getTree();
    }

    public void getTree() {
        Intent intent = getIntent();
        tree = intent.getParcelableExtra(TREE);
        if (tree == null) {
            return;
        }
        textViewTenCay.setText(tree.getName());
        imageViewTree.setImageResource(tree.getImage());
        if (tree.getStatus() == Tree.GREEN) {
            textViewThieuNuoc.setText("0%");
            textViewNuocCanTuoi.setText("0ml");
            textViewThieuNuoc.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewNuocCanTuoi.setTextColor(getResources().getColor(R.color.colorAccent));
            btnTuoiNuoc.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            btnTuoiNuoc.setText("Đã đủ nước");
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("result", tree);
//            setResult(Activity.RESULT_OK, returnIntent);
        }
    }

    private void getControls() {
        textViewTenCay = findViewById(R.id.text_view_name_tree);
        textViewNuocCanTuoi = findViewById(R.id.text_view_nuoc_can_tuoi);
        textViewNuocConLai = findViewById(R.id.text_view_nuoc_con_lai);
        textViewThieuNuoc = findViewById(R.id.text_view_thieu_nuoc);
        btnTuoiNuoc = findViewById(R.id.button_tuoi_nuoc);
        imageViewTree = findViewById(R.id.image_view_tree_image_spray);
    }

    private void setEvents() {
        btnTuoiNuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewThieuNuoc.setText("0%");
                textViewNuocCanTuoi.setText("0ml");
                textViewNuocConLai.setText("2500ml");
                textViewThieuNuoc.setTextColor(getResources().getColor(R.color.colorAccent));
                textViewNuocCanTuoi.setTextColor(getResources().getColor(R.color.colorAccent));
                btnTuoiNuoc.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                btnTuoiNuoc.setText("Đã đủ nước");

                tree.setStatus(Tree.GREEN);
                tree.setIcon(Tree.GREEN);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", tree);
                setResult(Activity.RESULT_OK, returnIntent);
//                finish();
                btnTuoiNuoc.setClickable(false);
            }
        });
    }
}
