package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.object.Bar;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class NoticeBarController {

    //2.0以上小程序版本，动态公告接口
    @RequestMapping("/noticebar")
    public R noticebar() {
        return R.ok(Bar.bar);
    }
}