package com.pross;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class HttpThread extends Thread {

    private String url;
    private ArrayList<NameValuePair> data;
    private Handler handler;

    protected HttpThread(String url, ArrayList<NameValuePair> data,Handler handler) {
        this.url = url;
        this.data = data;
        this.handler = handler;
    }

    @Override
    public void run() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            HttpEntity requestEntity = new UrlEncodedFormEntity(data);
            httpPost.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                String result = reader.readLine();

                //打印返回
                Log.d("HTTP", "POST:" + result);
                Message message = Message.obtain();
                message.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("Post", result);
                message.setData(bundle);
                handler.sendMessage(message);
            } else {
                Log.d("HTTPError", "POST:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
