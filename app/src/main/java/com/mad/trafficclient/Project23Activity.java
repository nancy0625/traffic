package com.mad.trafficclient;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.ArrayList;
import java.util.List;

public class Project23Activity extends Activity  {

    private TextView ttt1;
    private TextView ttt2;
    private TextView ttt3;
    private TextView ttt4;
    private HttpThread httpThread,httpThread2;
    private List<String> list,list2;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            list = new ArrayList<String>();
            list2 = new ArrayList<String>();
            String result = (String) msg.obj;
            int nu = msg.what;


            if (nu == 2){
                list = new JsonTools().parseList(result,0);
                list2 = new JsonTools().parseList(result,1);
                ttt1.setText((Integer.valueOf(list.get(2))*1000/20*60)+"");
                ttt2.setText((Integer.valueOf(list2.get(2))*1000/20*60)+"");
            }else if (nu == 3){
                list = new JsonTools().parseList(result,0);
                list2 = new JsonTools().parseList(result,1);

                ttt3.setText((Integer.valueOf(list.get(2))*1000/20*60)+"");
                ttt4.setText((Integer.valueOf(list2.get(2))*1000/20*60)+"");
            }






            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project23);

        ttt1 = (TextView) findViewById(R.id.ttt1);
        ttt2 = (TextView) findViewById(R.id.ttt2);
        ttt3 = (TextView) findViewById(R.id.ttt3);
        ttt4 = (TextView) findViewById(R.id.ttt4);
        sta();
    }
    public void sta(){

        httpThread = new HttpThread(this,handler);
        httpThread.setUrl("http://192.168.199.42:8080/TrafficServer/action/GetBusstationInfo.do");
        String js1 = "{\"BusStationID\":1, \"UserName\":\"user1\"}";
        httpThread.setJsonstring(js1);
        httpThread.flog =2;
        httpThread.start();

        httpThread2 = new HttpThread(this,handler);
        httpThread2.setUrl("http://192.168.199.42:8080/TrafficServer/action/GetBusstationInfo.do");
        String js2 = "{\"BusStationID\":2, \"UserName\":\"user1\"}";
        httpThread2.setJsonstring(js2);
        httpThread2.flog =3;
        httpThread2.start();
    }

}
