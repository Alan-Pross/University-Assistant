package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeBar {

    @RequestMapping("/noticebar")
    public R noticebar() {
        //2.0以上小程序版本，动态公告接口
        return R.ok("测试动态公告");
    }
}