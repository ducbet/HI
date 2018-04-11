package com.example.tmd.hi_20172.model;

import java.util.Date;

public class History {
    Date date;
    String treeName;
    int treeId;
    int water;

    public History(Date date, String treeName, int treeId, int water) {
        this.date = date;
        this.treeName = treeName;
        this.treeId = treeId;
        this.water = water;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public int getTreeId() {
        return treeId;
    }

    public void setTreeId(int treeId) {
        this.treeId = treeId;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }
}
