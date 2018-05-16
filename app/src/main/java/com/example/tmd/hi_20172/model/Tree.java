package com.example.tmd.hi_20172.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmd on 06/04/2018.
 */

public class Tree extends StopOver implements Parcelable {
    public static final int RED = 0;
    public static final int YELLOW = 1;
    public static final int GREEN = 2;

    private int status;
    private int image;
    private String howToWater;
    private int[] array_icon;

    public Tree(LatLng latlon, int[] array_icon, String name, int status, int image, String howToWater) {
        super(latlon, array_icon[status], name);
        this.status = status;
        this.image = image;
        this.howToWater = howToWater;
        this.array_icon = array_icon;
    }

    protected Tree(Parcel in) {
        super(in);
        status = in.readInt();
        image = in.readInt();
        howToWater = in.readString();
        array_icon = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(status);
        dest.writeInt(image);
        dest.writeString(howToWater);
        dest.writeIntArray(array_icon);
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHowToWater() {
        return howToWater;
    }

    public void setHowToWater(String howToWater) {
        this.howToWater = howToWater;
    }

    @Override
    public void setIcon(int status) {
        super.setIcon(array_icon[status]);
    }

    @Override
    public int getIcon() {
        return array_icon[status];
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
