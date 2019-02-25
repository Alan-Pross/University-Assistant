package com.jhun.yunzhushou.object;

import java.util.HashMap;
import java.util.Map;

//电费类
public class PowerRate {

    public String Rate;
    public String kWH;

    public PowerRate(String Rate, String kWH) {
        this.Rate = Rate;
        this.kWH = kWH;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Rate", Rate);
        map.put("kWH", kWH);
        return map;
    }
}
