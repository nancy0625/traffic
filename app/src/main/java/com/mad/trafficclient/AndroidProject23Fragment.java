package com.mad.trafficclient;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;

import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.HttpUtils;
import com.mad.trafficclient.httppost.JsonTools;
import com.mad.trafficclient.httppost.Jsontool;

import java.util.ArrayList;
import java.util.List;

public class AndroidProject23Fragment extends Fragment {

    private TextView tv11Project23;
    private TextView tv12Project23;
    private TextView tv21Project23;
    private TextView tv22Project23;
    private HttpThread httpThread;
    private HttpThread httpThread2;
    private List<String> list,list2;
    private int bus1, bus2, sum1, sum2;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            list = new ArrayList<String>();
            list2 = new ArrayList<String>();


            switch (message.what){
                case 001:
                    Bundle bundle = message.getData();
                    String str1 = bundle.getString("list");
                    list = new Jsontool().parseList(str1,0);
                    list2 = new Jsontool().parseList(str1,1);
                    String sum = ((Integer.valueOf(list.get(2))*1000/20*60)+"");
                    String sum2 = ((Integer.valueOf(list2.get(2))*1000/20*60)+"");
                    tv11Project23.setText("距离站台"+sum+"m");
                    tv12Project23.setText("距离站台"+sum2+"m");

                    break;
                case 002:
                    Bundle bundle1 = message.getData();
                    String str4 = bundle1.getString("list2");
                    list = new Jsontool().parseList(str4,0);
                    list2 = new Jsontool().parseList(str4,1);
                    String sum3 = ((Integer.valueOf(list.get(2))*1000/20*60)+"");
                    String sum4 = ((Integer.valueOf(list2.get(2))*1000/20*60)+"");
                    tv21Project23.setText("距离站台"+sum3+"m");
                    tv22Project23.setText("距离站台"+sum4+"m");
                    break;
            }

            /*if (message.what == 1) {
                list = new ArrayList<Station>();
                String result = (String) message.obj;
                list = JsonTools.parseJsonStation(result);
                bus1 = Integer.parseInt(list.get(0).getDistance());
                Log.i("bus1", bus1 + "");
                bus2 = Integer.parseInt(list.get(1).getDistance());
                sum1 = (int) (bus1 / 20) * 60;
                sum2 = (int) (bus2 / 20) * 60;
                tv11Project23.setText("距离站台" + sum1 + "" + "m");
                tv12Project23.setText("距离站台" + sum2 + "" + "m");

            }else if (message.what == 2){

                list = new ArrayList<Station>();
                String result = (String) message.obj;
                list = JsonTools.parseJsonStation(result);
                bus1 = Integer.parseInt(list.get(0).getDistance());
                bus2 = Integer.parseInt(list.get(1).getDistance());
                sum1 = (int) (bus1 / 20) * 60;
                sum2 = (int) (bus2 / 20) * 60;
                Log.i("bus2", bus1 + "");
                tv21Project23.setText("距离站台" + sum1 + "" + "m");
                tv22Project23.setText("距离站台" + sum2 + "" + "m");

            }
*/
            Log.i("Station", list.toString());


        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project23, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv11Project23 = (TextView) view.findViewById(R.id.tv1_1_project23);
        tv12Project23 = (TextView) view.findViewById(R.id.tv1_2_project23);
        tv21Project23 = (TextView) view.findViewById(R.id.tv2_1_project23);
        tv22Project23 = (TextView) view.findViewById(R.id.tv2_2_project23);
       ser();


    }

    public void search1() {
       /* httpThread = new HttpThread(getActivity(), handler);
        httpThread.setUrl("http://192.168.191.1:8080/TrafficServer/action/GetBusstationInfo.do");
        String strJson = "{\"BusStationID\":1, \"UserName\":\"user1\"}";
        httpThread.setJsonstring(strJson);
        httpThread.setFlag(1);
        httpThread.start();

*/
        String js1 = "{\"BusStationID\":1, \"UserName\":\"user1\"}";
        String str1 =  new HttpUtils().Send("http://192.168.191.1:8080/TrafficServer/action/GetBusstationInfo.do",js1);
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("list",str1);
        message.setData(bundle);
        message.what = 001;
        handler.sendMessage(message);

    }

    public void search2() {
      /*  httpThread2 = new HttpThread(getActivity(), handler);
        httpThread2.setUrl("http://192.168.191.1:8080/TrafficServer/action/GetBusstationInfo.do");
        String strJson = "{\"BusStationID\":2, \"UserName\":\"user1\"}";
        httpThread2.setJsonstring(strJson);
       httpThread2.setFlag(2);
        httpThread2.start();*/
        String js1 = "{\"BusStationID\":2, \"UserName\":\"user1\"}";
        String str1 =  new HttpUtils().Send("http://192.168.191.1:8080/TrafficServer/action/GetBusstationInfo.do",js1);
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("list2",str1);
        message.what = 002;
        message.setData(bundle);
        handler.sendMessage(message);


    }
    public void ser(){
        new Thread(){
            @Override
            public void run() {
               search1();
                search2();
            }
        }.start();
    }

}
