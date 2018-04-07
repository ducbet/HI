package com.example.tmd.hi_20172.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.activity.map.MapsActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setEvent();
    }

    private void setEvent() {
        findViewById(R.id.text_view_register_from_register_act).setOnClickListener(this);
        findViewById(R.id.text_view_login_from_register_act).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.text_view_register_from_register_act:
                intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                break;
            case R.id.text_view_login_from_register_act:
                finish();
                break;
        }
    }
}
