package com.jhun.xiaozhushou.api;

import com.jhun.xiaozhushou.R;
import com.jhun.xiaozhushou.logic.QueryPower;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class A1Controller {

    @RequestMapping("/a1")
    public R a1(String qsh) {
        if (qsh.isEmpty()) return R.error("a1:qsh=null");
        System.out.println("a1:qsh=" + qsh);
        Map<String, Object> map = new HashMap<String, Object>();

        //返回电费查询结果
        map.putAll(QueryPower.get(qsh));

        return R.ok(map);
    }
}