package com.mad.trafficclient;

/**
 * Created by asus on 2017/12/26.
 */

public class Record {
    public int ID;
    public String Intime;
    public String Outtime;
    public int Money;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getIntime() {
        return Intime;
    }

    public void setIntime(String intime) {
        Intime = intime;
    }

    public String getOuttime() {
        return Outtime;
    }

    public void setOuttime(String outtime) {
        Outtime = outtime;
    }


}
