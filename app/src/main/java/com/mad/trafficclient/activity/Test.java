package com.mad.trafficclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.mad.trafficclient.httppost.HttpPostRequest;
import com.mad.trafficclient.httppost.HttpThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.R.attr.icon;
import static android.R.attr.text;
import static android.R.id.list;

/**
 * Created by asus on 2017/12/25.
 */

public class Test extends Activity {
    private Handler handler;
    private HttpThread httpThread;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        list = new ArrayList<String>();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
               String ss = message.obj.toString();
                try {
                    JSONArray jsonArray = new JSONArray(ss);
                    JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                    Iterator<String> iterator = jsonObject.keys();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        String value = jsonObject.getString(key);
                        list.add(value);
                    }
                    Log.i("TADDD",list.toString());
                    Log.i("TTTTT",list.get(3));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            };
        });

        httpThread = new HttpThread(this, handler);
        httpThread.setUrl("http://192.168.199.20:8080/TrafficServer/action/GetAllSense.do");
        String strJson = "{\"UserName\":" + "\"user1\"" + "}";
        httpThread.setJsonstring(strJson);
        httpThread.start();
    }



}
