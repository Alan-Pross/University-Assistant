package com.pross;

import android.app.Application;
import android.content.Intent;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
    static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);

        myApplication = this;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        log(ex.toString());
        rebot();
    }

    public static void log(String log) {
        Date now = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddHHmmss");
        String txt = "Error" + ft.format(now) + ".txt";

        if (log.length() > 1000000) log = log.substring(log.length() - 900000);
        //开始上传请求
        Request<String> stringPostRequest = NoHttp.createStringRequest(NetConfig.getUrl("Log"), RequestMethod.POST);
        //构建上传参数
        stringPostRequest.add("log", log);
        stringPostRequest.add("txt", txt);
        NoHttp.newRequestQueue().add(0, stringPostRequest, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                MainActivity.print("开始发送log信息");
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                MainActivity.print("发送完毕:" + txt);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    public static void rebot(){
        Intent intent = new Intent(myApplication, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        myApplication.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static String getTime(){
        Date now = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        return ft.format(now);
    }
}