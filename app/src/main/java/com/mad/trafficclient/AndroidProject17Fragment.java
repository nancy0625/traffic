package com.mad.trafficclient;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.ArrayList;
import java.util.List;


public class AndroidProject17Fragment extends Fragment implements View.OnClickListener {


    private Button btn_project17;
    private TextView tv_project17;
    private TextView tv_project17a;
    private HttpThread httpThread;
    private List<String> list;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            list = new ArrayList<String>();
            list = JsonTools.parseJson((String) message.obj);
            if (Integer.valueOf(list.get(2))<=3){
                tv_project17.setBackgroundResource(R.drawable.carone);
                tv_project17a.setBackgroundResource(R.drawable.cartwo);
            }else {
                tv_project17a.setBackgroundResource(R.drawable.carone);
                tv_project17.setBackgroundResource(R.drawable.cartwo);

            }
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.android_project17, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_project17).setOnClickListener(this);
        tv_project17 = (TextView)view.findViewById(R.id.tv_project17);
        tv_project17a = (TextView)view.findViewById(R.id.tv_project17a);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_project17:
                Log.i("btnbtntbt","dddddddd");
                httpThread = new HttpThread(getActivity(), handler);
                httpThread.setUrl("http://192.168.191.1:8080/TrafficServer/action/GetRoadStatus.do");
                String strJson = "{\"RoadId\":1,\"UserName\":\"user1\"}";
                httpThread.setJsonstring(strJson);
                httpThread.start();
                break;
        }

    }
}
