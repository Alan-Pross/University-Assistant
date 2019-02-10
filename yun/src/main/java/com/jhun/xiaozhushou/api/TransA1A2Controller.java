package com.jhun.xiaozhushou.api;

import com.jhun.xiaozhushou.R;
import com.jhun.xiaozhushou.logic.QueryPE;
import com.jhun.xiaozhushou.logic.QueryPower;
import com.jhun.xiaozhushou.object.PEReport;
import com.jhun.xiaozhushou.object.PowerRate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransA1A2Controller {

    //反式接口 用于获取电费
    @RequestMapping(value = "/transa1")
    public R transa1(String qsh, PowerRate pr) {
        if (qsh.isEmpty()) return R.error("transa1:qsh=null");
        if (pr == null) return R.error("transa1:pr=null");
        System.out.println("transa1:qsh=" + qsh + "&pr=" + pr);
        Map<String, Object> map = new HashMap<String, Object>();

        if(!qsh.equals("110")){
            //写入结果
            QueryPower.set(qsh, pr);
        } else {
            //表示无需查询
            map.put("qsh","110");
        }

        //如果队列中有需要查询的，返回给它
        if (QueryPower.MapPowerWaiting.size() > 0) {
            map.put("qsh", QueryPower.MapPowerWaiting.get(0));
        }

        return R.ok(map);
    }

    //反式接口 用于获取体测成绩
    @RequestMapping(value = "/transa2")
    public R transa2(String xh, PEReport pr) {
        if (xh.isEmpty()) return R.error("transa2:xh=null");
        if (pr == null) return R.error("transa2:pr=null");
        System.out.println("transa2:xh=" + xh + "&pr=" + pr);
        Map<String, Object> map = new HashMap<String, Object>();

        if(!xh.equals("110")){
            //写入结果
            QueryPE.set(xh, pr);
        } else {
            //表示无需查询
            map.put("xh","110");
        }
        //写入结果

        //如果队列中有需要查询的，返回给它
        if (QueryPE.MapPEWaiting.size() > 0) {
            map.put("xh", QueryPE.MapPEWaiting.get(0));
        }

        return R.ok(map);
    }
}
