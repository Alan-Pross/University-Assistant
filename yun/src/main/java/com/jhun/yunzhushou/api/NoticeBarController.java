package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class NoticeBarController {

    //2.0以上小程序版本，动态公告接口
    @RequestMapping("/noticebar")
    public R noticebar() {

        String bar;
        try {
            bar = new String(Files.readAllBytes(Paths.get("./bar.txt")));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("欢迎使用校园云助理！");
            return R.ok("欢迎使用校园云助理！");
        }
        return R.ok(bar);
    }
}