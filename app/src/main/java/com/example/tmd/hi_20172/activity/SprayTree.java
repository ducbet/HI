package com.example.tmd.hi_20172.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;

public class SprayTree extends AppCompatActivity {

    private TextView textViewThieuNuoc;
    private TextView textViewNuocConLai;
    private TextView textViewNuocCanTuoi;
    private Button btnTuoiNuoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spray_tree);

        getControls();
        setEvents();
    }

    private void getControls() {
        textViewNuocCanTuoi = findViewById(R.id.text_view_nuoc_can_tuoi);
        textViewNuocConLai = findViewById(R.id.text_view_nuoc_con_lai);
        textViewThieuNuoc = findViewById(R.id.text_view_thieu_nuoc);
        btnTuoiNuoc = findViewById(R.id.button_tuoi_nuoc);
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
            }
        });
    }
}
