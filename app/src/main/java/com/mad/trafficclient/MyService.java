package com.mad.trafficclient;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.mad.trafficclient.db.MyDatabaseHelper;
import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.HttpUtilss;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2018/1/4.
 */

public class MyService extends Service {
    public MyService(){

    }
    private Project02Activity project02Activity;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       /* project02Activity = new Project02Activity(this);
        project02Activity.setDatas();*/


        return super.onStartCommand(intent, flags, startId);
    }



}
