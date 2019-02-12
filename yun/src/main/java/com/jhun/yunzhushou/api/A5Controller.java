package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.Forgot46;
import com.jhun.yunzhushou.logic.QueryPu;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class A5Controller {

    //A5接口 用于输入获取普通话成绩用的身份证号、姓名
    @RequestMapping("/a5result")
    public R a5result(String sfzh, String xm) {
        System.out.println("a4result:sfzh=" + sfzh + "&xm=" + xm);
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            //返回结果
            map.putAll(QueryPu.result(sfzh, xm));
        } catch (IOException e) {
            return R.error(e.toString());
        }

        return R.ok(map);
    }
}
