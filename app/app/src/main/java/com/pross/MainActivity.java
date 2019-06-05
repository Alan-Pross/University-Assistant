package com.pross;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    public static TextView print;
    static Button log;

    public static Boolean isClosed = false;
    static MainActivity mainActivity;

    final static String appVer = "1004";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        //绑定view
        print = findViewById(R.id.textView);
        log = findViewById(R.id.log);

        FontStyle fontStyle = new FontStyle(this, "consola.ttf");
        fontStyle.setTypeface(print, false);

        Intent intent = getIntent();
        String print = intent.getStringExtra("print");
        if(print != null)
            print(print);

        initPermission();
    }

    private void getUpdate() {
        //检查更新
        Request<String> stringPostRequest = NoHttp.createStringRequest(NetConfig.getUrl("Update"), RequestMethod.GET);
        NoHttp.newRequestQueue().add(0, stringPostRequest, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                MainActivity.print("正在检查更新...");
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                mainActivity.runOnUiThread(() -> print.setText(""));
                JSONObject js = JSONObject.parseObject(response.get());
                String serverVer = js.getString("serverver");
                String apkUrl = js.getString("apkurl");
                MainActivity.print("当前版本" + appVer + "/最新版本" + serverVer);
                if (!serverVer.equals(appVer)) {
                    MainActivity.print("======================");
                    MainActivity.print("开始下载：" + apkUrl);
                    MainActivity.print("!!!此程序需要更新!!!");
                    MainActivity.print("======================");

                    //程序更新
                    doUpdate("https://" + apkUrl, serverVer + ".apk");
                } else {
                    MainActivity.print("======================");
                    MainActivity.print("修复寝室电费查询功能");
                    MainActivity.print("本次更新内容如下：");
                    MainActivity.print("!!!此程序是最新版本!!!");
                    MainActivity.print("======================");

                    //开启服务器
                    OpenServer.start();
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                MainActivity.print(MyApplication.getTime() + "连接服务器失败");
                //等待60秒后重新连接服务器
                new Thread(()-> {
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                    }
                    getUpdate();
                }).start();
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

    private void doUpdate(String url, String filename) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        DownloadRequest downloadRequest = NoHttp.createDownloadRequest(url, path, filename, true, false);
        DownloadQueue downloadQueue = NoHttp.newDownloadQueue();
        downloadQueue.add(0, downloadRequest, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                if (exception instanceof TimeoutError) {
                    downloadQueue.add(0, downloadRequest, this);
                } else {
                    MyApplication.log(exception.toString());
                    MainActivity.print(exception.toString());
                    MainActivity.print("下载出现错误,错误日志已上报给服务器");
                }
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {

            }

            @Override
            public void onProgress(int what, int progress, long fileCount, long speed) {
                MainActivity.print("已下载" + progress + "%速度" + speed / 1024 + "kb/s");
            }

            @Override
            public void onFinish(int what, String filePath) {
                MainActivity.print("下载完成");
                install(filePath);
            }

            @Override
            public void onCancel(int what) {
                MainActivity.print("取消下载");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initPermission() {
        Boolean a = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!a) {
            ActivityCompat.requestPermissions(MainActivity.mainActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else
            getUpdate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //通过权限
                getUpdate();
            } else {
                //拒绝权限
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
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
        if (isCon) {
            mainActivity.runOnUiThread(() -> {
                log.setText("已连接");
                log.setBackgroundColor(0xff8bc24c);
            });
        } else {
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

    public static boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(MainActivity.mainActivity, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void install(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getPathUri(mainActivity, filePath), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    public static Uri getPathUri(Context context, String filePath) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String packageName = context.getPackageName();
            uri = FileProvider.getUriForFile(context, packageName + ".fileProvider", new File(filePath));
        } else {
            uri = Uri.fromFile(new File(filePath));
        }
        return uri;
    }
}