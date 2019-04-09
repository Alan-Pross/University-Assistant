package com.jhun.yunzhushou.api.apiapp;

import com.jhun.yunzhushou.R;
import com.jhun.yunzhushou.logic.QueryPE;
import com.jhun.yunzhushou.logic.QueryPower;
import com.jhun.yunzhushou.object.A1A2online;
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
        A1A2online.A1online = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();

        if (!qsh.equals("110")) {
            PowerRate pr = new PowerRate(Rate, kWH);
            try {
                //从查询队列中删除
                QueryPower.MapPowerWaiting.remove(qsh);
                //写入结果
                QueryPower.set(qsh, pr);
            } catch (NullPointerException e) {
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
    public R transa2(String xh,
                     String shengao, String tizhong, String feihuo, String m50, String tiaoyuan, String m1000, String tiqian, String yinti,
                     String stizhong, String sfeihuo, String sm50, String stiaoyuan, String sm1000, String stiqian, String syinti, String s) {
        A1A2online.A2online = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<String, Object>();

        if (!xh.equals("110")) {
            PEReport pr = new PEReport(shengao, tizhong, feihuo, m50, tiaoyuan, m1000, tiqian, yinti);
            pr.setS(stizhong, sfeihuo, sm50, stiaoyuan, sm1000, stiqian, syinti, s);
            try {
                //从查询队列中删除
                QueryPE.MapPEWaiting.remove(xh);
                //写入结果
                QueryPE.set(xh, pr);
            } catch (NullPointerException e) {
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
