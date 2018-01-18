package com.mad.trafficclient;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.animation.AnimationSet;
import android.widget.TextView;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;

public class TestfraFragment extends Fragment implements View.OnClickListener {

    private TextView tt1;
    private TextView tt2;
    private TextView tt3;
    private TextView tt4;
    private RelativeLayout relativeLayout;
    private TextView tt5;
    private TextView tt6;
    private TextView tt7;
    private TextView tt8;
    private TextView tt9;
    private TextView tt10;
    private Thread mThread1,mThread2,mThread3,mThread4;
    private AnimatorSet animatorSet1,animatorSet2,animatorSet3,animatorSet4;
    private ObjectAnimator animator1,animator2,animator3,animator4,animator5,animator6;
    private Random mRandom = new Random();
    private LinkedList<Integer> mList;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 001:
                    animatorSet1.play(animator3).with(animator6).with(animator1);
                    animatorSet1.setDuration(5000);
                    animatorSet1.start();
                    setT2();
                    break;
                case 002:
                    animatorSet2.play(animator3).with(animator4).with(animator1);
                    animatorSet2.setDuration(5000);
                    animatorSet2.start();
                    setT3();

                    break;
                case 003:
                    animatorSet3.play(animator3).with(animator5).with(animator1);
                    animatorSet3.setDuration(5000);
                    animatorSet3.start();
                    setT4();

                    break;
                case 004:
                    animatorSet4.play(animator3).with(animator6).with(animator1);
                    animatorSet4.setDuration(5000);
                    animatorSet4.start();


                    break;

            }
            update();
        }
    };
    private void update(){
        tt5.setText("空气温度");
        tt7.setText("空气湿度");
        tt9.setText("CO2");
        tt6.setText(mList.get(0)+"");
        tt8.setText(mList.get(1)+"");
        tt10.setText(mList.get(2)+"");
        if (Integer.valueOf(mList.get(0))>300){
            tt6.setBackgroundResource(R.drawable.red);
        }else {
            tt6.setBackgroundResource(R.drawable.green);
        }
        if (Integer.valueOf(mList.get(1))>500){
            tt8.setBackgroundResource(R.drawable.red);
        }else {
            tt8.setBackgroundResource(R.drawable.green);
        }
        if (Integer.valueOf(mList.get(2))>200){
            tt10.setBackgroundResource(R.drawable.red);
        }else {
            tt10.setBackgroundResource(R.drawable.green);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.testfra, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tt1 = (TextView) view.findViewById(R.id.tt1);
        tt2 = (TextView) view.findViewById(R.id.tt2);
        tt3 = (TextView) view.findViewById(R.id.tt3);
        tt4 = (TextView) view.findViewById(R.id.tt4);
        view.findViewById(R.id.btn_tt).setOnClickListener(this);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        tt5 = (TextView) view.findViewById(R.id.tt5);
        tt6 = (TextView) view.findViewById(R.id.tt6);
        tt7 = (TextView) view.findViewById(R.id.tt7);
        tt8 = (TextView) view.findViewById(R.id.tt8);
        tt9 = (TextView) view.findViewById(R.id.tt9);
        tt10 = (TextView) view.findViewById(R.id.tt10);
        mList = new LinkedList<Integer>();
        animatorSet1 = new AnimatorSet();
        animatorSet2 = new AnimatorSet();
        animatorSet3 = new AnimatorSet();
        animatorSet4 = new AnimatorSet();

    }
    private void ram(){
        new Thread(){
            @Override
            public void run() {
               while (true){
                   if (mList.size()<=18){
                       mList.add(mRandom.nextInt(6)*10);
                       mList.add(mRandom.nextInt(10)*100);
                       mList.add(mRandom.nextInt(8)*100);
                   }else {
                       mList.poll();
                   }
                   try {
                       Thread.sleep(3000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        }.start();
    }
    private  void alpha(View view){
        animator1 = ObjectAnimator.ofFloat(view,"alpha",1f,0f,1f);
        animator1.setDuration(5000);
    }
    private  void scale(View view){
        animator2 = ObjectAnimator.ofFloat(view,"scaleX",1f,0.3f,1f);
        animator2.setDuration(5000);
    }
    private  void rarote(View view){
        animator3 = ObjectAnimator.ofFloat(view,"Rotation",0f,360f);
        animator3.setDuration(5000);
    }
    private  void tran(View view){
        float y = view.getTranslationY();
        animator4 = ObjectAnimator.ofFloat(view,"TranslationX",y,300,y);
        animator4.setDuration(5000);
    }
    private  void tran1(View view){
        float y = view.getTranslationY();
        animator5 = ObjectAnimator.ofFloat(view,"TranslationX",y,-300,y);
        animator5.setDuration(5000);
    }

    private  void tran2(View view){
        float x = view.getTranslationX();
        animator6 = ObjectAnimator.ofFloat(view,"TranslationY",x,300,150);
        animator6.setDuration(5000);
    }
    private void setT1(){
        mThread1 = new Thread(){
            @Override
            public void run() {
                alpha(tt1);
                scale(tt1);
                rarote(tt1);
                tran2(tt1);
                handler.sendEmptyMessage(001);
            }
        };
        mThread1.start();
    }
    private void setT2(){
        mThread2 = new Thread(){
            @Override
            public void run() {
                alpha(tt2);
                scale(tt2);
                rarote(tt2);
                tran(tt2);
                handler.sendEmptyMessage(002);
            }
        };
        mThread2.start();
    }
    private void setT3(){
        mThread3 = new Thread(){
            @Override
            public void run() {
                alpha(tt3);
                scale(tt3);
                rarote(tt3);
                tran1(tt3);
                handler.sendEmptyMessage(003);
            }
        };
        mThread3.start();
    }
    private void setT4(){
        mThread4 = new Thread(){
            @Override
            public void run() {
                alpha(tt4);
                scale(tt4);
                rarote(tt4);
                tran2(tt4);
                handler.sendEmptyMessage(004);
            }
        };
        mThread4.start();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tt:
               ram();
                setT1();
                break;
        }
    }
}
