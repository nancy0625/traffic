package com.mad.trafficclient;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mad.trafficclient.db.MyDataBaseHelper;
import com.mad.trafficclient.httppost.HttpThread;
import com.mad.trafficclient.httppost.JsonTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AndroidProject11Fragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private Spinner spinner;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private ListView listView2;
    private List<String> list;
    private List<Car> list2;
    private MyAdapter11 adapter3;
    private ArrayAdapter<String> adapter;
    private MyDataBaseHelper dataBaseHelper;
    private String item;
   private HttpThread httpThread;
    private Boolean flag = false;
     Handler handler = new Handler(new Handler.Callback() {
         @Override
         public boolean handleMessage(Message message) {
             new JsonTools(getActivity()).parseJson3((String) message.obj);
             flag = true;
             spinner.setSelection(1);

             list2 = dataBaseHelper.selectCarS("money");


             return false;
         }
     });


    /*private HttpThread httpThread;
    private Handler handler;
    private Car car;*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.android_project11, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (Spinner)view.findViewById(R.id.spinnerr);

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getdata());
        listView2 = (ListView)view.findViewById(R.id.listView2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        httpThread = new HttpThread(getActivity(),handler);
        httpThread.setUrl("http://192.168.1.231:8080/json.txt");
        String json = "{\"RoadId\":1,\"UserName\":\"user1\"}";
        httpThread.setJsonstring(json);
        httpThread.start();

        dataBaseHelper  = new MyDataBaseHelper(getActivity(), "Sense.db", null, 1);




    }

    private List<String> getdata(){
        list = new ArrayList<String>();
        list.add("Money降序");
        list.add("Money升序");
        list.add("车号升序");
        list.add("车号降序");
        list.add("时间升序");
        list.add("时间降序");
        return list;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (!flag){
            return;
        }


           item = spinner.getItemAtPosition(i).toString();

            if (item.equals("Money降序")){

                list2 = dataBaseHelper.selectCarJ("money");





            }else if (item.equals("Money升序")){
                list2 = dataBaseHelper.selectCarS("money");



            }else if (item.equals("车号升序")){
                list2 = dataBaseHelper.selectCarS("Carid");


            }else if (item.equals("车号降序")){
                list2 = dataBaseHelper.selectCarJ("Carid");


            }else if (item.equals("时间升序")){
                list2 = dataBaseHelper.selectCarS("outtime");


            }else if (item.equals("时间降序")){
                list2 = dataBaseHelper.selectCarJ("outtime");


            }

        adapter3 = new MyAdapter11(list2,getActivity());
        listView2.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();






    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




  /*  public void seek(){
        httpThread.setUrl("http://169.254.66.216:8080/TrafficServer/action/GetAllCarAccount.do");
        String json = "{\"UserName\":\"user1\"}";
        httpThread.setJsonstring(json);
        httpThread.start();
    }*/
}
