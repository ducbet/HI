package com.example.tmd.hi_20172.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tmd on 06/04/2018.
 */

public class Water extends StopOver implements Parcelable {
    public Water(LatLng latlon, int icon, String name) {
        super(latlon, icon, name);
    }
}
