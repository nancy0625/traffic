package com.mad.trafficclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 2017/12/26.
 */

public class MyAdapter20 extends BaseAdapter {

    private List<Record> list;
    private LayoutInflater mInflater;


    public MyAdapter20(List<Record> list, Context context) {
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
            view = mInflater.inflate(R.layout.project20_item, null);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.text201);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.text202);
            viewHolder.textView3 = (TextView) view.findViewById(R.id.text203);
            viewHolder.textView4 = (TextView) view.findViewById(R.id.text204);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

            Record record = list.get(i);

            viewHolder.textView1.setText(record.getID()+"" );

            viewHolder.textView2.setText(record.getIntime() );

            viewHolder.textView3.setText(record.getOuttime() );

            viewHolder.textView4.setText(record.getMoney()+"" );

        return view;
    }

    class ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;

    }
}
