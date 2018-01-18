package com.mad.trafficclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class AndroidRegisterProject41Fragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private FragmentManager manager;
    private SharedPreferences sp;
    private boolean flog = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_register_project41, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        view.findViewById(R.id.btn_sub_project41).setOnClickListener(this);
        view.findViewById(R.id.btn_back_resgister).setOnClickListener(this);
        manager = getFragmentManager();
        sp = getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);
    }

    private EditText getEtuserProject41(){
        return (EditText) getView().findViewById(R.id.etuser_project41);
    }

    private EditText getEtemailProject41(){
        return (EditText) getView().findViewById(R.id.etemail_project41);
    }

    private EditText getEtpwdProject41(){
        return (EditText) getView().findViewById(R.id.etpwd_project41);
    }

    private EditText getEtPpwdProject41(){
        return (EditText) getView().findViewById(R.id.et_ppwd_project41);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub_project41:
                check();

                break;
            case R.id.btn_back_resgister:
               FragmentTransaction ft = manager.beginTransaction();
                AndroidProject41Fragment fr = new AndroidProject41Fragment();
                ft.replace(R.id.maincontent,fr);
                ft.commit();
                break;
        }
    }
    public void check(){
        String name = getEtuserProject41().getText().toString().trim();
        String email = getEtemailProject41().getText().toString().trim();
        String psw = getEtpwdProject41().getText().toString().trim();
        String ppsw = getEtPpwdProject41().getText().toString().trim();
        String username = "[a-zA-Z]{4,25}";
        String emm = "\\d{6,25}";

        if (! Pattern.matches(username,name)){
            Toast.makeText(getActivity(),"用户名少于4位，请检查输入",Toast.LENGTH_SHORT).show();
            flog=false;
        }else {
            flog = true;
        }
        if (!Pattern.matches(emm,email)){
            Toast.makeText(getActivity(),"邮箱输入少于6位，请检查输入",Toast.LENGTH_SHORT).show();
            flog=false;
        }else {
            flog = true;
        }
        if (!Pattern.matches(emm,psw)){
            Toast.makeText(getActivity(),"密码输入少于6位，请检查输入",Toast.LENGTH_SHORT).show();
            flog=false;
        }else {
            flog = true;
        }
        if (!psw.equals(ppsw)){
            Toast.makeText(getActivity(),"两次密码不一致，请检查输入",Toast.LENGTH_SHORT).show();
            flog=false;
        }else {
            flog = true;
        }
        if (flog){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("user",name);
            editor.putString("email",email);
            editor.putString("pass",ppsw);
            editor.commit();
            Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(),"保存失败，输入格式不正确",Toast.LENGTH_SHORT).show();
        }

    }
}
