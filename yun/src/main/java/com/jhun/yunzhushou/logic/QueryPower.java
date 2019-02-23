package com.jhun.yunzhushou.logic;

import com.jhun.yunzhushou.tools.Tools;
import com.jhun.yunzhushou.object.PowerRate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//电费查询方法
public class QueryPower {

    //所有已查到的体测成绩列表
    public static Map<String, PowerRate> MapPower = new HashMap<String, PowerRate>();

    //所有需要查询的体测成绩列表
    public static List<String> MapPowerWaiting = new ArrayList();

    public static Map<String, Object> get(String qsh) {
        Map<String, Object> map = new HashMap<String, Object>();

        //删除上次结果
        if(MapPower.containsKey(qsh)) MapPower.remove(qsh);

        //把学号放入需要查询的列表中
        MapPowerWaiting.add(qsh);

        //处理超时
        int wait = 3;
        //等待查询结果
        Tools.Sleep(3);
        while (!MapPower.containsKey(qsh)) {
            if(wait > 9) {
                map.put("error","服务器可能没开");
                //从查询队列中删除
                QueryPower.MapPowerWaiting.remove(qsh);
                return map;
            }
            Tools.Sleep(1);
            wait++;
        }

        //返回查询结果
        map.putAll(MapPower.get(qsh).toMap());
        MapPower.remove(qsh);
        return map;
    }

    public static void set(String qsh, PowerRate pr) {
        QueryPower.MapPower.put(qsh, pr);
        QueryPower.MapPowerWaiting.remove(qsh);
    }
}
