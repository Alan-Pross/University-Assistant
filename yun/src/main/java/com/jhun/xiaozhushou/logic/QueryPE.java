package com.jhun.xiaozhushou.logic;

import com.jhun.xiaozhushou.tools.Tools;
import com.jhun.xiaozhushou.object.PEReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//体测成绩查询方法
public class QueryPE {

    //所有已查到的体测成绩列表
    public static Map<String, PEReport> MapPE = new HashMap<String, PEReport>();

    //所有需要查询的体测成绩列表
    public static List<String> MapPEWaiting = new ArrayList();

    public static Map<String, Object> get(String xuehao) {
        Map<String, Object> map = new HashMap<String, Object>();

        //把学号放入需要查询的列表中
        MapPEWaiting.add(xuehao);

        //等待查询结果
        Tools.Sleep(5);
        while (!find(xuehao)) {
            Tools.Sleep(1);
        }

        //返回查询结果
        map.putAll(MapPE.get(xuehao).toMap());
        return map;
    }

    //检测是否已查到结果
    private static boolean find(String xuehao) {
        try {
            MapPE.get(xuehao);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void set(String xh, PEReport pr) {
        QueryPE.MapPE.put(xh, pr);
        QueryPE.MapPEWaiting.remove(xh);
    }
}
