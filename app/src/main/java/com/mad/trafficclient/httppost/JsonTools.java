package com.mad.trafficclient.httppost;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by asus on 2018/1/4.
 */

public class JsonTools {
    List<String> list = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();

    public List<String> parseList(String json,int num){
        try {

            JSONObject jsonObject = new JSONObject(json);
            String jss = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(jss);
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(num);
            Log.i("ddd",jsonObject1+"");
            Iterator<String> iterator = jsonObject1.keys();
            while(iterator.hasNext()){
                String key = iterator.next();
                String value = jsonObject1.getString(key);
                list.add(value);
                Log.i("ddd",value);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<String> parseList2(String json){
        try {

            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iterator = jsonObject.keys();
            while(iterator.hasNext()){
                String key = iterator.next();
                String value = jsonObject.getString(key);
                list.add(value);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
