package com.mad.trafficclient;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.mad.trafficclient.httppost.HttpThread14;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.ArrayList;
import java.util.List;


public class AndroidProject14Fragment extends Fragment implements View.OnClickListener {


    private TextView guangtv;
    private Button guangbtn;
    private TextView guangzhaotext;
    private TextView guangtext;
    private HttpThread14 httpThread;
    private List<String> list;
    private Switch aSwitch;

     Handler handler = new Handler(new Handler.Callback() {
         @Override
         public boolean handleMessage(Message message) {
             String result = (String) message.obj;
             list= new ArrayList<String>();
             list = JsonTools.parseJson(result);
             if (Integer.valueOf(list.get(6))>3000){
                 guangzhaotext.setText(list.get(6)+"光线太强了，为您自动关闭所有路灯");
                 aSwitch.setChecked(false);
                 aSwitch.setText("关闭");

             }else {
                 guangzhaotext.setText(list.get(6)+"光线太弱了，为您自动打开所有路灯");
                 aSwitch.setChecked(true);
                 aSwitch.setText("打开");
             }



             return false;
         }
     });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.android_project14, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guangtv = (TextView)view.findViewById(R.id.guangtv);
        guangbtn = (Button)view.findViewById(R.id.guangbtn);
        guangzhaotext = (TextView)view.findViewById(R.id.guangzhaotext);
        guangtext = (TextView)view.findViewById(R.id.guangtext);
        aSwitch = (Switch)view.findViewById(R.id.aSwitch);
        search();


    }

    @Override
    public void onClick(View view) {

    }
    public void search(){
        httpThread = new HttpThread14(getActivity(), handler);
        httpThread.setUrl("http://192.168.191.1:8080/TrafficServer/action/GetAllSense.do");
        String strJson = "{\"UserName\":" + "\"user1\"}";
        httpThread.setJsonstring(strJson);
        httpThread.start();
    }
}
