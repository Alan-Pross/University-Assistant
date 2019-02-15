package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.QueryPE;
import com.jhun.yunzhushou.tools.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class A2Controller {

    @RequestMapping("/a2")
    public R a2(String xh) {
        System.out.println("a2:xh=" + xh);
        Map<String, Object> map = new HashMap<String, Object>();

        //返回体测成绩查询结果
        map.putAll(QueryPE.get(xh));

        //记录查询信息
        Tools.logi("./logi/queryPE.txt", xh);
        return R.ok(map);
    }
}
