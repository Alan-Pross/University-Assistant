package com.jhun.yunzhushou.api.apiweb;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Admin;
import com.jhun.yunzhushou.object.Bar;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChangeBarController {

    @RequestMapping("/changebar")
    public R changebar(String bar, String key) {
        System.out.println("changebar:bar=" + bar + "&key=" + key);
        Map<String, Object> map = new HashMap<>();

        map.put("bar",bar);

        if(key.equals(Admin.key)) {
            map.put("修改前",Bar.bar);
            map.put("修改后",bar);
            Bar.bar = bar;
        } else {
            return R.error();
        }

        //记录修改
        Tools.logi("./logi/bar.txt", map.toString());

        return R.ok(map);
    }
}