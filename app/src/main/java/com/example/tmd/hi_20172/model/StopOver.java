package com.example.tmd.hi_20172.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tmd on 07/04/2018.
 */

public abstract class StopOver {
    protected String id = "";
    protected LatLng latlon;
    protected int icon;
    protected boolean choose;
    protected String name;

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
}
