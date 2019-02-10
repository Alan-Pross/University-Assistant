package com.jhun.xiaozhushou.object;

import java.util.HashMap;
import java.util.Map;

//电费类
public class PowerRate {

    public String CurrentRate;
    public String CurrentWH;
    public String CurrentW;

    public PowerRate() {}

    public PowerRate(String CurrentRate, String CurrentWH, String CurrentW) {
        this.CurrentRate = CurrentRate;
        this.CurrentWH = CurrentWH;
        this.CurrentW = CurrentW;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("CurrentRate", CurrentRate);
        return map;
    }
}
