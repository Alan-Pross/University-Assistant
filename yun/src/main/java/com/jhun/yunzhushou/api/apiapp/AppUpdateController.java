package com.jhun.yunzhushou.api.apiapp;

import com.jhun.yunzhushou.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AppUpdateController {

    @Value("${server.ssl.key-alias}")
    private volatile String url;

    //用于接收安卓服务器verson信息的接口
    @RequestMapping("/appupdate")
    public R appupdate() {
        Map<String, Object> map = new HashMap<>();

        try {
            String serverver = new String(Files.readAllBytes(Paths.get("./app.txt")));
            map.put("serverver",serverver);
            map.put("apkurl",url + "/" + serverver + ".apk");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.toString());
        }
        return R.ok(map);
    }
}