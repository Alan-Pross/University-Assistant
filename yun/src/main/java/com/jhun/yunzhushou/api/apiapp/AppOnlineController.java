package com.jhun.yunzhushou.api.apiapp;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.ServerOnline;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppOnlineController {

    //查询安卓服务器在线状态
    @RequestMapping("/online")
    public R online() {
        Map<String, Object> map = new HashMap<>();

        map.put("online", ServerOnline.get()?"1":"0");

        return R.ok(map);
    }
}