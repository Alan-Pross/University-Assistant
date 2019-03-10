package com.jhun.yunzhushou.object;

import java.util.HashMap;
import java.util.Map;

//体测成绩类
public class PEReport {

    public String shengao;
    public String tizhong;
    public String feihuo;
    public String m50;
    public String tiaoyuan;
    public String m1000;
    public String tiqian;
    public String yinti;

    public String sshengao;
    public String stizhong;
    public String sfeihuo;
    public String sm50;
    public String stiaoyuan;
    public String sm1000;
    public String stiqian;
    public String syinti;

    public String s;


    public PEReport(String shengao, String tizhong, String feihuo, String m50, String tiaoyuan, String m1000, String tiqian, String yinti) {
        this.shengao = shengao;
        this.tizhong = tizhong;
        this.feihuo = feihuo;
        this.m50 = m50;
        this.tiaoyuan = tiaoyuan;
        this.m1000 = m1000;
        this.tiqian = tiqian;
        this.yinti = yinti;
    }

    public void setS(String sshengao, String stizhong, String sfeihuo, String sm50, String stiaoyuan, String sm1000, String stiqian, String syinti, String s) {
        this.sshengao = sshengao;
        this.stizhong = stizhong;
        this.sfeihuo = sfeihuo;
        this.sm50 = sm50;
        this.stiaoyuan = stiaoyuan;
        this.sm1000 = sm1000;
        this.stiqian = stiqian;
        this.syinti = syinti;

        this.s = s;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("shengao",shengao);
        map.put("tizhong",tizhong);
        map.put("feihuo",feihuo);
        map.put("m50",m50);
        map.put("tiaoyuan",tiaoyuan);
        map.put("m1000",m1000);
        map.put("tiqian",tiqian);
        map.put("yinti",yinti);
        map.put("sshengao",sshengao);
        map.put("stizhong",stizhong);
        map.put("sfeihuo",sfeihuo);
        map.put("sm50",sm50);
        map.put("stiaoyuan",stiaoyuan);
        map.put("sm1000",sm1000);
        map.put("stiqian",stiqian);
        map.put("syinti",syinti);
        map.put("s",s);
        return map;
    }

}
