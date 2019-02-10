package com.pross;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView print;
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
    }

    public void open(View v){
        print("开启服务");
        OpenServer.start();
        print("服务已关闭");
        sleep(1);
        finish();
    }

    public void close(View v){
        print("关闭服务中");
        isClosing = true;
    }

    public void print(String s){
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
