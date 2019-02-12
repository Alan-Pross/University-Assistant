package com.pross;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

public class MainActivity extends AppCompatActivity {

    final static WebClient webClient = new WebClient(BrowserVersion.CHROME);

    static TextView print;
    Button open;
    Button close;

    public static Boolean isClosing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定view
        print = findViewById(R.id.textView);
        open = findViewById(R.id.open);
        close = findViewById(R.id.close);

        print("--欢迎使用江大云助手安卓服务器--");
        print("别看人家这么丑，人家可是服务器呢");

        //支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        //超时时间
        webClient.getOptions().setTimeout(10000);
    }

    public void open(View v){
        print("开启服务");
        OpenServer.start();
        print("服务已关闭");
        webClient.close();
        sleep(1);
        finish();
    }

    public void close(View v){
        print("关闭服务中");
        isClosing = true;
    }

    public static void print(String s){
        print.setText(print.getText() + "\n" + s);
    }

    public static void sleep(long seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
