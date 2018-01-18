package com.mad.trafficclient;

/**
 * Created by asus on 2018/1/4.
 */

public class Sense {
    public int Hum;
    public int Tem;
    public int Co;
    public int status;
    public String timer;
    public int Lig;
    public int Pm;

    public int getPm() {
        return Pm;
    }

    public void setPm(int pm) {
        Pm = pm;
    }

    public int getHum() {
        return Hum;
    }

    public void setHum(int hum) {
        Hum = hum;
    }

    public int getTem() {
        return Tem;
    }

    public void setTem(int tem) {
        Tem = tem;
    }

    public int getCo() {
        return Co;
    }

    public void setCo(int co) {
        Co = co;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getLig() {
        return Lig;
    }

    public void setLig(int lig) {
        Lig = lig;
    }
}
