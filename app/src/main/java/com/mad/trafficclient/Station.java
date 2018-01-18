package com.mad.trafficclient;

/**
 * Created by asus on 2017/12/26.
 */

public class Station {
    public int ID;
    public String Name;
    public String Distance;
    public int Time;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public int getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }
}
