package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Query46;
import com.jhun.yunzhushou.logic.QueryJZ;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class A6Controller {

    //A6接口 用于获取查询四六级成绩用的验证码
    @RequestMapping("/a6getimg")
    public R a6getimg(String openid) {
        System.out.println("a6getimg:openid=" + openid);
        Map<String, Object> map = new HashMap<>();

        try {
            //返回验证码图片名称
            map.put("img", new QueryJZ().getImg(openid));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }

    //a6接口 用于输入查询四六级成绩用的准考证号、姓名、验证码
    @RequestMapping("/a6result")
    public R a6result(String openid, String zkzh, String xm, String yzm) {
        System.out.println("a6result:openid=" + openid + "&zkzh=" + zkzh + "&xm=" + xm + "&yzm=" + yzm);
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
