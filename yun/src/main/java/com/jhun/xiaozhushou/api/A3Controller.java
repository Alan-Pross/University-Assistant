package com.jhun.xiaozhushou.api;

import com.jhun.xiaozhushou.R;
import com.jhun.xiaozhushou.logic.Query46;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class A3Controller {

    //A3接口 用于获取查询四六级成绩用的验证码 验证码文件在C:/XZS/img/'openid'.jpg
    @RequestMapping("/a3getimg")
    public R a3getimg(String openid) {
        if (openid.isEmpty()) return R.error("a3getimg:openid=null");
        System.out.println("a3getimg:openid=" + openid);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回验证码图片名称
            map.put("img", Query46.getImg(openid));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }

    //A3接口 用于输入查询四六级成绩用的准考证号、姓名、验证码
    @RequestMapping("/a3result")
    public R a3result(String openid, String zkzh, String xm, String yzm) {
        if (openid.isEmpty()) return R.error("a3result:openid=null");
        if (zkzh.isEmpty()) return R.error("a3result:zkzh=null");
        if (xm.isEmpty()) return R.error("a3result:xm=null");
        if (yzm.isEmpty()) return R.error("a3result:yzm=null");
        System.out.println("a3result:openid=" + openid + "&zkzh=" + zkzh + "&xm=" + xm + "&yzm=" + yzm);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回结果
            map.putAll(Query46.result(openid, zkzh, xm, yzm));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }
}
