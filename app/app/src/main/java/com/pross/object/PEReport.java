package com.pross.object;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

//体测成绩类
public class PEReport {

    public String TiaoYuan;
    public String M400;

    public PEReport(String TiaoYuan, String M400) {
        this.TiaoYuan = TiaoYuan;
        this.M400 = M400;
    }

    public ArrayList<NameValuePair> toNVP() {
        ArrayList<NameValuePair> NVP = new ArrayList<NameValuePair>();
        NVP.add(new BasicNameValuePair("TiaoYuan", TiaoYuan));
        NVP.add(new BasicNameValuePair("M400", M400));
        return NVP;
    }

}
