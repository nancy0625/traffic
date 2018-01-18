package com.mad.trafficclient;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AndroidProject38Fragment extends Fragment {

    private ListView lvProject38;
    private MyAdapter38 adapter38;
    private HttpThread httpThread;
    private List<String> list = new ArrayList<String>();;
    private List<String> list2 = new ArrayList<String>();
    private Bundle bundle;


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            String result = (String) message.obj;
            list =  JsonTools.parseJson(result);

            list2.add(list.get(2));
            Log.i("listsss", list2.toString());
            adapter38 = new MyAdapter38(list2,getActivity());

            lvProject38.setAdapter(adapter38);

            return false;

        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project38, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvProject38 = (ListView) view.findViewById(R.id.lv_project38);
        httpThread = new HttpThread(getActivity(), handler);

        sendPost();
        lvProject38.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

               deleteItem(i);
                return false;
            }
        });
    }
    public void deleteItem(int num){
        if (list2.remove(num) != null){
            Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
        }
        adapter38.notifyDataSetChanged();
    }

    private void sendPost() {
        bundle = getArguments();

        if (bundle != null) {
            post();

        }


    }

    public void post() {
        int i=0;
        for ( i=1;i<=4;i++){

            httpThread.setUrl("http://192.168.191.1:8080/TrafficServer/action/GetRoadStatus.do");
            String json = "{\"RoadId\":" + i + ",\"UserName\":\"user1\"}";
            httpThread.setJsonstring(json);
            httpThread.start();
            Log.i("jkj", json);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }


}
