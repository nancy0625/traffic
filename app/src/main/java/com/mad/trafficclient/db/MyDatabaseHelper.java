package com.mad.trafficclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.textservice.TextInfo;
import android.widget.Toast;

import com.mad.trafficclient.Sense;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 2018/1/4.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Context mContext;
    final String CREATE_SENSE = " create table sense (" +
            " id integer primary key autoincrement," +
            " tem integer," +
            " hum integer," +
            " light integer," +
            " CO2 integer," +
            " pm integer," +
            " status integer," +
            " timer integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SENSE);
        Log.i("创建", "成功");

    }

    public void setData(double temm, double humm, double lig, double coo, double pmm, double sta) {
        db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        ContentValues updateValues = new ContentValues();
        DecimalFormat df = new DecimalFormat("#.##");
        updateValues.put("tem", df.format(temm));
        updateValues.put("hum", df.format(humm));
        updateValues.put("light", df.format(lig));
        updateValues.put("CO2", df.format(coo));
        updateValues.put("pm", df.format(pmm));
        updateValues.put("status", df.format(sta));
        updateValues.put("timer", sdf.format(now));
        db.insert("sense", null, updateValues);
        Log.i("rrr", "插入成功");


    }
    public List<Sense> selectAvg(int num){
        List<Sense> list = new ArrayList<Sense>();
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select tem,hum,light,CO2,pm,status,timer from sense" +
                " group by timer order by 1 desc" +
                " limit "+num,null);
        cursor.moveToFirst();
        Log.i("ccc",cursor.getCount()+"");
        do {
            Sense sense = new Sense();
            sense.setTem(cursor.getInt(0));
            sense.setHum(cursor.getInt(1));
            sense.setLig(cursor.getInt(2));
            sense.setCo(cursor.getInt(3));
            sense.setPm(cursor.getInt(4));
            sense.setStatus(cursor.getInt(5));
            sense.setTimer(cursor.getString(6));
            list.add(sense);

        }while (cursor.moveToNext());
        cursor.close();

        return list;

    }

    public void del() {
        db = this.getWritableDatabase();
        Date now = new Date();
        long now1 = now.getTime();
        long before = now1 - 8 * 60 * 1000;
        Date beforeDate = new Date(before);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        db.execSQL("delete from sense where timer<'" + sdf.format(beforeDate) + "'");
        Log.i("删除", "成功");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
