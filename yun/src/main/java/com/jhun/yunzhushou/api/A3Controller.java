package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Query46;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class A3Controller {

    //A3接口 用于获取查询四六级成绩用的验证码
    @RequestMapping("/a3getimg")
    public R a3getimg(String openid) {
        System.out.println("a3getimg:openid=" + openid);
        Map<String, Object> map = new HashMap<>();

        try {
            //返回验证码图片名称
            map.put("img", new Query46().getImg(openid));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }

    //A3接口 用于输入查询四六级成绩用的准考证号、姓名、验证码
    @RequestMapping("/a3result")
    public R a3result(String openid, String zkzh, String xm, String yzm) {
        System.out.println("a3result:openid=" + openid + "&zkzh=" + zkzh + "&xm=" + xm + "&yzm=" + yzm);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回结果
            map.putAll(new Query46().result(openid, zkzh, xm, yzm));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        //记录查询信息
        map.put("openid", openid);
        map.put("zkzh", zkzh);
        map.put("xm", xm);
        Tools.logi("./logi/query46.txt", map.toString());
        return R.ok(map);
    }
}
