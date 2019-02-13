package com.pross.object;

//电费类
public class PowerRate {

    public String CurrentRate;
    public String CurrentWH;
    public String CurrentW;

    public PowerRate(String CurrentRate, String CurrentWH, String CurrentW) {
        this.CurrentRate = CurrentRate;
        this.CurrentWH = CurrentWH;
        this.CurrentW = CurrentW;
    }
}
