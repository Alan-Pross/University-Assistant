package com.pross;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PEReport;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class A2Thread extends Thread {

    final public static String a2 = "http://94.191.42.64:1234/xzs/transa2";
    public static List<String> ListPE = new ArrayList();

    @Override
    public void run() {

        try {
            //如果查询队列为空，加入一个110的空查询
            if(!(ListPE.size() > 0)){
                ListPE.add("110");
            } else{
                MainActivity.print("A2Thread:开始电费查询" + ListPE.get(0));
            }

            //获得结果
            PEReport pr = HtmlUnit.a2get(ListPE.get(0));

            //从查询队列中删除
            ListPE.remove(0);

            //构建上传参数
            ArrayList<NameValuePair> NVPdata = new ArrayList<NameValuePair>();
            NameValuePair NVPxh = new BasicNameValuePair("xh", ListPE.get(0));
            NVPdata.add(NVPxh);
            NVPdata.addAll(pr.toNVP());

            //开始上传请求
            MainActivity.print("A2Thread:上传" + NVPdata.toString());
            new HttpThread(a2,NVPdata,new Handler(){
                @Override
                public void handleMessage(Message result) {

                    //获得json对象
                    JSONObject js = JSONObject.parseObject(result.getData().getString("Post"));

                    //如果返回的查询不为110，此查询请求加入查询列表
                    String xh = js.getString("xh");
                    if(!xh.equals("110")){
                        ListPE.add(xh);
                        MainActivity.print("A2Thread:收到查询请求" + xh);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
