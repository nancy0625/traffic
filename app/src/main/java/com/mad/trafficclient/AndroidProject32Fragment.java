package com.mad.trafficclient;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AndroidProject32Fragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView moneyProject32B;
    private Spinner spinnerProject32;
    private TextView tv_setmoney_project32;
    private AlertDialog.Builder builder1, builder2;
    private TextView tv1, tv2, tv3;
    private CheckBox cb;
    private Date date;
    private ArrayAdapter arrayAdapter;
    private String item = "";
    SimpleDateFormat format;
    private AlertDialog dialog2, dialog;
    private View view01, view02;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project32, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_setmoney_project32 = (TextView) view.findViewById(R.id.tv_setmoney_project32);
        moneyProject32B = (TextView) view.findViewById(R.id.smoney_project32);
        spinnerProject32 = (Spinner) view.findViewById(R.id.spinner_project32);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getCar());
        spinnerProject32.setAdapter(arrayAdapter);
        spinnerProject32.setOnItemSelectedListener(this);
        view.findViewById(R.id.serach_project32).setOnClickListener(this);
        view.findViewById(R.id.setMoney_project32).setOnClickListener(this);

        date = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        builder1 = new AlertDialog.Builder(getActivity());
        dialog = builder1.create();
        builder2 = new AlertDialog.Builder(getActivity());
        dialog2 = builder2.create();
        view02 = LayoutInflater.from(getActivity()).inflate(R.layout.android_project32view2, null);
        view01 = LayoutInflater.from(getActivity()).inflate(R.layout.android_project32view1, null);
        cb = (CheckBox) view02.findViewById(R.id.cb_project32);
        cb.setChecked(false);
        view01.findViewById(R.id.nbtn_project32).setOnClickListener(this);
        view01.findViewById(R.id.pbtn_project32).setOnClickListener(this);

        view02.findViewById(R.id.nnbtn_project32).setOnClickListener(this);
        view02.findViewById(R.id.ppbtn_project32).setOnClickListener(this);

        dialog.setView(view02);


    }

    private List<String> getCar() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        return list;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serach_project32:

                break;
            case R.id.setMoney_project32:

                tv1 = (TextView) view01.findViewById(R.id.time_project32);
                tv1.setText("充值时间：" + format.format(date));
                tv2 = (TextView) view01.findViewById(R.id.money_project32);
                tv2.setText("充值金额：" + moneyProject32B.getText().toString().trim());
                tv3 = (TextView) view01.findViewById(R.id.car_project32);
                tv3.setText("充值车号：" + item);


                dialog2.setView(view01);

                dialog2.show();

                break;
            case R.id.pbtn_project32:
                dialog2.dismiss();

                break;
            case R.id.nbtn_project32:
                if (!cb.isChecked()){
                    dialog.show();
                }else {
                    dialog2.dismiss();
                }

                break;
            case R.id.ppbtn_project32:

                dialog.dismiss();
                dialog2.dismiss();
                break;
            case R.id.nnbtn_project32:
                dialog.dismiss();
                dialog2.dismiss();

                break;


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = spinnerProject32.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
