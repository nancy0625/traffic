package com.mad.trafficclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.mad.trafficclient.db.MyDatabaseHelper;
import com.mad.trafficclient.httppost.HttpUtilss;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Project02Activity extends Activity {

    private TextView tv1;
    private TextView tv01;
    private TextView tv2;
    private TextView tv02;
    private TextView tv3;
    private TextView tv03;
    private TextView tv4;
    private TextView tv04;
    private TextView tv5;
    private TextView tv05;
    private TextView tv6;
    private TextView tv06;
    private SQLiteDatabase db;
    private MyDatabaseHelper databaseHelper;
    private List<Sense> list;
    private List<Integer> list2;
    private Context context;
    private String ll, l2;
    private Thread mThread1,mThread2,mThread3;



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 3:
                    update1();

                    break;
                case 4:
                    update2();

                    break;
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project02);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv01 = (TextView) findViewById(R.id.tv01);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv02 = (TextView) findViewById(R.id.tv02);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv03 = (TextView) findViewById(R.id.tv03);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv04 = (TextView) findViewById(R.id.tv04);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv05 = (TextView) findViewById(R.id.tv05);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv06 = (TextView) findViewById(R.id.tv06);
        list = new ArrayList<Sense>();
        list2 = new ArrayList<Integer>();
        sta1();
        sta2();
        databaseHelper = new MyDatabaseHelper(Project02Activity.this,"Sense.db",null,1);
        setThread3();

    }

    private void parsejson1() {
        JSONObject mjson = null;
        try {
            mjson = new JSONObject(ll);
            Sense sense = new Sense();
            sense.setCo(mjson.getInt("co2"));
            sense.setLig(mjson.getInt("LightIntensity"));
           sense.setTem(mjson.getInt("temperature"));
           sense.setHum(mjson.getInt("humidity"));
           sense.setPm(mjson.getInt("pm2.5"));
            list.add(sense);
            //大于1分钟的数据删除掉
            if(list.size()>12){
                list.remove(0);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void parsejson2(){
        JSONObject mjson = null;
        try {
            mjson = new JSONObject(l2);
            list2.add(mjson.getInt("Status"));
            //大于1分钟的数据删除掉
            if(list2.size()>12){
                list2.remove(0);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void update1() {
         Sense sense = list.get(list.size()-1);
            tv01.setText(sense.getTem()+"");
            tv02.setText(sense.getHum()+"");
            tv03.setText(sense.getLig()+"");
            tv04.setText(sense.getCo()+"");
            tv05.setText(sense.getPm()+"");
            if (sense.getTem()> 40) {
                tv01.setBackgroundResource(R.drawable.red);
            } else {
                tv01.setBackgroundResource(R.drawable.green);
            }
            if (sense.getHum()> 100) {
                tv02.setBackgroundResource(R.drawable.red);
            } else {
                tv02.setBackgroundResource(R.drawable.green);
            }
            if (sense.getLig() > 500) {
                tv03.setBackgroundResource(R.drawable.red);
            } else {
                tv03.setBackgroundResource(R.drawable.green);
            }
            if (sense.getCo()> 50) {
                tv04.setBackgroundResource(R.drawable.red);
            } else {
                tv04.setBackgroundResource(R.drawable.green);
            }
            if (sense.getPm()> 100) {
                tv05.setBackgroundResource(R.drawable.red);
            } else {
                tv05.setBackgroundResource(R.drawable.green);
            }


    }

    private void update2() {
     Integer staus = list2.get(list2.size() - 1);
            tv06.setText(staus+"");
            if (staus > 3) {
                tv06.setBackgroundResource(R.drawable.red);
            } else {
                tv06.setBackgroundResource(R.drawable.green);
            }

    }


    public void sta1() {
       mThread1 =  new Thread() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    String js1 = "{\"UserName\":\"user1\"}";
                    ll = new HttpUtilss().send("http://192.168.1.183:8080/TrafficServer/action/GetAllSense.do", js1);
                    parsejson1();

                    handler.sendEmptyMessage(3);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread1.start();


    }
    public void sta2() {
      mThread2 =  new Thread() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {

                    String js2 = "{\"RoadId\":1,\"UserName\":\"user1\"}";
                    l2 = new HttpUtilss().send("http://192.168.1.183:8080/TrafficServer/action/GetRoadStatus.do", js2);
                    parsejson2();
                    handler.sendEmptyMessage(4);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        mThread2.start();


    }
    private void setThread3(){
       mThread3 = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        Thread.sleep(1*60*1000);//每隔一分钟添加数据到数据库
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int [] total = {0,0,0,0,0,0};
                    double[] average = {0,0,0,0,0,0};
                    if (list.size()>=12&&list2.size()>=12){
                        //求出最近的数据的和
                        for (int i = list.size()-1;i>=0;i--){
                            Sense sense = list.get(i);
                            total[0] += sense.getTem();
                            total[1] += sense.getHum();
                            total[2] += sense.getLig();
                            total[3] += sense.getCo();
                            total[4] += sense.getPm();
                        }
                        for (int i = list2.size() - 1;i >= 0;i--){
                            int status = list2.get(i);
                            total[5] += status;

                        }
                        for (int i = 0;i<6;i++){
                            average[i] = (double)total[i]/12.0;
                        }

                        databaseHelper.setData(average[0],average[1],average[2],average[3],average[4],average[5]);
                        databaseHelper.del();
                    }
                }
            }
        };
        mThread3.start();
    }




}
