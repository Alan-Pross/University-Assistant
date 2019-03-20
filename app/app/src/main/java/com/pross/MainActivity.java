package com.pross;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;


public class MainActivity extends AppCompatActivity {

    static TextView print;
    static Button log;

    public static Boolean isClosed = false;
    static MainActivity mainActivity;

    final static int appVer = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        //绑定view
        print = findViewById(R.id.textView);
        log = findViewById(R.id.log);

        NoHttp.initialize(this);
        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttp:");// 打印Log的tag。

        FontStyle fontStyle = new FontStyle(this, "consola.ttf");
        fontStyle.setTypeface(print, false);

        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(200);

        //检查更新
        Request<String> stringPostRequest = NoHttp.createStringRequest(NetConfig.getUrl("Update"), RequestMethod.POST);
        NoHttp.newRequestQueue().add(0, stringPostRequest, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                MainActivity.print("正在检查更新");
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                JSONObject js = JSONObject.parseObject(response.get());
                String serverVer = js.getString("serverver");
                String apkUrl = js.getString("apkurl");
                MainActivity.print(MyApplication.getTime() + "当前/最新版本:" + appVer + "/" + serverVer);
                if(Integer.getInteger(serverVer) > appVer) {
                    MainActivity.print("需要更新");
                    Uri uri = Uri.parse(apkUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else{
                    //开启服务器
                    OpenServer.start();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MainActivity.print(MyApplication.getTime() + "连接服务器失败");
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        //暴力退出
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void print(String s) {
        mainActivity.runOnUiThread(() -> print.setText(s + "\n" + print.getText()));
    }

    public static void con(boolean isCon) {
        if(isCon){
            mainActivity.runOnUiThread(() -> {
                log.setText("已连接");
                log.setBackgroundColor(0xff8bc24c);
            });
        }
        else {
            mainActivity.runOnUiThread(() -> {
                log.setText("未连接");
                log.setBackgroundColor(0xffde4307);
            });
        }
    }

    public static boolean isJhunWIFI() {
        WifiManager wifiMgr = (WifiManager) mainActivity.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiMgr.getConnectionInfo();
        String wifiId = info != null ? info.getSSID() : null;
        if (wifiId.equals("\"JHUN-AUTO\"")) return true;
        return false;
    }

    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}