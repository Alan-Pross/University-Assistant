package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.ServerOnline;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class A1A2onlineController {

    //查询安卓服务器在线状态
    @RequestMapping("/online")
    public R online(String which) {
        System.out.println("online:which=" + which);
        Map<String, Object> map = new HashMap<>();

        map.put("online", ServerOnline.get(which)?"1":"0");

        return R.ok(map);
    }
}