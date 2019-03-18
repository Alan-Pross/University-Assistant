package com.pross;

import android.os.Vibrator;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PEReport;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class A2Thread extends Thread {

    final public static String a2 = "https://www.daohangcn.cn/transa2";
    public static List<String> ListPE = new ArrayList();

    static int fail = 0;

    @Override
    public void run() {
        while (!MainActivity.isClosed) {
            //如果查询队列为空，加入一个110的空查询
            if (!(ListPE.size() > 0)) {
                ListPE.add("110");
            } else {
                MainActivity.print("A2:" + MyApplication.getTime() + "开始体测查询" + ListPE.get(0));
            }

            //开始上传请求
            Request<String> stringPostRequest = NoHttp.createStringRequest(a2, RequestMethod.POST);

            PEReport pr = null;
            try {
                //获得结果
                pr = HtmlUnit.a2get(ListPE.get(0));
            } catch (Exception e) {
                e.printStackTrace();
                pr.s = "没有查询到结果";
            }

            //构建上传参数
            if (pr != null) {
                stringPostRequest.add("shengao", pr.shengao);
                stringPostRequest.add("tizhong", pr.tizhong);
                stringPostRequest.add("feihuo", pr.feihuo);
                stringPostRequest.add("m50", pr.m50);
                stringPostRequest.add("tiaoyuan", pr.tiaoyuan);
                stringPostRequest.add("m1000", pr.m1000);
                stringPostRequest.add("tiqian", pr.tiqian);
                stringPostRequest.add("yinti", pr.yinti);
                stringPostRequest.add("sshengao", pr.sshengao);
                stringPostRequest.add("stizhong", pr.stizhong);
                stringPostRequest.add("sfeihuo", pr.sfeihuo);
                stringPostRequest.add("sm50", pr.sm50);
                stringPostRequest.add("stiaoyuan", pr.stiaoyuan);
                stringPostRequest.add("sm1000", pr.sm1000);
                stringPostRequest.add("stiqian", pr.stiqian);
                stringPostRequest.add("syinti", pr.syinti);
                stringPostRequest.add("s", pr.s);
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
                        MainActivity.print("A2:" + MyApplication.getTime() + "收到查询请求" + xh);
                    }

                    if(fail > 8)
                        MainActivity.print("A2连接成功！！！！！");
                    fail = 0;
                }

                @Override
                public void onFailed(int what, Response<String> response) {
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
                            fail++;
                            if(fail > 8 && fail < 16)
                                MainActivity.print("A2断开连接··········");
                            if(fail > 16){
                                Vibrator vibrator = (Vibrator)MainActivity.mainActivity.getSystemService(MainActivity.mainActivity.VIBRATOR_SERVICE);
                                vibrator.vibrate(200);
                            }
                            if(fail > 32)
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
    }
}