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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.mad.trafficclient.httppost.HttpUtilss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentProject11Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerProject11;
    private ListView lvProject11;
    private MyAdapter adapter;
    private Thread mThread1;
    private SpinnerAdapter adapter1;
    private String res1;
    private String item;
    private Button btn;
    private List<Car> list = new ArrayList<Car>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle1 = msg.getData();
            String result = bundle1.getString("item");
            Log.i("dd", result);
            switch (msg.what) {

                case 001:
                    if (result.equals("Money升序")) {
                        sortms();
                    } else if (result.equals("Money降序")) {
                        sortmj();
                    } else if (result.equals("时间升序")) {
                        sort1();
                    } else if (result.equals("时间降序")) {
                        sort2();
                    } else if (result.equals("车号升序")) {
                        sort1();
                    } else if (result.equals("车号降序")) {
                        sort2();
                    } else {
                        sort1();
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }


        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project11, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerProject11 = (Spinner) view.findViewById(R.id.spinner_project11);
        lvProject11 = (ListView) view.findViewById(R.id.lv_project11);
        adapter = new MyAdapter(list, getActivity());
        lvProject11.setAdapter(adapter);
        adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getData());
        spinnerProject11.setAdapter(adapter1);
        spinnerProject11.setOnItemSelectedListener(this);
        btn = (Button)view.findViewById(R.id.btn_project11);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_project(v);
            }
        });




    }
    private void btn_project(View view){
        if (list.size()>0){
            list.clear();
        }

        setThread1();
    }

    private List<String> getData() {
        List<String> list = new ArrayList<String>();
        list.add("Money升序");
        list.add("车号升序");
        list.add("时间降序");
        list.add("时间升序");
        list.add("Money降序");
        list.add("车号降序");

        return list;
    }

    private void sort1() {
        Collections.sort(list, new Comparator<Car>() {


            @Override
            public int compare(Car lhs, Car rhs) {
                if (lhs.getCarId() > rhs.getCarId()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    private void sort2() {
        Collections.sort(list, new Comparator<Car>() {


            @Override
            public int compare(Car lhs, Car rhs) {
                if (lhs.getCarId() < rhs.getCarId()) {
                    return 1;
                }
                return -1;
            }
        });
    }
    private void sortms() {
        Collections.sort(list, new Comparator<Car>() {


            @Override
            public int compare(Car lhs, Car rhs) {
                if (lhs.getMoney() > rhs.getMoney()) {
                    return 1;
                }
                return -1;
            }
        });
    }
    private void sortmj() {
        Collections.sort(list, new Comparator<Car>() {


            @Override
            public int compare(Car lhs, Car rhs) {
                if (lhs.getMoney() < rhs.getMoney()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    int i = 0;

    private void setThread1() {
        mThread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                while (true){
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String json = "{ \"UserName\":\"user1\"}";
                    res1 = new HttpUtilss().send("http://192.168.199.1:8080/TrafficServer/action/GetAllCarAccount.do", json);
                    parseJson(i);
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("item", item);
                    message.setData(bundle);
                    message.what = 001;
                    handler.sendMessage(message);
                    i++;
                    if (i>14){
                        i = 0;
                    }


                }

            }
        };
        mThread1.start();

    }

    private void parseJson(int num) {
        try {
            JSONObject jsonObject = new JSONObject(res1);
            String ss = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(ss);
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(num);
            Car car = new Car();
            car.setCarId(jsonObject1.getInt("CarId"));
            car.setNowDate(jsonObject1.getString("UserName"));
            car.setMoney(jsonObject1.getInt("Balance"));
            list.add(car);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (list.size()>15){
           list.clear();
            i = -1;

        }


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = (String) spinnerProject11.getItemAtPosition(position);
        setThread1();


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
