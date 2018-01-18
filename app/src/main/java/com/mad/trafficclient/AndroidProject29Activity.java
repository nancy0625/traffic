package com.mad.trafficclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.mad.trafficclient.activity.Activity_Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndroidProject29Activity extends Activity implements View.OnClickListener {
    private SharedPreferences sp;
    private ProgressDialog dialog;
    private boolean status;
    private String user,pswd;
    private boolean flag = false;

    private String Str_name,Str_psw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_project29);
        findViewById(R.id.btnregister_project29).setOnClickListener(this);
        findViewById(R.id.btnlogin_project29).setOnClickListener(this);
        findViewById(R.id.btn_internet_project29).setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在登录，请稍后");
        sp = getSharedPreferences("config",MODE_PRIVATE);
        isRemember();
        getCbloginProject29().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Str_name.length()>0){
                    dialog.show();
                   new Thread(){
                       @Override
                       public void run() {
                           try {
                               Thread.sleep(3000);
                               dialog.dismiss();
                               sta();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                   }.start();


                }
            }
        });





    }
    public void sta(){
        Intent intent = new Intent(this, Activity_Main.class);
        startActivity(intent);
    }


    private EditText getEtuserProject29(){
        return (EditText) findViewById(R.id.etuser_project29);
    }

    private EditText getEtpwdProject29(){
        return (EditText) findViewById(R.id.etpwd_project29);
    }

    private CheckBox getCbloginProject29(){

        return (CheckBox) findViewById(R.id.cblogin_project29);
    }

    private CheckBox getCbpswProject29(){
        return (CheckBox) findViewById(R.id.cbpsw_project29);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnregister_project29:

                break;
            case R.id.btnlogin_project29:
                user = getEtuserProject29().getText().toString().trim();
                pswd = getEtpwdProject29().getText().toString().trim();

                if (getCbpswProject29().isChecked()){

                    saveLogin(user,pswd);
                   getCbpswProject29().setChecked(true);

                }else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.commit();
                }
                checkuser(user,pswd);

                break;
            case R.id.btn_internet_project29:

                break;
        }
    }
    public boolean checkuser(String username, String pass){
        String userpreg = "[A-Za-z]{6,12}$";
        String passw = "^[A-Za-z][A-Za-z0-9]{6,12}$";
        Pattern p = Pattern.compile(userpreg);
        Pattern p2 = Pattern.compile(passw);
        Matcher m = p.matcher(username);
        Matcher m2 = p2.matcher(pass);
        boolean isValid = m.matches();
        boolean isok = m2.matches();
        if(isValid&&isok){
            flag = true;
            Toast.makeText(this, "登录成功",Toast.LENGTH_LONG).show();
        }else {
            flag = false;
            Toast.makeText(this,"用户名或密码格式不正确，请重新输入",Toast.LENGTH_SHORT).show();
        }

        return flag;

    }


    private void isRemember(){

            Str_name = sp.getString("username","");
            Str_psw = sp.getString("password","");
        if (Str_psw.length()>0&&Str_name.length()>0){
            getEtuserProject29().setText(Str_name);
            getEtpwdProject29().setText(Str_psw);
            getCbpswProject29().setChecked(true);

        }

    }
    private void saveLogin(final String name, final String ppd){

           status = sp.getBoolean("status",false);
            getCbpswProject29().setChecked(status);
            getCbpswProject29().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("status",true);
                    editor.putString("username",name);
                    editor.putString("password",ppd);
                    editor.commit();


                }
            });

    }
}
