package com.example.tmd.hi_20172.activity.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tmd.hi_20172.R;
import com.example.tmd.hi_20172.model.StopOver;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by tmd on 11/04/2018.
 */

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private StopOver stopOver;

    public CustomInfoWindowGoogleMap(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.info_tree, null);
        stopOver = (StopOver) marker.getTag();
        TextView txtName = view.findViewById(R.id.text_view_info_tree_name);
        txtName.setText(stopOver.getName());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
