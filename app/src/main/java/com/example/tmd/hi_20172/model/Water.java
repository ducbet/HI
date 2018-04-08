package com.example.tmd.hi_20172.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tmd on 06/04/2018.
 */

public class Water extends StopOver implements Parcelable {

    protected Water(Parcel in) {
        id = in.readString();
        latlon = in.readParcelable(LatLng.class.getClassLoader());
        icon = in.readInt();
        name = in.readString();
        choose = in.readByte() != 0;
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

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public static Creator<Water> getCREATOR() {
        return CREATOR;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Water(LatLng latlon, int icon) {
        this.latlon = latlon;
        this.icon = icon;
    }

    public Water(LatLng latlon, int icon, String name) {
        this.latlon = latlon;
        this.icon = icon;
        this.name = name;
    }

    public Water(String id, LatLng latlon, int icon, String name, boolean choose) {
        this.id = id;
        this.latlon = latlon;
        this.icon = icon;
        this.name = name;
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
        parcel.writeString(name);
        parcel.writeByte((byte) (choose ? 1 : 0));
    }
}
