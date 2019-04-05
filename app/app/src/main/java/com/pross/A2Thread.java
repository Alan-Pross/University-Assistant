package com.pross;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PEReport;
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

public class A2Thread extends Thread {
    public static List<String> ListPE = new ArrayList();
    private static RequestQueue A2Queue;
    private static Request<String> A2Request;
    static int fail = 0;
    @Override
    public void run() {
        A2Queue = NoHttp.newRequestQueue();
        while (!MainActivity.isClosed) {
            //如果查询队列为空，加入一个110的空查询
            if (!(ListPE.size() > 0)) {
                ListPE.add("110");
            }
//            else {
                //开始查询
//                MainActivity.print("A2:" + MyApplication.getTime() + "体测" + ListPE.get(0));
//            }

            //开始上传请求
            A2Request = NoHttp.createStringRequest(NetConfig.getUrl("A2"), RequestMethod.POST);
            A2Request.setCacheMode(ONLY_REQUEST_NETWORK);

            PEReport pr = null;
            try {
                //获得结果
                pr = HtmlUnit.a2get(ListPE.get(0));
            } catch (Exception e) {
            }

            //构建上传参数
            if (pr == null) {
                pr = new PEReport("没有查询到结果","110","110","110","110","110","110","110");
                pr.setS("请检查输入是否正确","110","110","110","110","110","110","110");
            }
            A2Request.add("shengao", pr.shengao);
            A2Request.add("tizhong", pr.tizhong);
            A2Request.add("feihuo", pr.feihuo);
            A2Request.add("m50", pr.m50);
            A2Request.add("tiaoyuan", pr.tiaoyuan);
            A2Request.add("m1000", pr.m1000);
            A2Request.add("tiqian", pr.tiqian);
            A2Request.add("yinti", pr.yinti);
            A2Request.add("stizhong", pr.stizhong);
            A2Request.add("sfeihuo", pr.sfeihuo);
            A2Request.add("sm50", pr.sm50);
            A2Request.add("stiaoyuan", pr.stiaoyuan);
            A2Request.add("sm1000", pr.sm1000);
            A2Request.add("stiqian", pr.stiqian);
            A2Request.add("syinti", pr.syinti);
            A2Request.add("s", pr.s);

            A2Request.add("xh", ListPE.get(0));
            A2Queue.add(0, A2Request, new SimpleResponseListener<String>() {
                @Override
                public void onStart(int what) {
                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                    MainActivity.con(true);
                    //获得json对象
                    JSONObject js = JSONObject.parseObject(response.get());

                    //如果返回的查询不为110，此查询请求加入查询列表
                    String xh = js.getString("xh");
                    if (!xh.equals("110")) {
                        ListPE.add(xh);
                        MainActivity.print("A2:" + MyApplication.getTime() + "收到" + xh);
                    }
                    if(fail > 0)
                        MainActivity.print("A2已连回");
                    fail = 0;
                }

                @Override
                public void onFailed(int what, Response<String> response) {
                    MainActivity.con(false);
                    Date now = new Date( );
                    SimpleDateFormat ft = new SimpleDateFormat("HH");
                    switch (ft.format(now)){
                        case "23":break;
                        case "00":break;
                        case "01":break;
                        case "02":break;
                        case "03":break;
                        case "04":break;
                        case "05":break;
                        case "06":break;
                        case "07":break;
                        case "08":break;
                        default:{
                            MainActivity.print("A2连接失败");
                            fail++;
                            if(fail > 20){
                                MainActivity.zhendong();
                            }
                            if(fail > 40)
                                MyApplication.rebot();
                        }
                    }
                }

                @Override
                public void onFinish(int what) {
                }
            });
            //从查询队列中删除
            ListPE.remove(0);
            if(ListPE.size() == 0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
        MainActivity.print(MyApplication.getTime() + "A2停止线程");
    }
}