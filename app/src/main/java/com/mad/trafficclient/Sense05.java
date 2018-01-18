package com.mad.trafficclient;

/**
 * Created by asus on 2018/1/4.
 */

public class Sense05 {
    public int id;
    public int result;
    public int status;
    public String timer;

    public String  getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
