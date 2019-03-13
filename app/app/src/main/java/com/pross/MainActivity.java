package com.pross;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.RequestQueue;


public class MainActivity extends AppCompatActivity {

    static TextView print;
    Button log;

    public static Boolean isClosed = false;
    static RequestQueue requestQueues;
    static MainActivity mainActivity;

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
        requestQueues = NoHttp.newRequestQueue();

        FontStyle fontStyle = new FontStyle(this, "consola.ttf");
        fontStyle.setTypeface(print, false);

        //开启服务器
        OpenServer.start();

        Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onBackPressed() {
        //暴力退出
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void log(View v) {
        String log = print.getText().toString();
        MyApplication.log(log);
    }

    public static void print(String s) {
        mainActivity.runOnUiThread(() -> print.setText(s + "\n" + print.getText()));
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