package com.mad.trafficclient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.db.MyDatabaseHelper;
import com.mad.trafficclient.httppost.HttpUtils;
import com.mad.trafficclient.httppost.HttpUtilss;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Project02Fragment extends Fragment  {

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
                case 5:
                    if (getActivity()!=null){
                        Toast.makeText(getActivity(),"网络状态码："+HttpUtils.status+"",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    if (getActivity()!=null){
                        Toast.makeText(getActivity(),"网络状态码："+HttpUtils.status+"",Toast.LENGTH_SHORT).show();
                        update1();

                    }

                    break;
                case 4:
                    if (getActivity()!=null){
                        Toast.makeText(getActivity(),"网络状态码："+HttpUtils.status+"",Toast.LENGTH_SHORT).show();
                        update2();

                    }

                    break;
            }
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project02, null);
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
    private void t1(){
        new Thread(){
            @Override
            public void run() {
               while (true){
                   String json = "{\"UserName\":\"user1\"}";
                   ll = HttpUtils.send("http://192.168.4.162:8080/TrafficServer/action/GetAllSense.do",json);
                   if (HttpUtils.status == 200){
                       parsejson1();
                       handler.sendEmptyMessage(3);

                   }else {
                       handler.sendEmptyMessage(5);
                   }
                   try {
                       Thread.sleep(5000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        }.start();

    }
    private void t2(){
        new Thread(){
            @Override
            public void run() {
                while (true){
                    String json = "{\"RoadId\":1,\"UserName\":\"user1\"}";
                    l2 = HttpUtils.send("http://192.168.4.162:8080/TrafficServer/action/GetRoadStatus.do",json);
                    if (HttpUtils.status == 200){
                        parsejson2();
                        handler.sendEmptyMessage(4);

                    }else {
                        handler.sendEmptyMessage(5);
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
    int [] total = {0,0,0,0,0,0};
    double [] avg = {0,0,0,0,0,0};
    private void t3(){
        new Thread(){
            @Override
            public void run() {
               while (true){
                   try {
                       Thread.sleep(1*60*1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                 if (list.size()>=12&&list2.size()>=12){
                     for (int i=0;i<list.size();i++){
                         total[0] += list.get(i).getTem();
                         total[1] += list.get(i).getHum();
                         total[2] += list.get(i).getLig();
                         total[3] += list.get(i).getCo();
                         total[4] += list.get(i).getPm();
                     }
                     for (int j=0; j<list2.size();j++){
                         total[5] = list2.get(j);
                     }
                     for (int k = 0;k<total.length;k++){
                         avg[k] = (double)total[k]/12;
                     }
                     databaseHelper.setData(avg[0],avg[1],avg[2],avg[3],avg[4],avg[5]);
                     databaseHelper.del();
                 }
               }
            }
        }
        .start();
    }
    private void update1(){
        Sense sense = list.get(list.size()-1);
            tv01.setText(sense.getTem()+"");
            tv02.setText(sense.getHum()+"");
            tv03.setText(sense.getLig()+"");
            tv04.setText(sense.getCo()+"");
            tv05.setText(sense.getPm()+"");
        if (sense.getTem()>40){
            tv01.setBackgroundResource(R.drawable.red);
        }else {
            tv01.setBackgroundResource(R.drawable.green);
        }
        if (sense.getHum()>60){
            tv02.setBackgroundResource(R.drawable.red);
        }else {
            tv02.setBackgroundResource(R.drawable.green);
        }
        if (sense.getLig()>3000){
            tv03.setBackgroundResource(R.drawable.red);
        }else {
            tv04.setBackgroundResource(R.drawable.green);
        }
        if (sense.getCo()>100){
            tv04.setBackgroundResource(R.drawable.red);
        }else {
            tv04.setBackgroundResource(R.drawable.green);
        }
        if (sense.getPm()>400){
            tv05.setBackgroundResource(R.drawable.red);
        }else {
            tv05.setBackgroundResource(R.drawable.green);
        }

    }
    private void update2(){
       tv06.setText(list2.get(list2.size() - 1)+"");
        if (list2.get(list2.size()-1)>3){
            tv06.setBackgroundResource(R.drawable.red);
        }else {
            tv06.setBackgroundResource(R.drawable.green);
        }

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv01 = (TextView) view.findViewById(R.id.tv01);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv02 = (TextView) view.findViewById(R.id.tv02);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv03 = (TextView) view.findViewById(R.id.tv03);
        tv4 = (TextView) view.findViewById(R.id.tv4);
        tv04 = (TextView) view.findViewById(R.id.tv04);
        tv5 = (TextView) view.findViewById(R.id.tv5);
        tv05 = (TextView) view.findViewById(R.id.tv05);
        tv6 = (TextView) view.findViewById(R.id.tv6);
        tv06 = (TextView) view.findViewById(R.id.tv06);
        list = new ArrayList<Sense>();
        list2 = new ArrayList<Integer>();
        databaseHelper = new MyDatabaseHelper(getActivity(),"Sense.db",null,1);
        t1();
        t2();
        t3();


    }


}
