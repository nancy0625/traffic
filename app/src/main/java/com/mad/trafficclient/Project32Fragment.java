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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project32Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    private TextView textView4;
    private TextView textView8;
    private TextView textView5;
    private Spinner spinnerProject32;
    private TextView textView6;
    private TextView textView7,tv01;
    private AlertDialog.Builder builder1,builder2;
    private AlertDialog dialog1,dialog2;
    private View view01,view02;
    private SpinnerAdapter adapter;
    private Button btn1,btn2,btn3,btn4;
    private String item;
    private CheckBox cb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project32, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView4 = (TextView) view.findViewById(R.id.textView4);
        textView8 = (TextView) view.findViewById(R.id.textView8);
        textView5 = (TextView) view.findViewById(R.id.textView5);
        spinnerProject32 = (Spinner) view.findViewById(R.id.spinner_project32);
        textView6 = (TextView) view.findViewById(R.id.textView6);
        textView7 = (TextView) view.findViewById(R.id.textView7);
        view.findViewById(R.id.btn_project3202).setOnClickListener(this);
        view.findViewById(R.id.btn_project3201).setOnClickListener(this);
        view01 = LayoutInflater.from(getActivity()).inflate(R.layout.project32_chongdialog,null);
        view02 = LayoutInflater.from(getActivity()).inflate(R.layout.project32_dialog,null);
        builder2 = new AlertDialog.Builder(getActivity());
        builder1 = new AlertDialog.Builder(getActivity());

        btn1 = (Button)view01.findViewById(R.id.btn_32dialog1) ;
        btn2 = (Button)view01.findViewById(R.id.btn_32dialog2) ;
        btn3 = (Button)view02.findViewById(R.id.btn_32dialog01) ;
        btn4 = (Button)view02.findViewById(R.id.btn_32dialog02) ;

        cb = (CheckBox)view02.findViewById(R.id.cb_project32_dialog) ;
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        tv01 = (TextView)view01.findViewById(R.id.tv_1pr);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,dat());
        spinnerProject32.setAdapter(adapter);
        spinnerProject32.setOnItemSelectedListener(this);

        dialog1 = builder1.create();
        dialog1.setView(view01);
        dialog2 = builder2.create();
        dialog2.setView(view02);
    }
private List<String> dat(){
    List<String> list = new ArrayList<String>();
    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4");
    list.add("5");
    return list;
};
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_project3202:
                //TODO implement
                break;
            case R.id.btn_project3201:
                SimpleDateFormat fsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                tv01.setText("充值时间："+fsdf.format(new Date().getTime())+"充值金额:"+textView7.getText().toString().trim()+"充值车号"+item);
                dialog1.show();
                break;
            case R.id.btn_32dialog1:
                dialog1.dismiss();
                break;
            case R.id.btn_32dialog2:
                if (cb.isChecked()){
                    dialog2.dismiss();
                }else {
                    dialog2.show();
                }
                dialog1.dismiss();

                break;
            case R.id.btn_32dialog01:
                dialog1.dismiss();
                dialog2.dismiss();
                break;
            case R.id.btn_32dialog02:
                dialog2.dismiss();
                dialog1.dismiss();



        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = (String) spinnerProject32.getItemAtPosition(position);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
