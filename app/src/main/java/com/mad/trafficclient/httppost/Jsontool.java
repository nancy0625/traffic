package com.mad.trafficclient.httppost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by asus on 2018/1/5.
 */

public class Jsontool {
    public List<String> parseList(String json,int num){
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String js = jsonObject.getString("data");
            JSONArray jsonArray = new JSONArray(js);
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(num);
            Iterator<String> iterator = jsonObject1.keys();
            while (iterator.hasNext()){
                String key= iterator.next();
                String value = jsonObject1.getString(key);
                list.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<String> parseList2(String json){
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(json);


            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()){
                String key= iterator.next();
                String value = jsonObject.getString(key);
                list.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
