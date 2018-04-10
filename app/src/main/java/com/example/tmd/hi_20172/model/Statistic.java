package com.example.tmd.hi_20172.model;

import java.util.Random;

/**
 * Created by tmd on 10/04/2018.
 */

public class Statistic {
    private String username;
    private String time;
    private int ml_water;

    public Statistic(String username, String time) {
        this.username = username;
        this.time = time;
        this.ml_water = new Random().nextInt(100) + 100;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMl_water() {
        return ml_water;
    }

    public void setMl_water(int ml_water) {
        this.ml_water = ml_water;
    }
}
