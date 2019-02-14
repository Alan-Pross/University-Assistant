package com.pross;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PEReport;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class A2Thread extends Thread {

    public static boolean a2stop = false;
    final public static String a2 = "https://www.jhuncloud.com/transa2";
    public static List<String> ListPE = new ArrayList();

    @Override
    public void run() {
        while (!MainActivity.isClosed) {
            try {
                //如果查询队列为空，加入一个110的空查询
                if (!(ListPE.size() > 0)) {
                    ListPE.add("110");
                } else {
                    MainActivity.print("A2Thread:开始体测查询" + ListPE.get(0));
                }

                //获得结果
                PEReport pr = HtmlUnit.a2get(ListPE.get(0));

                //开始上传请求
                Request<String> stringPostRequest = NoHttp.createStringRequest(a2, RequestMethod.POST);
                //构建上传参数
                if (pr != null) {
                    stringPostRequest.add("TiaoYuan", pr.TiaoYuan);
                    stringPostRequest.add("M400", pr.M400);
                }
                stringPostRequest.add("xh", ListPE.get(0));
                MainActivity.requestQueues.add(2, stringPostRequest, new SimpleResponseListener<String>() {
                    @Override
                    public void onStart(int what) {
                    }

                    @Override
                    public void onSucceed(int what, Response<String> response) {
                        //获得json对象
                        JSONObject js = JSONObject.parseObject(response.get());

                        //如果返回的查询不为110，此查询请求加入查询列表
                        String xh = js.getString("xh");
                        if (!xh.equals("110")) {
                            ListPE.add(xh);
                            MainActivity.print("A2Thread:收到查询请求" + xh);
                        }
                    }

                    @Override
                    public void onFailed(int what, Response<String> response) {
                    }

                    @Override
                    public void onFinish(int what) {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            //从查询队列中删除
            ListPE.remove(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
        a2stop = true;
        MainActivity.print("A2Thread已关闭");
        if(A1Thread.a1stop){
            if(MainActivity.exit) MainActivity.stopApp();
            else MainActivity.openButton();
        }
    }
}
