package com.example.tmd.hi_20172.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tmd on 07/04/2018.
 */

public abstract class StopOver implements Parcelable {
    protected String id = "";
    protected LatLng latlon;
    protected int icon;
    protected boolean choose;
    protected String name;

    protected StopOver(LatLng latlon, int icon, String name) {
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(latlon, flags);
        dest.writeInt(icon);
        dest.writeByte((byte) (choose ? 1 : 0));
        dest.writeString(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
