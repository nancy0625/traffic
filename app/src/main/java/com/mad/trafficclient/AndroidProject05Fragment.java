package com.mad.trafficclient;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ListView;

import com.mad.trafficclient.db.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AndroidProject05Fragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private TextView textChuan;
    private Spinner spinner;
    private TextView textZhou;
    private Spinner spinner2;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private ListView listView01;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    private MyAdapter05 adapter3;
    private MyDataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private List<Sense> list1;
    private String item,item2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project05, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textChuan = (TextView) view.findViewById(R.id.textChuan);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        textZhou = (TextView) view.findViewById(R.id.textZhou);
        spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        view.findViewById(R.id.search).setOnClickListener(this);
        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);
        text3 = (TextView) view.findViewById(R.id.text3);
        text4 = (TextView) view.findViewById(R.id.text4);
        listView01 = (ListView) view.findViewById(R.id.listView01);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getData());
        adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getData2());
        spinner2.setAdapter(adapter2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        dataBaseHelper = new MyDataBaseHelper(getActivity(), "Sense.db", null, 1);
        db = dataBaseHelper.getReadableDatabase();
    }


    public List<String> getData() {
        List<String> list = new ArrayList<String>();

        list.add("空气温度");
        list.add("空气湿度");
        list.add("光照");
        list.add("CO2");
        list.add("PM2.5");
        list.add("道路状态");

        return list;
    }

    public List<String> getData2() {
        List<String> list = new ArrayList<String>();
        list.add("5/m");
        list.add("3/m");
        return list;
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView==spinner){
            item = spinner.getItemAtPosition(i).toString();


        }else {
            item2 = spinner2.getItemAtPosition(i).toString();
            if (item2.equals("5/m")){
                list1 = dataBaseHelper.selectAvg(5);
            }else {
                list1 = dataBaseHelper.selectAvg(3);
            }


        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        adapter3 = new MyAdapter05(list1,getActivity());
        listView01.setAdapter(adapter3);
        adapter3.type = item;
        adapter3.notifyDataSetChanged();
    }
}
