package com.mad.trafficclient;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Random;

public class TestttFragment extends Fragment implements View.OnClickListener {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv10;
    private Thread mThread1,mThread2,mThread3,mThread4;
    private Random mRandom = new Random();
    private LinkedList<Integer> mList = new LinkedList<Integer>();
    private AnimatorSet animatorSet1,animatorSet2,animatorSet3,animatorSet4;
    private ObjectAnimator animator1,animator2,animator3,animator4,animator5,animator6;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    animatorSet1.play(animator3).with(animator6).with(animator1);
                    animatorSet1.setDuration(5000);
                    animatorSet1.start();
                    t2();
                    break;
                case 2:
                    animatorSet2.play(animator3).with(animator4).with(animator1);
                    animatorSet2.setDuration(5000);
                    animatorSet2.start();

                    t3();

                    break;
                case 3:
                    animatorSet3.play(animator3).with(animator5).with(animator1);
                    animatorSet3.setDuration(5000);
                    animatorSet3.start();
                    t4();
                    break;
                case 4:
                    animatorSet4.play(animator3).with(animator6).with(animator1);
                    animatorSet4.setDuration(5000);
                    animatorSet4.start();

                    break;
            }
            update();
        }
    };
    private void update(){
        tv5.setText("CO2");
        tv7.setText("温度");
        tv9.setText("湿度");
        tv6.setText(mList.get(0)+"");
        tv8.setText(mList.get(1)+"");
        tv10.setText(mList.get(2)+"");
        if (Integer.valueOf(mList.get(0))>200){
            tv6.setBackgroundResource(R.drawable.red);
        }else {
            tv6.setBackgroundResource(R.drawable.green);
        }
        if (Integer.valueOf(mList.get(1))>300){
            tv8.setBackgroundResource(R.drawable.red);
        }else {
            tv8.setBackgroundResource(R.drawable.green);
        }
        if (Integer.valueOf(mList.get(2))>400){
            tv10.setBackgroundResource(R.drawable.red);
        }else {
            tv10.setBackgroundResource(R.drawable.green);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.testtt, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv1 = (TextView) view.findViewById(R.id.tv_1);
        tv2 = (TextView) view.findViewById(R.id.tv_2);
        tv3 = (TextView) view.findViewById(R.id.tv_3);
        tv4 = (TextView) view.findViewById(R.id.tv_4);
        view.findViewById(R.id.btn_tv).setOnClickListener(this);
        tv5 = (TextView) view.findViewById(R.id.tv_5);
        tv6 = (TextView) view.findViewById(R.id.tv_6);
        tv7 = (TextView) view.findViewById(R.id.tv_7);
        tv8 = (TextView) view.findViewById(R.id.tv_8);
        tv9 = (TextView) view.findViewById(R.id.tv_9);
        tv10 = (TextView) view.findViewById(R.id.tv_10);
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
                    if (mList.size()<=10){
                        mList.add(mRandom.nextInt(6)*10);
                        mList.add(mRandom.nextInt(10)*100);
                        mList.add(mRandom.nextInt(4)*1000);
                    }else {
                        mList.poll();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private void alpha(View view){
        animator1 = ObjectAnimator.ofFloat(view,"alpha",1f,0f,1f);
        animator1.setDuration(5000);
    }
    private void scale(View view){
        animator2 = ObjectAnimator.ofFloat(view,"scaleX",1f,0f,1f);
        animator2.setDuration(5000);
    }
    private void rorote(View view){
        animator3 = ObjectAnimator.ofFloat(view,"Rotation",1f,0f,1f);
        animator3.setDuration(5000);
    }
    private void tran(View view){
        float y = view.getTranslationY();
        animator4 = ObjectAnimator.ofFloat(view,"TranslationX",y,300,y);
        animator4.setDuration(5000);
    }
    private void tran1(View view){
        float y = view.getTranslationY();
        animator5 = ObjectAnimator.ofFloat(view,"TranslationX",y,-300,y);
        animator5.setDuration(5000);
    }
    private void tran2(View view){
        float x = view.getTranslationX();
        animator6 = ObjectAnimator.ofFloat(view,"TranslationY",x,300,150);
        animator6.setDuration(5000);
    }
    private void t1(){
        mThread1 = new Thread(){
            @Override
            public void run() {
                alpha(tv1);
                scale(tv1);
                rorote(tv1);
                tran2(tv1);
                handler.sendEmptyMessage(1);
            }
        };
        mThread1.start();
    }
    private void t2(){
        mThread2 = new Thread(){
            @Override
            public void run() {
                alpha(tv2);
                scale(tv2);
                rorote(tv2);
                tran(tv2);
                handler.sendEmptyMessage(2);
            }
        };
        mThread2.start();
    }
    private void t3(){
        mThread3 = new Thread(){
            @Override
            public void run() {
                alpha(tv3);
                scale(tv3);
                rorote(tv3);
                tran1(tv3);
                handler.sendEmptyMessage(3);
            }
        };
        mThread3.start();
    }
    private void t4(){
        mThread4 = new Thread(){
            @Override
            public void run() {
                alpha(tv4);
                scale(tv4);
                rorote(tv4);
                tran2(tv4);
                handler.sendEmptyMessage(4);
            }
        };
        mThread4.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tv:
               ram();
                t1();

                break;
        }
    }
}
