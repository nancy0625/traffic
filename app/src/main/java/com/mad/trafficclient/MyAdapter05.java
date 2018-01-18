package com.mad.trafficclient;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 2018/1/9.
 */

public class MyAdapter05 extends BaseAdapter {
  private List<Sense> list;
    private LayoutInflater mInflater;
    public String item;

    public MyAdapter05(Context context,List<Sense> list) {
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment5_project_item,null);
            viewHolder.tv1 = (TextView)convertView.findViewById(R.id.t1_p5);
            viewHolder.tv2 = (TextView)convertView.findViewById(R.id.t2_p5);
            viewHolder.tv3 = (TextView)convertView.findViewById(R.id.t3_p5);
            viewHolder.tv4 = (TextView)convertView.findViewById(R.id.t4_p5);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();

        }
        Log.i("item",item);
        Sense sense = list.get(position);
        if (item.equals("空气温度")){
            viewHolder.tv1.setText(item);

            viewHolder.tv2.setText(sense.getTem()+"");
            if (sense.getTem()>40){
                viewHolder.tv3.setText("异常");
            }else {
                viewHolder.tv3.setText("正常");
            }

            viewHolder.tv4.setText(sense.getTimer()+"");

        }else if (item.equals("空气湿度")){
            viewHolder.tv1.setText(item);
            viewHolder.tv2.setText(sense.getHum()+"");
            if (sense.getHum()>60){
                viewHolder.tv3.setText("异常");
            }else {
                viewHolder.tv3.setText("正常");
            }
            viewHolder.tv4.setText(sense.getTimer()+"");
        }else if (item.equals("光照强度")){
            viewHolder.tv1.setText(item);
            viewHolder.tv2.setText(sense.getLig()+"");
            if (sense.getLig()>2000){
                viewHolder.tv3.setText("异常");
            }else {
                viewHolder.tv3.setText("正常");
            }
            viewHolder.tv4.setText(sense.getTimer()+"");
        }else if (item.equals("CO2")){
            viewHolder.tv1.setText(item);
            viewHolder.tv2.setText(sense.getCo()+"");
            if (sense.getCo()>3000){
                viewHolder.tv3.setText("异常");
            }else {
                viewHolder.tv3.setText("正常");
            }
            viewHolder.tv4.setText(sense.getTimer()+"");
        }else if (item.equals("PM2.5")){
            viewHolder.tv1.setText(item);
            viewHolder.tv2.setText(sense.getPm()+"");
            if (sense.getPm()>35){
                viewHolder.tv3.setText("异常");
            }else {
                viewHolder.tv3.setText("正常");
            }
            viewHolder.tv4.setText(sense.getTimer()+"");
        }

        Log.i("itessm",sense.getCo()+"");


        return convertView;
    }
    class ViewHolder{
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public TextView tv4;
    }
}
