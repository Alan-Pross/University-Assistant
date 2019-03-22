package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class NoticeBar {

    //2.0以上小程序版本，动态公告接口
    @RequestMapping("/noticebar")
    public R noticebar() {
        try {
        String bar = new String(Files.readAllBytes(Paths.get("./bar.txt")));
            return R.ok(bar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("欢迎使用校园云管家！");
    }
}