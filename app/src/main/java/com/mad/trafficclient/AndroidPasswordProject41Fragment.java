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

import java.util.regex.Pattern;

public class AndroidPasswordProject41Fragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private FragmentManager manager;
    private SharedPreferences sp;
    private boolean flog = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_password_project41, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) view.findViewById(R.id.textView);
        view.findViewById(R.id.btn_internet_project29).setOnClickListener(this);
        view.findViewById(R.id.btn_back_password).setOnClickListener(this);
        manager = getFragmentManager();
        sp = getActivity().getSharedPreferences("config",getActivity().MODE_PRIVATE);
    }

    private EditText getEtusernameProject41() {
        return (EditText) getView().findViewById(R.id.etusername_project41);
    }

    private EditText getEtePwdMailProject41() {
        return (EditText) getView().findViewById(R.id.ete_pwd_mail_project41);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_internet_project29:
                String name = sp.getString("user","");
                String pass = sp.getString("pass","");
                check();
                if (flog){
                    Toast.makeText(getActivity(),"用户名为："+name+"密码为："+pass,Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_back_password:
                FragmentTransaction ft = manager.beginTransaction();
                AndroidProject41Fragment fr = new AndroidProject41Fragment();
                ft.replace(R.id.maincontent, fr);
                ft.commit();
                break;
        }
    }
    public void check(){
        String name = getEtusernameProject41().getText().toString().trim();
        String email = getEtePwdMailProject41().getText().toString().trim();

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

    }
}
