package com.example.tmd.hi_20172.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tmd on 06/04/2018.
 */

public class Tree extends StopOver implements Parcelable {
    public static final int RED = 0;
    public static final int YELLOW = 1;
    public static final int GREEN = 2;

    private int status;

    public Tree(LatLng latlon, int icon, String name, int status) {
        super(latlon, icon, name);
        this.status = status;
    }

    protected Tree(Parcel in) {
        super(in);
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tree> CREATOR = new Creator<Tree>() {
        @Override
        public Tree createFromParcel(Parcel in) {
            return new Tree(in);
        }

        @Override
        public Tree[] newArray(int size) {
            return new Tree[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
