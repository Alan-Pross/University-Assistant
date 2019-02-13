package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Forgot46;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class A4Controller {

    //A4接口 用于获取查询四六级考号用的验证码
    @RequestMapping("/a4getimg")
    public R a4getimg(String openid) {
        if (openid.isEmpty()) return R.error("a4getimg:openid=null");
        System.out.println("a4getimg:openid=" + openid);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回验证码图片名称
            map.put("img", Forgot46.getImg(openid));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }

    //A4接口 用于输入获取四六级考号用的身份证号、姓名、验证码
    @RequestMapping("/a4result")
    public R a4result(String openid, String sfzh, String xm, String yzm) {
        System.out.println("a4result:openid=" + openid + "&sfzh=" + sfzh + "&xm=" + xm + "&yzm=" + yzm);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回结果
            map.putAll(Forgot46.result(openid, sfzh, xm, yzm));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        //记录查询信息
        map.put("openid", openid);
        map.put("sfzh", sfzh);
        map.put("xm", xm);
        Tools.logi("./logi/forgot46.txt", map.toString());
        return R.ok(map);
    }
}
