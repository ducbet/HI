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

    protected Water(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Water> CREATOR = new Creator<Water>() {
        @Override
        public Water createFromParcel(Parcel in) {
            return new Water(in);
        }

        @Override
        public Water[] newArray(int size) {
            return new Water[size];
        }
    };
}
