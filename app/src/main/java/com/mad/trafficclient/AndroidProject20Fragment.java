package com.mad.trafficclient;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.db.MyDataBaseHelper;
import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.List;
import java.util.concurrent.TimeoutException;


public class AndroidProject20Fragment extends Fragment implements View.OnClickListener {


    private Button btnsearch_project20;
    private List<Record> list;

    private List<Record> records;
    private HttpThread httpThread;
   private ListView lv;
    private MyDataBaseHelper dataBaseHelper;
    private MyAdapter20 adapter20;
    Boolean flag = false;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            records = new JsonTools(getActivity()).parseJson20((String) message.obj);
            Log.i("records",records.toString());
            adapter20 = new MyAdapter20(records,getActivity());
            lv.setAdapter(adapter20);
            adapter20.notifyDataSetChanged();


            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.android_project20, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btnsearch_project20).setOnClickListener(this);
        view.findViewById(R.id.btn_project20).setOnClickListener(this);
        httpThread = new HttpThread(getActivity(),handler);
        dataBaseHelper  = new MyDataBaseHelper(getActivity(), "Sense.db", null, 1);
        lv = (ListView)view.findViewById(R.id.lv_project20);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsearch_project20:
                Log.i("btnbtntbt","dddddddd");
                httpThread = new HttpThread(getActivity(), handler);
                httpThread.setUrl("http://192.168.191.1:8080/json.txt");
                String strJson = "{\"RoadId\":1,\"UserName\":\"user1\"}";
                httpThread.setJsonstring(strJson);
                httpThread.start();
                break;
            case R.id.btn_project20:
                dataBaseHelper.deleteTable("record");
                for (int i=0;i<records.size();i++){
                   dataBaseHelper.setData20(records.get(i).getID(),records.get(i).getIntime(),records.get(i).getOuttime(),records.get(i).getMoney());
                }
                Toast.makeText(getActivity(),"存储成功",Toast.LENGTH_SHORT).show();

                break;


        }


    }
}
