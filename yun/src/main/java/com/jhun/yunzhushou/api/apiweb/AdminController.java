package com.jhun.yunzhushou.api.apiweb;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Admin;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    @RequestMapping("/admin")
    public R admin(String root, String pass, String key) {
        System.out.println("admin:root=" + root + "&pass=" + pass + "&key=" + key);
        Map<String, Object> map = new HashMap<>();

        map.put("账号", root);
        map.put("密码", pass);
        map.put("验证码", key);

        if (!key.equals("wh4t")) {
            //记录
            Tools.logi("./logi/admin.txt", map.toString());
            return R.error();
        }

        if (Admin.enter(root, pass)) {
            map.put("key", Admin.getKey());
            map.put("状态", "成功");
            map.put("is", "ok");
        } else {
            map.put("状态", "失败");
        }

        //记录
        Tools.logi("./logi/admin.txt", map.toString());

        return R.ok(map);
    }
}