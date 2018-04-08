package com.example.tmd.hi_20172.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tmd on 07/04/2018.
 */

public class StopOver implements Parcelable {
    protected String id = "";
    protected LatLng latlon;
    protected int icon;
    protected boolean choose;
    protected String name;

    public StopOver(LatLng latlon, int icon, String name) {
        this.latlon = latlon;
        this.icon = icon;
        this.name = name;
    }

    protected StopOver(Parcel in) {
        id = in.readString();
        latlon = in.readParcelable(LatLng.class.getClassLoader());
        icon = in.readInt();
        choose = in.readByte() != 0;
        name = in.readString();
    }

    public static final Creator<StopOver> CREATOR = new Creator<StopOver>() {
        @Override
        public StopOver createFromParcel(Parcel in) {
            return new StopOver(in);
        }

        @Override
        public StopOver[] newArray(int size) {
            return new StopOver[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatlon() {
        return latlon;
    }

    public void setLatlon(LatLng latlon) {
        this.latlon = latlon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeParcelable(latlon, i);
        parcel.writeInt(icon);
        parcel.writeByte((byte) (choose ? 1 : 0));
        parcel.writeString(name);
    }
}
