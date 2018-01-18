package com.mad.trafficclient;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.mad.trafficclient.activity.Activity_Main;

public class AndroidProject41Activity extends Activity implements View.OnClickListener {

    private TextView textView;
    private TextView textView2;
    private TextView btnregisterProject41;
    private TextView btnfindProject41;
    private SharedPreferences spp;
    private FragmentManager manager;
    private long mExitTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_project41);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        btnregisterProject41 = (TextView) findViewById(R.id.btnregister_project41);
        btnfindProject41 = (TextView) findViewById(R.id.btnfind_project41);
        findViewById(R.id.btn_internet_project29).setOnClickListener(this);
        spp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        manager = getFragmentManager();

        if (spp.getString("usernames","").length()>0){
            sta(Activity_Main.class);
        }

    }

    private EditText getEtuserProject29(){
        return (EditText) findViewById(R.id.etuser_project29);
    }

    private EditText getEtpwdProject29(){
        return (EditText) findViewById(R.id.etpwd_project29);
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
                    Toast.makeText(this,"用户名或者密码错误，请重新输入",Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this,cl);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            if ((System.currentTimeMillis()-mExitTime)<2000){
                System.exit(0);
            }else {
                Toast.makeText(this,"再按一次退出系统",Toast.LENGTH_LONG).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
