package com.pross;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

public class MainActivity extends AppCompatActivity {

    static TextView print;
    Button open;
    Button close;
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
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);
        log = findViewById(R.id.log);

        print("--欢迎使用江大云助手安卓服务器--");
        print("别看人家这么丑，人家可是服务器呢");

        NoHttp.initialize(this);
        Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
        Logger.setTag("NoHttp:");// 打印Log的tag。
        requestQueues = NoHttp.newRequestQueue();
    }

    public void open(View v) {
        print("开启服务");
        OpenServer.start();
    }

    public void close(View v) {
        print("关闭服务中");
        isClosed = true;
    }

    public void log(View v) {
        String url = "/applog";
        String txt = System.currentTimeMillis() / 1000 + ".txt";
        //开始上传请求
        Request<String> stringPostRequest = NoHttp.createStringRequest(url, RequestMethod.POST);
        //构建上传参数
        stringPostRequest.add("log", print.getText().toString());
        stringPostRequest.add("txt", txt);
        MainActivity.requestQueues.add(2, stringPostRequest, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                MainActivity.print("发送AppLog:" + txt);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    public static void print(String s) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                print.setText(s + "\n" + print.getText());
            }
        });
    }
}
