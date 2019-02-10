package com.jhun.xiaozhushou.api;

import com.jhun.xiaozhushou.R;
import com.jhun.xiaozhushou.logic.Forgot46;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class A4Controller {

    //A4接口 用于输入获取准考证号用的姓名、身份证号
    @RequestMapping("/a4result")
    public R a4result(String xm, String sfzh) {
        if (xm.isEmpty()) return R.error("a4result:xm=null");
        if (sfzh.isEmpty()) return R.error("a4result:sfzh=null");
        System.out.println("a4result:xm=" + xm + "&sfzh=" + sfzh);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //打开浏览器获取验证码
            map.putAll(Forgot46.start());
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }

    //A4管理员专用接口 用于开启查号系统并生成验证码，验证码文件在C:/XZS/img/a/a.jpg
    @RequestMapping("/a4start")
    public R a4url() {
        System.out.println("a4start:");
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //打开浏览器获取验证码
            map.putAll(Forgot46.start());
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }

    //A4管理员专用接口 用于输入开启查号系统用的验证码
    @RequestMapping("/a4yzm")
    public R a4url(String a) {
        if (a.isEmpty()) return R.error("a4yzm:a=null");
        System.out.println("a4yzm:a=" + a);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回验证码结果
            map.putAll(Forgot46.a(a));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }
}
