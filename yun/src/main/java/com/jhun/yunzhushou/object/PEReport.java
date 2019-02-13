package com.jhun.yunzhushou.object;

import java.util.HashMap;
import java.util.Map;

//体测成绩类
public class PEReport {

    public String TiaoYuan;
    public String M400;

    public PEReport(String TiaoYuan, String M400) {
        this.TiaoYuan = TiaoYuan;
        this.M400 = M400;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("TiaoYuan", TiaoYuan);
        map.put("M400", M400);
        return map;
    }

}
