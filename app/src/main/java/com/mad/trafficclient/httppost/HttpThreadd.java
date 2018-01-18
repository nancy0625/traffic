package com.mad.trafficclient.httppost;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.mad.trafficclient.Sense;
import com.mad.trafficclient.db.MyDataBaseHelper;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class HttpThreadd extends Thread {

    private Context context;
    private String url;
    private String jsonstring;
    private List<String> list2 = new ArrayList<String>();
    private List<String> list = new ArrayList<String>();
    private Handler handler;
    private MyDataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public HttpThreadd(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
        dataBaseHelper = new MyDataBaseHelper(context, "Sense.db", null, 1);
        db = dataBaseHelper.getWritableDatabase();
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {


            HttpPostRequest post2 = new HttpPostRequest();
            String json = "{\"RoadId\":1,\"UserName\":\"user1\"}";
            int re = post2.requestHttp("http://192.168.191.1:8080/TrafficServer/action/GetRoadStatus.do", json);
            String info2 = post2.getWebContext();



            HttpPostRequest post = new HttpPostRequest();

            int res = post.requestHttp(url, jsonstring);

            String webContent = post.getWebContext();
            System.out.println("***res:" + res);
            if (res == 1 && re == 1) {
                list = JsonTools.parseJson(webContent);
                list2 = JsonTools.parseJson(info2);

                Sense sense = new Sense();
                sense.setTemperature(Integer.valueOf(list.get(4)));
                sense.setHumidity(Integer.valueOf(list.get(5)));
                sense.setLightIntensity(Integer.valueOf(list.get(6)));
                sense.setCO2(Integer.valueOf(list.get(3)));
                sense.setPm(Integer.valueOf(list.get(2)));
                sense.setStatus(Integer.valueOf(list2.get(2)));

                setData(sense.getTemperature(),sense.getHumidity(),sense.getLightIntensity(),sense.getCO2(),sense.getPm(),sense.getStatus());


            }
            /******/
            else if (res == 901) {
                Message msg = new Message();
                msg.what = res;
                msg.obj = "timeout!";
                handler.sendMessage(msg);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        /******/

    }
    public void setData(int temm, int humm, int ligt, int COO, int ppm, int statut) {

        ContentValues updateValues = new ContentValues();
        updateValues.put("temperature", temm);
        updateValues.put("humidity", humm);
        updateValues.put("lightIntensity", ligt);
        updateValues.put("CO2", COO);
        updateValues.put("pm", ppm);
        updateValues.put("status", statut);
        updateValues.put("timer",new Date().getTime());
        db.insert("sense", null, updateValues);
        Log.i("TTTT","CCCCCCCCC");


    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJsonstring() {
        return jsonstring;
    }

    public void setJsonstring(String jsonstring) {
        this.jsonstring = jsonstring;
    }
}

