package com.pross;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PowerRate;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yanzhenjie.nohttp.rest.CacheMode.ONLY_REQUEST_NETWORK;

public class A1Thread extends Thread {
    public static List<String> ListPower = new ArrayList();
    private static RequestQueue A1Queue;
    private static Request<String> A1Request;
    static int fail = 0;
    @Override
    public void run() {
        A1Queue = NoHttp.newRequestQueue();

        while (!MainActivity.isClosed) {
            //如果查询队列为空，加入一个110的空查询
            if (!(ListPower.size() > 0)) {
                ListPower.add("110");
            }

            //开始上传请求
            A1Request = NoHttp.createStringRequest(NetConfig.getUrl("A1"), RequestMethod.POST);
            A1Request.setCacheMode(ONLY_REQUEST_NETWORK);

            PowerRate pr = null;
            try {
                //获得结果
                pr = HtmlUnit.a1get(ListPower.get(0));
            } catch (Exception e) {
            }

            //构建上传参数
            if (pr == null) {
                pr = new PowerRate("没有查询到结果","请检查输入是否正确");
            }
            A1Request.add("Rate", pr.Rate);
            A1Request.add("kWH", pr.kWH);

            A1Request.add("qsh", ListPower.get(0));
            A1Queue.add(0, A1Request, new SimpleResponseListener<String>() {
                @Override
                public void onStart(int what) {
                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    MainActivity.con(true);
                    //获得json对象
                    JSONObject js = JSONObject.parseObject(response.get());

                    //如果返回的查询不为110，此查询请求加入查询列表
                    String qsh = js.getString("qsh");
                    if (!qsh.equals("110")) {
                        ListPower.add(qsh);
                        MainActivity.print("A1:" + MyApplication.getTime() + "收到" + qsh);
                    }
                    if(fail > 0) {
                        MainActivity.print("主服务器已连接");
                        MyApplication.rebot();
                    }
                    fail = 0;
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    MainActivity.con(false);
                    fail++;
                    if(fail == 1)
                        MainActivity.print("主服务器无响应");
                    if(fail > 30)
                        MyApplication.rebot();
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
        MainActivity.print(MyApplication.getTime() + "A1停止线程");
    }
}