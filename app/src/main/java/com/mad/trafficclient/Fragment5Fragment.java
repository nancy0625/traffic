package com.mad.trafficclient;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;

import com.mad.trafficclient.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Fragment5Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private TextView tv1P5;
    private Spinner spinner1Project5;
    private TextView tv2P5;
    private Spinner spinner2Project5;
    private TextView t1;
    private TextView t2;
    private TextView t3;
    private ListView lvP5;
    private SpinnerAdapter mSadapter1,mSadapter2;
    private List<Sense> list = new ArrayList<Sense>();
    private Thread mThread1;
    private String res1;
    private String item;
    private String items;
    private int num;
    private MyAdapter05 adapter05;
    private MyDatabaseHelper myDatabaseHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment5, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv1P5 = (TextView) view.findViewById(R.id.tv1_p5);
        spinner1Project5 = (Spinner) view.findViewById(R.id.spinner1_project5);
        tv2P5 = (TextView) view.findViewById(R.id.tv2_p5);
        spinner2Project5 = (Spinner) view.findViewById(R.id.spinner2_project5);
        view.findViewById(R.id.btn1_p5).setOnClickListener(this);
        t1 = (TextView) view.findViewById(R.id.t1);
        t2 = (TextView) view.findViewById(R.id.t2);
        t3 = (TextView) view.findViewById(R.id.t3);
        lvP5 = (ListView) view.findViewById(R.id.lv_p5);


        mSadapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getData1());
        spinner1Project5.setAdapter(mSadapter1);
        mSadapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getData2());
        spinner2Project5.setAdapter(mSadapter2);
        spinner1Project5.setOnItemSelectedListener(this);
        spinner2Project5.setOnItemSelectedListener(this);
        myDatabaseHelper = new MyDatabaseHelper(getActivity(),"Sense.db",null,1);



    }

    private List<String> getData1(){
        List<String> list = new ArrayList<String>();
        list.add("空气温度");
        list.add("空气湿度");
        list.add("光照强度");
        list.add("CO2");
        list.add("PM2.5");


        return list;
    }
    private List<String> getData2(){
        List<String> list = new ArrayList<String>();
        list.add("5/m");
        list.add("3/m");

        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1_p5:
                adapter05 = new MyAdapter05(getActivity(),list);
                lvP5.setAdapter(adapter05);

                adapter05.item = items;
                adapter05.notifyDataSetChanged();
                break;
        }
    }







    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent==spinner1Project5){
            items = (String) spinner1Project5.getItemAtPosition(position);
        }else {
            item = (String)spinner2Project5.getItemAtPosition(position);
            if (item.equals("5/m")){
                list = myDatabaseHelper.selectAvg(5);
            }else if (item.equals("3/m")){
                list = myDatabaseHelper.selectAvg(3);
            }
        }


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
