package com.mad.trafficclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/12/27.
 */

public class AndroidProject26Activity extends Activity{
    private HttpThread httpThread,httpThread2;
    private List<String> list;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String result = (String) message.obj;
            list= new ArrayList<String>();
            list = JsonTools.parseJson(result);
            if (Integer.valueOf(list.get(3))>500){
                CarStop(1);
                CarStop(2);
                Log.i("CAR","####");

            }else  if (Integer.valueOf(list.get(3))>600){
                CarStop(1);
                CarStop(2);
                CarStop(3);
                CarStop(4);

            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        post();
    }
    public void post(){
        httpThread = new HttpThread(this, handler);
        httpThread.setUrl("http://192.168.191.1:8080/TrafficServer/action/GetAllSense.do");
        String strJson = "{\"UserName\":\"user1\"}";
        httpThread.setJsonstring(strJson);
        httpThread.start();
    }
    public void CarStop(int num){
        httpThread2 = new HttpThread(this, handler);
        httpThread2.setUrl("http://192.168.1.231:8080/TrafficServer/action/GetAllSense.do");
        String strJson = "{\"CarId\":"+num+", \"CarAction\":\"Stop\", \"UserName\":\"user2\"}";
        httpThread2.setJsonstring(strJson);
        httpThread2.start();
    }
    /*public void CarStart(int num){
        httpThread2 = new HttpThread(this, handler);
        httpThread2.setUrl("http://192.168.199.20:8080/TrafficServer/action/GetAllSense.do");
        String strJson = "{\"CarId\":"+num+", \"CarAction\":\"Start\", \"UserName\":\"user2\"}";
        httpThread2.setJsonstring(strJson);
        httpThread2.start();
    }*/
}
