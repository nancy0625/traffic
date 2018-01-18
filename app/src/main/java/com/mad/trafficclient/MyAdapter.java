package com.mad.trafficclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/1/8.
 */

public class MyAdapter extends BaseAdapter {
    private List<Car> list = new ArrayList<Car>();
    private LayoutInflater mInflater;

    public MyAdapter(List<Car> list, Context context) {
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
            convertView = mInflater.inflate(R.layout.project11_item,null);
            viewHolder.tv1 = (TextView)convertView.findViewById(R.id.tv1101);
            viewHolder.tv2 = (TextView)convertView.findViewById(R.id.tv1102);
            viewHolder.tv3 = (TextView)convertView.findViewById(R.id.tv1103);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Car car = list.get(position);
        viewHolder.tv1.setText(car.getCarId()+"");
        viewHolder.tv2.setText(car.getNowDate()+"");
        viewHolder.tv3.setText(car.getMoney()+"");
        return convertView;
    }
    class ViewHolder{
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
    }
}
