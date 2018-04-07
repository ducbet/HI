package com.example.tmd.hi_20172.activity.map;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auth Priyanka
 */

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng;

    // TODO: 06/04/2018
    public static PatternItem DOT = new Dot();
    public static PatternItem DASH = new Dash(20);
    public static PatternItem GAP = new Gap(10);
    public static List<PatternItem> alternativePattern = Arrays.asList(DOT, GAP);

    public static List<List<Polyline>> listRoutesPolylines = new ArrayList<>();

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        latLng = (LatLng) objects[2];


        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {

        List<String[]> directionsList;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        displayDirection(directionsList);

    }

    public void displayDirection(List<String[]> directionsList) {
        Log.e("MY_TAG", "directionsList.length: " + directionsList.size());
        for (int i = 0; i < directionsList.size(); i++) {
            int count = directionsList.get(i).length;
            List<Polyline> polylines = new ArrayList<>();
            PolylineOptions options = new PolylineOptions()

                    .width(10)
                    .startCap(new RoundCap())
                    .endCap(new RoundCap());

            if (i == 0) {
                // shorstest route
                for (int j = 0; j < count; j++) {
                    options.addAll(PolyUtil.decode(directionsList.get(i)[j]))
                            .color(Color.parseColor("#71FF0000"));
                    Polyline polyline = mMap.addPolyline(options);
                    polyline.setClickable(true);
                    polylines.add(polyline);
                }
            } else {
                for (int j = 0; j < count; j++) {
                    options.addAll(PolyUtil.decode(directionsList.get(i)[j]))
                            .color(Color.parseColor("#71FF0000"))
                            .pattern(alternativePattern);
                    Polyline polyline = mMap.addPolyline(options);
                    polyline.setClickable(true);
                    polylines.add(polyline);
                }
            }
            listRoutesPolylines.add(polylines);
        }
    }
}

