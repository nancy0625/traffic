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
 * Created by asus on 2017/12/26.
 */

public class MyAdapter11 extends BaseAdapter {

    private List<Car> list;
    private LayoutInflater mInflater;


    public MyAdapter11(List<Car> list, Context context) {
        this.list = list;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.project11_item, null);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.text001);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.text002);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.text003);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.text004);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

            Car car = list.get(i);

            viewHolder.textView1.setText(car.getID()+"" );

            viewHolder.textView2.setText(car.getIntime() );

            viewHolder.textView3.setText(car.getOuttime() );

            viewHolder.textView4.setText(car.getMoney()+"" );

        return view;
    }

    class ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;

    }
}
