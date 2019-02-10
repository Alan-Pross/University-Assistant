package com.jhun.xiaozhushou.logic;

import com.jhun.xiaozhushou.tools.Tools;
import com.jhun.xiaozhushou.object.PowerRate;

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

    public static Map<String, Object> get(String qinshihao) {
        Map<String, Object> map = new HashMap<String, Object>();

        //把学号放入需要查询的列表中
        MapPowerWaiting.add(qinshihao);

        //等待查询结果
        Tools.Sleep(3);
        while (!find(qinshihao)) {
            Tools.Sleep(1);
        }

        //返回查询结果
        map.putAll(MapPower.get(qinshihao).toMap());
        return map;
    }

    //检测是否已查到结果
    private static boolean find(String qinshihao) {
        try {
            MapPower.get(qinshihao);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void set(String qsh, PowerRate pr) {
        QueryPower.MapPower.put(qsh, pr);
        QueryPower.MapPowerWaiting.remove(qsh);
    }
}
