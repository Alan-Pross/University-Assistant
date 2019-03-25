package com.pross.object;

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

    public void setS(String stizhong, String sfeihuo, String sm50, String stiaoyuan, String sm1000, String stiqian, String syinti, String s) {
        this.stizhong = stizhong;
        this.sfeihuo = sfeihuo;
        this.sm50 = sm50;
        this.stiaoyuan = stiaoyuan;
        this.sm1000 = sm1000;
        this.stiqian = stiqian;
        this.syinti = syinti;

        this.s = s;
    }
}
