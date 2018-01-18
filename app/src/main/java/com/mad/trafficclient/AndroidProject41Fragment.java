package com.mad.trafficclient;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.mad.trafficclient.activity.Activity_Main;

public class AndroidProject41Fragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private TextView textView2;
    private TextView btnregisterProject41;
    private TextView btnfindProject41;
    private SharedPreferences spp;
    private FragmentManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project41, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        btnregisterProject41 = (TextView) view.findViewById(R.id.btnregister_project41);
        btnfindProject41 = (TextView) view.findViewById(R.id.btnfind_project41);
        view.findViewById(R.id.btn_internet_project29).setOnClickListener(this);
        spp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        manager = getFragmentManager();
       btnregisterProject41.setOnClickListener(this);
       btnfindProject41.setOnClickListener(this);


        if (spp.getString("usernames","").length()>0){
            sta(Activity_Main.class);
        }

    }

    public void  Register(View v){
        FragmentTransaction ft = manager.beginTransaction();
        AndroidRegisterProject41Fragment fr = new AndroidRegisterProject41Fragment();
        ft.replace(R.id.maincontent,fr);
        ft.commit();


    }


    public void find(View v){
        FragmentTransaction ft = manager.beginTransaction();
        AndroidPasswordProject41Fragment fr = new AndroidPasswordProject41Fragment();
        ft.replace(R.id.maincontent,fr);
        ft.commit();
    }


    private EditText getEtuserProject29(){
        return (EditText) getView().findViewById(R.id.etuser_project29);
    }

    private EditText getEtpwdProject29(){
        return (EditText) getView().findViewById(R.id.etpwd_project29);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_internet_project29:
                String Strname = getEtuserProject29().getText().toString().trim();
                String Strpsd = getEtpwdProject29().getText().toString().trim();
                SharedPreferences.Editor editor = spp.edit();
                editor.putString("usernames",Strname);
                editor.putString("passwords",Strpsd);
                editor.commit();

               if (Strname.equals("admin")&&Strpsd.equals("123456")){
                   sta( Activity_Main.class);
               }else {
                   Toast.makeText(getActivity(),"用户名或者密码错误，请重新输入",Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.btnregister_project41:
                Register(btnregisterProject41);
                break;
            case R.id.btnfind_project41:
                find(btnfindProject41);
                break;
        }
    }
    public void sta(Class cl){
        Intent intent = new Intent(getActivity(),cl);
        startActivity(intent);
    }

}
