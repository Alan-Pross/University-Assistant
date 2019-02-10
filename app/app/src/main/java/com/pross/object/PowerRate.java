package com.pross.object;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

    public ArrayList<NameValuePair> toNVP() {
        ArrayList<NameValuePair> NVP = new ArrayList<NameValuePair>();
        NVP.add(new BasicNameValuePair("CurrentRate", CurrentRate));
        NVP.add(new BasicNameValuePair("CurrentWH", CurrentWH));
        NVP.add(new BasicNameValuePair("CurrentW", CurrentW));
        return NVP;
    }
}
