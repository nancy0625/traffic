package com.mad.trafficclient;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Switch;

import com.mad.trafficclient.httppost.HttpUtilss;

import org.json.JSONException;
import org.json.JSONObject;

public class Project14Fragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private TextView tv01Project14;
    private TextView tv02Project14;
    private Switch switchProject14;
    private Thread mThread1,mThread2,mThread4;
    private String res1;
    private Integer status;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 001:
                    if (status>2000){
                        tv01Project14.setText(status+""+" 光线太强了，为你自动关闭所有路灯");


                        for (int i=1;i<=4;i++){
                            setThread2(i);
                        }

                    }else {


                        tv01Project14.setText(status+""+" 光线太弱了，为你自动打开所有路灯");
                        for (int i=1;i<=4;i++){
                            setThread4(i);
                        }
                    }



                    break;
            }


        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project14, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        view.findViewById(R.id.btn_project14).setOnClickListener(this);
        tv01Project14 = (TextView) view.findViewById(R.id.tv01_project14);
        tv02Project14 = (TextView) view.findViewById(R.id.tv02_project14);
        switchProject14 = (Switch) view.findViewById(R.id.switch_project14);
        setThread1();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_project14:

                break;
        }
    }
    private void setThread4(final int num){
        mThread4 = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){

                    String json = "{\"RoadLightId\":"+num+",\"Action\":\"Start\", \"UserName\":\"user1\"}";
                    res1 = HttpUtilss.send("http://192.168.1.183:8080/TrafficServer/action/SetRoadLightStatusAction.do",json);
                    parseJson();
                    handler.sendEmptyMessage(004);

                }
            }
        };
        mThread4.start();
    }
    private void setThread2(final int num){
        mThread2 = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){

                    String json = "{\"RoadLightId\":"+num+",\"Action\":\"Close\", \"UserName\":\"user1\"}";
                    res1 = HttpUtilss.send("http://192.168.1.183:8080/TrafficServer/action/SetRoadLightStatusAction.do",json);
                    parseJson();
                    handler.sendEmptyMessage(002);

                }
            }
        };
        mThread2.start();
    }
   /* private void setThread3(final int num){
        //mThread3 = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String json = "{\"RoadLightId\":"+num+", \"UserName\":\"user1\"}";
                    res1 = HttpUtils.send("http://192.168.1.183:8080/TrafficServer/action/GetRoadLightStatus.do",json);
                    parseJson();
                    handler.sendEmptyMessage(003);

                }
            }
        };
        mThread3.start();
    }*/
    private void setThread1(){
        mThread1 = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){

                    String json = "{\"SenseName\":\"LightIntensity\", \"UserName\":\"user1\"}";
                    res1 = HttpUtilss.send("http://192.168.1.183:8080/TrafficServer/action/GetSenseByName.do",json);
                    parseJson();
                    handler.sendEmptyMessage(001);
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        mThread1.start();
    }
    private void parseJson(){
        try {
            JSONObject jsonObject = new JSONObject(res1);
           status = jsonObject.getInt("LightIntensity");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
   /* private void parseJson2(){
        try {
            JSONObject jsonObject = new JSONObject(res1);
            status = jsonObject.getInt("LightIntensity");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/
}
