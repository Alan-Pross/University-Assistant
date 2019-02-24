package com.pross;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PowerRate;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.util.ArrayList;
import java.util.List;

public class A1Thread extends Thread {

    public static boolean a1stop = false;
    final public static String a1 = "https://www.daohangcn.cn/transa1";
    public static List<String> ListPower = new ArrayList();

    @Override
    public void run() {
        while (!MainActivity.isClosed) {
            //如果查询队列为空，加入一个110的空查询
            if (!(ListPower.size() > 0)) {
                ListPower.add("110");
            } else {
                MainActivity.print("A1:" + System.currentTimeMillis() / 1000 + "开始电费查询" + ListPower.get(0));
            }

            //开始上传请求
            Request<String> stringPostRequest = NoHttp.createStringRequest(a1, RequestMethod.POST);

            PowerRate pr = null;
            try {
                //获得结果
                pr = HtmlUnit.a1get(ListPower.get(0));
            } catch (Exception e) {
                e.printStackTrace();
                pr.Rate = "没有查询到结果";
                pr.kWH = "请检查输入是否正确";
            }

            //构建上传参数
            if (pr != null) {
                stringPostRequest.add("Rate", pr.Rate);
                stringPostRequest.add("kWH", pr.kWH);
            }
            stringPostRequest.add("qsh", ListPower.get(0));
            MainActivity.requestQueues.add(2, stringPostRequest, new SimpleResponseListener<String>() {
                @Override
                public void onStart(int what) {
                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    //获得json对象
                    JSONObject js = JSONObject.parseObject(response.get());

                    //如果返回的查询不为110，此查询请求加入查询列表
                    String qsh = js.getString("qsh");
                    if (!qsh.equals("110")) {
                        ListPower.add(qsh);
                        MainActivity.print("A1:" + System.currentTimeMillis() / 1000 + "收到查询请求" + qsh);
                    }
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                }

                @Override
                public void onFinish(int what) {
                }
            });
            //从查询队列中删除
            ListPower.remove(0);
            if(ListPower.size() == 0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
        a1stop = true;
        MainActivity.print("A1Thread已关闭");
        if (A2Thread.a2stop) {
            if (MainActivity.exit) MainActivity.stopApp();
        }
    }
}