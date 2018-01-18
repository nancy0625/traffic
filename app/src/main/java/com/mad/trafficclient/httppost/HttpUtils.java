package com.mad.trafficclient.httppost;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by asus on 2018/1/12.
 */

public class HttpUtils {
    public static int status;
    public static String send(String url,String json){
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setRequestProperty("Content-type","json");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(json.getBytes("utf-8"));
            outputStream.flush();
            InputStream is = httpURLConnection.getInputStream();
            if (httpURLConnection.getResponseCode() == 200){
                status = 200;
                int len = 0;
                byte data[] = new byte[1024];
                while ((len = is.read(data))!=-1){
                    bao.write(data,0,len);
                }
                is.close();
            }else {
                status = 404;
            }
        }catch (SocketTimeoutException e1){
            e1.printStackTrace();
            status = 901;
        }catch (ConnectTimeoutException e2){
            e2.printStackTrace();
            status = 902;
        }catch (InterruptedIOException e3){
            e3.printStackTrace();
            status = 903;
        }
        catch (IOException e) {
            e.printStackTrace();
            status = 904;
        }


        return new String(bao.toByteArray());
    }
}
