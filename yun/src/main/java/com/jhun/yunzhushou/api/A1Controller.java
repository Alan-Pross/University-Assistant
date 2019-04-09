package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.QueryPower;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class A1Controller {

    @RequestMapping("/a1")
    public R a1(String qsh) {
        System.out.println("a1:qsh=" + qsh);
        Map<String, Object> map = new HashMap<>();

        //返回电费查询结果
        map.putAll(QueryPower.get(qsh));
        map.put("qsh", qsh);
        map.put("time", Tools.getTime("yyyy-MM-dd HH:mm:ss"));

        //记录查询信息
        Tools.logi("./logi/queryPower/" + qsh + ".txt", map.toString());
        return R.ok(map);
    }
}