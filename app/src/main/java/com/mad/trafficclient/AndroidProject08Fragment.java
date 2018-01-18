package com.mad.trafficclient;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AndroidProject08Fragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private Spinner spinner1;
    private Spinner spinner2;
    private List<String> list;
    private List<String> list2;
    private TextView textView;
    private EditText text1;
    private ArrayAdapter<String> adapter;
    private HttpThread httpThread;
    private String item1,item2;
    private int flag1;
    private String str;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (flag1==1){
                String result = (String) message.obj;
                list2 = JsonTools.parseJson(result);
                textView.setText("账户余额为："+list2.get(2));
            }else {
                String result = (String) message.obj;
                list2 = JsonTools.parseJson(result);
                Toast.makeText(getActivity(),list2.get(1),Toast.LENGTH_SHORT).show();
            }


            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project08, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        text1 = (EditText)view.findViewById(R.id.ed_text);
        textView = (TextView)view.findViewById(R.id.text);
        view.findViewById(R.id.btn).setOnClickListener(this);
        view.findViewById(R.id.chongzhi).setOnClickListener(this);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getdata());
        httpThread = new HttpThread(getActivity(),handler);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);



    }

    private List<String> getdata(){
        list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        return list;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView==spinner1){
            item1 = spinner1.getItemAtPosition(i).toString();
            Log.i("item1",item1);
        }else {
            item2 = spinner2.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                httpThread.setUrl("http://192.168.1.231:8080/TrafficServer/action/GetCarAccountBalance.do");
                String json = "{\"CarId\":"+item1+", \"UserName\":\"user"+item1+"\"}";
                httpThread.setJsonstring(json);
                httpThread.start();
                flag1 = 1;
                break;
            case R.id.chongzhi:

                str = text1.getText().toString().trim();
                if (Integer.valueOf(str)>200){
                    Toast.makeText(getActivity(),"超过阈值,请重新输入",Toast.LENGTH_SHORT).show();
                }else {
                    httpThread.setUrl("http://192.168.1.231:8080/TrafficServer/action/SetCarAccountRecharge.do");
                    String jsson = "{\"CarId\":"+item2+",\"Money\":"+str+", \"UserName\":\"user" + item2 + "\"}";
                    httpThread.setJsonstring(jsson);
                    httpThread.start();
                    flag1 = 2;
                }
                break;

        }
    }
}
