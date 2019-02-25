package com.jhun.yunzhushou.api;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.QueryPE;
import com.jhun.yunzhushou.logic.QueryPower;
import com.jhun.yunzhushou.object.PEReport;
import com.jhun.yunzhushou.object.PowerRate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransA1A2Controller {

    //反式接口 用于获取电费
    @RequestMapping(value = "/transa1")
    public R transa1(String qsh, String Rate, String kWH) {
        PowerRate pr = new PowerRate(Rate, kWH);
        Map<String, Object> map = new HashMap<String, Object>();

        if (!qsh.equals("110")) {
            try {
                //从查询队列中删除
                QueryPower.MapPowerWaiting.remove(qsh);
                //写入结果
                QueryPower.set(qsh, pr);
            } catch (NullPointerException e){
            }
        }

        //如果队列中有需要查询的，返回给它
        if (QueryPower.MapPowerWaiting.size() > 0) {
            map.put("qsh", QueryPower.MapPowerWaiting.get(0));
        } else {
            //表示无需查询
            map.put("qsh", "110");
        }

        return R.ok(map);
    }

    //反式接口 用于获取体测成绩
    @RequestMapping(value = "/transa2")
    public R transa2(String xh, String TiaoYuan, String M400) {
        PEReport pr = new PEReport(TiaoYuan, M400);
        Map<String, Object> map = new HashMap<String, Object>();

        if (!xh.equals("110")) {
            try {
                //从查询队列中删除
                QueryPE.MapPEWaiting.remove(xh);
                //写入结果
                QueryPE.set(xh, pr);
            } catch (NullPointerException e){
            }
        }

        //如果队列中有需要查询的，返回给它
        if (QueryPE.MapPEWaiting.size() > 0) {
            map.put("xh", QueryPE.MapPEWaiting.get(0));
        } else {
            //表示无需查询
            map.put("xh", "110");
        }

        return R.ok(map);
    }
}
