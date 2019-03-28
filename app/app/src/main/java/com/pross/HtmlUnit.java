package com.pross;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.pross.object.PEReport;
import com.pross.object.PowerRate;

import java.io.IOException;

public class HtmlUnit {

    public static PowerRate a1get(String qsh) throws Exception {
        PowerRate pr;

        //如果是110表示是一次空查询
        if(qsh.equals("110")){
            return new PowerRate("110","110");
        }

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //超时时间
        webClient.getOptions().setTimeout(10000);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage page;
        if(qsh.charAt(0) == '北'){
            //查北区 北1至北14
            page = webClient.getPage("http://210.42.74.111:8080/admin/sys!chaxun.action?fjmc=" + qsh.substring(1));
        } else if (qsh.charAt(0) == '南') {
            //查南区 南1至南9，研究生
            page = webClient.getPage("http://210.42.74.113:8080/admin/sys!chaxun.action?fjmc=" + qsh.substring(1));
        } else {
            return null;
        }

        //加载js
        webClient.waitForBackgroundJavaScript(2000);

        HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='listTable']").get(0);

        //余额
        String r1 = table.getCellAt(2, 5).asText();
        //电量
        String r2 = table.getCellAt(3, 1).asText();

        pr = new PowerRate(r1,r2);

        webClient.close();
        MainActivity.print("A1:" +  MyApplication.getTime() + "查询完成" + qsh + "余额:" + r1);
        return pr;
    }

    public static PEReport a2get(String xh) throws IOException {
        PEReport pr;

        //如果是110表示是一次空查询
        if(xh.equals("110")){
            pr = new PEReport("110","110","110","110","110","110","110","110");
            pr.setS("110","110","110","110","110","110","110","110");
            return pr;
        }

        //浏览器设置
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(10000);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        //进入网页
        HtmlPage page = webClient.getPage("http://210.42.72.169/");

        //学生选项
        HtmlSelect displayRole = (HtmlSelect) page.getByXPath("//select[@name='displayRole']").get(0);
        HtmlPage page1 = displayRole.setSelectedAttribute("911",true);

        //获取输入框
        HtmlInput displayName = (HtmlInput) page1.getByXPath("//input[@name='displayName']").get(0);
        HtmlInput displayPasswd = (HtmlInput) page1.getByXPath("//input[@name='displayPasswd']").get(0);

        //填入学号
        displayName.setValueAttribute(xh);
        displayPasswd.setValueAttribute(xh);

        //登录
        page1.executeJavaScript("checkSubmit()");

        //获取cookies并进入成绩页
        webClient.getPage("http://210.42.72.169/student/studentInfo.jsp?userName=" + xh + "&passwd=" + xh);
        HtmlPage resultPage = webClient.getPage("http://210.42.72.169/student/queryHealthInfo.jsp");

        //获得成绩表格
        HtmlTable table = (HtmlTable) resultPage.getByXPath("//table[@bgcolor='#cdddf4']").get(0);

        String r1 = table.getCellAt(3, 1).asText();
        String r2 = table.getCellAt(4, 1).asText();
        String r3 = table.getCellAt(5, 1).asText();
        String r4 = table.getCellAt(6, 1).asText();
        String r5 = table.getCellAt(7, 1).asText();
        String r6 = table.getCellAt(8, 1).asText();
        String r7 = table.getCellAt(9, 1).asText();
        String r8 = table.getCellAt(10, 1).asText();

        String sr2 = table.getCellAt(4, 2).asText();
        String sr3 = table.getCellAt(5, 2).asText();
        String sr4 = table.getCellAt(6, 2).asText();
        String sr5 = table.getCellAt(7, 2).asText();
        String sr6 = table.getCellAt(8, 2).asText();
        String sr7 = table.getCellAt(9, 2).asText();
        String sr8 = table.getCellAt(10, 2).asText();

        String s = table.getCellAt(3, 6).asText();

        pr = new PEReport(r1,r2,r3,r4,r5,r6,r7,r8);
        pr.setS(sr2,sr3,sr4,sr5,sr6,sr7,sr8,s);

        webClient.close();
        MainActivity.print("A2:" +  MyApplication.getTime() + "查询完成" + xh + "总分:" + s);
        return pr;
    }
}
