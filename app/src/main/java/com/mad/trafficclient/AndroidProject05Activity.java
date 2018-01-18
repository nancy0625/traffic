package com.mad.trafficclient;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mad.trafficclient.db.MyDataBaseHelper;

import java.util.ArrayList;
import java.util.List;


public class AndroidProject05Activity extends Activity implements AdapterView.OnItemSelectedListener {


    private TextView textChuan;
    private Spinner spinner;
    private TextView textZhou;
    private Spinner spinner2;
    private ListView listView;
    private Button button;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    private MyAdapter05 adapter3;
    private MyDataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;
    private List<Sense> list1;
    private String item,item2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_project05);
        initView();

    }

    private void initView() {
        textChuan = (TextView) findViewById(R.id.textChuan);
        spinner = (Spinner) findViewById(R.id.spinner);
        textZhou = (TextView) findViewById(R.id.textZhou);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        listView = (ListView) findViewById(R.id.listView01);
        button = (Button)findViewById(R.id.search);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getData());
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getData2());
        spinner2.setAdapter(adapter2);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        dataBaseHelper = new MyDataBaseHelper(this, "Sense.db", null, 1);
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
    public void search(View view) {

        adapter3 = new MyAdapter05(list1,this);
        listView.setAdapter(adapter3);
        adapter3.type = item;
        adapter3.notifyDataSetChanged();

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
