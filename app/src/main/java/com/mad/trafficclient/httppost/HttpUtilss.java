package com.mad.trafficclient.httppost;

import android.content.Context;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by asus on 2018/1/5.
 */

public class HttpUtilss {
  public static  int status;


    public static String send(String url,String json){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-type","application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(json.getBytes("utf-8"));
            outputStream.flush();
            InputStream is = httpURLConnection.getInputStream();
            if (httpURLConnection.getResponseCode() == 200){
                status = 200;

                int len = 0;
                byte [] data = new byte[1024];
                while ((len = is.read(data))!= -1){
                    bao.write(data,0,len);
                }
                is.close();
            }else {
                status = 404;
            }

        }catch (ConnectTimeoutException e1){
            e1.printStackTrace();
            status = 901;
        }catch (SocketTimeoutException e2){
            e2.printStackTrace();
            status = 901;
        }catch (InterruptedIOException e3){
            e3.printStackTrace();
            status = 902;
        }
        catch (IOException e4) {
            e4.printStackTrace();
            status = 903;
        }

        return new String(bao.toByteArray());
    }

}
