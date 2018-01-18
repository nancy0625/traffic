package com.mad.trafficclient;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.trafficclient.db.MyDataBaseHelper;

public class AndroidProject02Fragment extends Fragment {

    private MyDataBaseHelper dataBaseHelper;

    private TextView tvTitle1;
    private TextView wendu;
    private TextView tvTitle2;
    private TextView shidu;
    private TextView tvTitle3;
    private TextView guangzhao;
    private TextView tvTitle4;
    private TextView huatan;
    private TextView tvTitle5;
    private TextView pm;
    private TextView tvTitle6;
    private TextView daolu;
    private  Notification notification;
    private View view01;
    private AlertDialog dialog;
    private AlertDialog.Builder builder1;
    private NotificationManager notificationManager;
    private TextView mLoadingTv;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Diog();
            Sense sense = (Sense) msg.obj;
            update(sense.getTemperature(), sense.getHumidity(), sense.getLightIntensity(), sense.getCO2(), sense.getPm(), sense.getStatus());
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_project02, null);
    }

    public void wendu(View view) {
        Toast.makeText(getActivity(), "温度", Toast.LENGTH_SHORT).show();
    }

    public void shidu(View view) {
        Toast.makeText(getActivity(), "湿度", Toast.LENGTH_SHORT).show();
    }

    public void guangzhao(View view) {
        Toast.makeText(getActivity(), "光照", Toast.LENGTH_SHORT).show();
    }

    public void huatan(View view) {
        Toast.makeText(getActivity(), "二氧化碳", Toast.LENGTH_SHORT).show();
    }

    public void pm(View view) {
        Toast.makeText(getActivity(), "PM2.5", Toast.LENGTH_SHORT).show();
    }

    public void daolu(View view) {

        Toast.makeText(getActivity(), "道路", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle1 = (TextView) view.findViewById(R.id.tv_title1);
        wendu = (TextView) view.findViewById(R.id.wendu);
        tvTitle2 = (TextView) view.findViewById(R.id.tv_title2);
        shidu = (TextView) view.findViewById(R.id.shidu);
        tvTitle3 = (TextView) view.findViewById(R.id.tv_title3);
        guangzhao = (TextView) view.findViewById(R.id.guangzhao);
        tvTitle4 = (TextView) view.findViewById(R.id.tv_title4);
        huatan = (TextView) view.findViewById(R.id.huatan);
        tvTitle5 = (TextView) view.findViewById(R.id.tv_title5);
        pm = (TextView) view.findViewById(R.id.pm);
        tvTitle6 = (TextView) view.findViewById(R.id.tv_title6);
        daolu = (TextView) view.findViewById(R.id.daolu);
        dataBaseHelper = new MyDataBaseHelper(getActivity(), "Sense.db", null, 1);


        builder1 = new AlertDialog.Builder(getActivity());
        dialog = builder1.create();
        view01 = LayoutInflater.from(getActivity()).inflate(R.layout.project02_fragement_dialog, null);
        ImageView imageView = (ImageView)view01.findViewById(R.id.spinnerImage) ;
        AnimationDrawable spinner = (AnimationDrawable)imageView.getBackground() ;
        spinner.start();

        dialog.setView(view01);

        Intent intent = new Intent(getActivity(), MyService.class);
        getActivity().startService(intent);
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);



      /*  Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };
        timer.schedule(timerTask,10000,5000);*/
        replace();

    }

    public void replace() {
        new Thread() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {

                    Sense sense = dataBaseHelper.select();
                    Message message = Message.obtain();
                    message.obj = sense;
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                }


            }
        }.start();

    }
    private void Diog(){


        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        /*dialog.getWindow().getAttributes().dimAmount = 0.5f;*/
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
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
            }
        }.start();

    }


    public void update(int t, int h, int l, int c, int p, int s) {
        if (t > 40) {
            sendNotification("Temperature", "异常", t + "", "40",1);

            wendu.setBackgroundResource(R.drawable.red);

        } else {
            notificationManager.cancel(1);
            wendu.setBackgroundResource(R.drawable.green);

        }
        if (h > 60) {
            sendNotification("humidity", "异常", h + "", "60",2);
            shidu.setBackgroundResource(R.drawable.red);

        } else {
            notificationManager.cancel(2);
            shidu.setBackgroundResource(R.drawable.green);
        }
        if (l > 2000) {
            sendNotification("LightIntensity", "异常", l + "", "2000",3);
            guangzhao.setBackgroundResource(R.drawable.red);

        } else {
            notificationManager.cancel(3);
            guangzhao.setBackgroundResource(R.drawable.green);
        }
        if (c > 3000) {
            sendNotification("CO2", "异常", c + "", "3000",4);
            huatan.setBackgroundResource(R.drawable.red);

        } else {
            notificationManager.cancel(4);
            huatan.setBackgroundResource(R.drawable.green);
        }
        if (p > 35) {
            sendNotification("PM2.5", "异常", p + "", "35",5);
            pm.setBackgroundResource(R.drawable.red);

        } else {
            notificationManager.cancel(5);
            pm.setBackgroundResource(R.drawable.green);
        }
        if (s >= 3) {
            sendNotification("RoadStatus", "堵塞", s + "", "3",6);
            daolu.setBackgroundResource(R.drawable.red);

        } else {
            notificationManager.cancel(6);
            daolu.setBackgroundResource(R.drawable.green);
        }
        wendu.setText(t + "");
        shidu.setText(h + "");
        guangzhao.setText(l + "");
        huatan.setText(c + "");
        pm.setText(p + "");
        daolu.setText(s + "");
    }

    private void sendNotification(String title, String type, String nowValue, String maxValue,int num) {

        //实例化通知管理器

        //实例化通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        builder.setContentTitle(title);//设置标题
        builder.setContentText("当前超标的数据类别:  " + type + "     当前值:" + nowValue + "   阈值:" + maxValue);//设置通知内容
        builder.setDefaults(NotificationCompat.PRIORITY_DEFAULT);//设置通知方式
        builder.setAutoCancel(true);//点击通知后，状态栏自动删除通知；
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setContentIntent(PendingIntent.getActivity(getActivity(), 0x12, new Intent(getActivity(), AndroidProject02Fragment.class), 0));//点击通知后，将要启动的程序组件对应的PendingIntent；

        notification = builder.build();
        //发送通知
        notificationManager.notify(num, notification);
        /*if (Integer.valueOf(nowValue)>Integer.valueOf(maxValue)){
            notificationManager.cancel(num);
        }*/




    }


}
