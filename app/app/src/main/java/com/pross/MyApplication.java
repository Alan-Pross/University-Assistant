package com.pross;

import android.app.Application;
import android.content.Intent;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {
    public static MyApplication myApplication;
    private static RequestQueue LogQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(this);

        InitializationConfig config = InitializationConfig.newBuilder(this)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(this).setEnable(false)
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                .cookieStore(
                        // 如果不维护cookie，setEnable(false)禁用。
                        new DBCookieStore(this).setEnable(false)
                )
                .retry(0) // 全局重试次数，配置后每个请求失败都会重试x次。
                .build();
        NoHttp.initialize(config);
        Logger.setDebug(false);

        myApplication = this;
        LogQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        log(ex.toString());
        MainActivity.print(ex.toString());
        ex.printStackTrace();
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
        LogQueue.add(0, stringPostRequest, new SimpleResponseListener<String>() {
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
        MainActivity.print("开始重启");
        Intent intent = new Intent(myApplication, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("print",MainActivity.print.getText().toString());
        myApplication.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static String getTime(){
        Date now = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("MMdd HH:mm:ss|");
        return ft.format(now);
    }
}