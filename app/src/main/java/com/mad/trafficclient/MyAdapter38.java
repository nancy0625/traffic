package com.mad.trafficclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus on 2017/12/28.
 */

public class MyAdapter38 extends BaseAdapter {
    private List<String> list;
    private LayoutInflater layoutInflater;
    private int it[]={1,2,3,4};
    private int item;


    public MyAdapter38(List<String> list, Context context) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
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
        if (view==null){
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.project38_item,null);
            viewHolder.textView1 = (TextView) view.findViewById(R.id.tv1_project38);
            viewHolder.textView2 = (TextView) view.findViewById(R.id.tv2_project38);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        item = it[i];

        if (Integer.valueOf(list.get(i))>4){
            viewHolder.textView1.setBackgroundResource(R.drawable.biyadi);
            viewHolder.textView2.setText("第"+item+""+"号路" +
                    "红色饱和");


        }else if (Integer.valueOf(list.get(i))>=3){
            viewHolder.textView1.setBackgroundResource(R.drawable.baoma);
            viewHolder.textView2.setText("第"+item+""+"号路" +
                    "一般拥挤");

        }else {
            viewHolder.textView1.setBackgroundResource(R.drawable.benchi);
            viewHolder.textView2.setText("第"+item+""+"号路" +
                    "畅通");
        }



        return view;
    }
    class ViewHolder{
     public    TextView textView1;
     public    TextView textView2;
    }
}
