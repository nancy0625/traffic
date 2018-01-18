package com.mad.trafficclient;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.mad.trafficclient.db.MyDataBaseHelper;
import com.mad.trafficclient.httppost.HttpUtils;
import com.mad.trafficclient.httppost.Jsontool;


import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class AndroidProject02Activity extends Activity {




    private TextView tv_title1;
    private TextView wendu;
    private TextView tv_title2;
    private TextView shidu;
    private TextView tv_title3;
    private TextView guangzhao;
    private TextView tv_title4;
    private TextView huatan;
    private TextView tv_title5;
    private TextView pm;
    private TextView tv_title6;
    private TextView daolu;
    private List<String> list,list2;
    private Context context;
    private MyDataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case 002:
                Bundle bundle = msg.getData();
                list = new Jsontool().parseList2(bundle.getString("list"));
                list2 = new Jsontool().parseList2(bundle.getString("list2"));
                update(Integer.valueOf(list.get(4)),Integer.valueOf(list.get(5)),Integer.valueOf(list.get(6)),Integer.valueOf(list.get(2)),Integer.valueOf(list.get(3)),Integer.valueOf(list2.get(2)));


        }
    }
};
public AndroidProject02Activity(Context context){
    this.context = context;
    dataBaseHelper = new MyDataBaseHelper(context, "Sense.db", null, 1);
    db = dataBaseHelper.getWritableDatabase();



}
public AndroidProject02Activity(){

}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_project02);
        try {
            initView();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void wendu(View view) {
        Toast.makeText(this, "温度", Toast.LENGTH_SHORT).show();
    }

    public void shidu(View view) {
        Toast.makeText(this, "湿度", Toast.LENGTH_SHORT).show();
    }

    public void guangzhao(View view) {
        Toast.makeText(this, "光照", Toast.LENGTH_SHORT).show();
    }

    public void huatan(View view) {
        Toast.makeText(this, "二氧化碳", Toast.LENGTH_SHORT).show();
    }

    public void pm(View view) {
        Toast.makeText(this, "PM2.5", Toast.LENGTH_SHORT).show();
    }

    public void daolu(View view) {
    }

    private void initView() throws InterruptedException {
        tv_title1 = (TextView) findViewById(R.id.tv_title1);
        wendu = (TextView) findViewById(R.id.wendu);
        tv_title2 = (TextView) findViewById(R.id.tv_title2);
        shidu = (TextView) findViewById(R.id.shidu);
        tv_title3 = (TextView) findViewById(R.id.tv_title3);
        guangzhao = (TextView) findViewById(R.id.guangzhao);
        tv_title4 = (TextView) findViewById(R.id.tv_title4);
        huatan = (TextView) findViewById(R.id.huatan);
        tv_title5 = (TextView) findViewById(R.id.tv_title5);
        pm = (TextView) findViewById(R.id.pm);
        tv_title6 = (TextView) findViewById(R.id.tv_title6);
        daolu = (TextView) findViewById(R.id.daolu);


        Intent intent = new Intent(this,MyService.class);
        startService(intent);
        sta();


    }
    public void sta(){
        new Thread(){
            @Override
            public void run() {
               while (!Thread.currentThread().isInterrupted()){
                   String js1 = "{\"UserName\":\"user1\"}";
                  String str1 =  new HttpUtils().Send("http://192.168.191.1:8080/TrafficServer/action/GetAllSense.do",js1);
                   String js2 = "{\"RoadId\":1,\"UserName\":\"user1\"}";
                   String str2 = new HttpUtils().Send("http://192.168.191.1:8080/TrafficServer/action/GetRoadStatus.do",js2);
                   Message message = Message.obtain();
                   message.what = 002;
                   Bundle bundle = new Bundle();
                   bundle.putString("list",str1);
                   bundle.putString("list2",str2);
                   message.setData(bundle);
                   handler.sendMessage(message);
                   try {
                       Thread.sleep(5000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

               }
            }
        }.start();
    }
    public void staa(){
        new Thread(){
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    String js1 = "{\"UserName\":\"user1\"}";
                    String str1 =  new HttpUtils().Send("http://192.168.191.1:8080/TrafficServer/action/GetAllSense.do",js1);
                    String js2 = "{\"RoadId\":1,\"UserName\":\"user1\"}";
                    String str2 = new HttpUtils().Send("http://192.168.191.1:8080/TrafficServer/action/GetRoadStatus.do",js2);
                    list = new Jsontool().parseList2(str1);
                    list2 = new Jsontool().parseList2(str2);
                    Sense sense = new Sense();
                    sense.setTemperature(Integer.valueOf(list.get(4)));
                    sense.setHumidity(Integer.valueOf(list.get(5)));
                    sense.setLightIntensity(Integer.valueOf(list.get(6)));
                    sense.setCO2(Integer.valueOf(list.get(2)));
                    sense.setPm(Integer.valueOf(list.get(3)));
                    sense.setStatus(Integer.valueOf(list2.get(2)));
                    setData(sense.getTemperature(),sense.getHumidity(),sense.getLightIntensity(),sense.getCO2(),sense.getPm(),sense.getStatus());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
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
        Log.i("TTTT","成功");


    }

   public void update(int t,int h,int l,int c,int p,int s){
       if (t > 40) {

           wendu.setBackgroundResource(R.drawable.red);

       } else {
           wendu.setBackgroundResource(R.drawable.green);
       }
       if (h > 60) {
           shidu.setBackgroundResource(R.drawable.red);

       } else {
           shidu.setBackgroundResource(R.drawable.green);
       }
       if (l > 2000) {
           guangzhao.setBackgroundResource(R.drawable.red);

       } else {
           guangzhao.setBackgroundResource(R.drawable.green);
       }
       if (c > 3000) {
           huatan.setBackgroundResource(R.drawable.red);

       } else {
           huatan.setBackgroundResource(R.drawable.green);
       }
       if (p > 35) {
           pm.setBackgroundResource(R.drawable.red);

       } else {
           pm.setBackgroundResource(R.drawable.green);
       }
       if (s >= 3) {
           daolu.setBackgroundResource(R.drawable.red);

       } else {
           daolu.setBackgroundResource(R.drawable.green);
       }
       wendu.setText(t + "");
       shidu.setText(h + "");
       guangzhao.setText(l + "");
       huatan.setText(c + "");
       pm.setText(p + "");
       daolu.setText(s + "");
   }




}
