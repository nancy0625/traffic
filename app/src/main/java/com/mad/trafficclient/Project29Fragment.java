package com.mad.trafficclient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mad.trafficclient.activity.Activity_Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Project29Fragment extends Fragment implements View.OnClickListener {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project29, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_01).setOnClickListener(this);
        view.findViewById(R.id.btn_02).setOnClickListener(this);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("提示");
        dialog.setMessage("正在登陆，请稍后。。。");
        getTv01().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ren();
            }
        });
        isrem();

    }

    private EditText getUser(){
        return (EditText) getView().findViewById(R.id.user);
    }

    private EditText getPsw(){
        return (EditText) getView().findViewById(R.id.psw);
    }

    private CheckBox getTv01(){
        return (CheckBox) getView().findViewById(R.id.cb_01);
    }
    private CheckBox getTv02(){
        return (CheckBox) getView().findViewById(R.id.cb_02);
    }
    private boolean isrem(){
        String user =sp.getString("username","");
        String pass =sp.getString("password","");

        if (user.length()>0&&pass.length()>0){
            getUser().setText(user);
            getPsw().setText(pass);
            getTv02().setChecked(true);
            return true;
        }
        return false;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_01:
                //TODO implement
                break;
            case R.id.btn_02:
                String u = getUser().getText().toString().trim();
                String p = getPsw().getText().toString().trim();
                che(u,p);
                if (getTv02().isChecked()){

                    editor = sp.edit();
                    editor.putString("username",u);
                    editor.putString("password",p);
                    editor.commit();
                }
                break;
        }
    }
    private void ren(){
        if (getTv01().isChecked()){
            String user =sp.getString("username","");
            String pass =sp.getString("password","");

            if (user.length()>0&&pass.length()>0){
                dialog.show();
            new Thread(){
                @Override
                public void run() {

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), Activity_Main.class);
                    startActivity(intent);
                }
            }.start();





            }
        }
    }
    private void che(String u,String  p){
        String ss = "[a-zA-Z]{6,12}";
        String pp = "^[a-zA-Z][a-zA-Z0-9]{6,12}";
        Pattern pattern1 = Pattern.compile(ss);
        Pattern pattern2 = Pattern.compile(pp);
        Matcher matcher1 = pattern1.matcher(u);
        Matcher matcher2 = pattern2.matcher(p);
        if (!matcher1.matches()&&!matcher2.matches()){
            Toast.makeText(getActivity(),"用户名和密码输入不正确，请重新输入",Toast.LENGTH_LONG).show();
        }

    }
}
