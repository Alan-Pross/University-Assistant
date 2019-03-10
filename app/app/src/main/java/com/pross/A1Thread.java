package com.pross;

import android.os.Vibrator;

import com.alibaba.fastjson.JSONObject;
import com.pross.object.PowerRate;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class A1Thread extends Thread {

    final public static String a1 = "https://www.daohangcn.cn/transa1";
    public static List<String> ListPower = new ArrayList();

    static int fail = 0;

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
                                MainActivity.print("服务器断开连接，请检查网络");
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
            ListPower.remove(0);
            if(ListPower.size() == 0){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}