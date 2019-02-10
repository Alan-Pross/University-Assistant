package com.jhun.xiaozhushou.api;

import com.jhun.xiaozhushou.R;
import com.jhun.xiaozhushou.logic.QueryPower;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class A2Controller {

    @RequestMapping("/a2")
    public R a2(String xh) {
        if (xh.isEmpty()) return R.error("a2:xh=null");
        System.out.println("a2:xh=" + xh);
        Map<String, Object> map = new HashMap<String, Object>();

        //返回体测成绩查询结果
        map.putAll(QueryPower.get(xh));

        return R.ok(map);
    }
}
