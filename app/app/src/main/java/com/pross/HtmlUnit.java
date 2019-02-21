package com.pross;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.pross.object.PEReport;
import com.pross.object.PowerRate;

import java.io.IOException;

public class HtmlUnit {

    public static PowerRate a1get(String qsh) throws IOException {
        PowerRate pr = null;

        //如果是110表示是一次空查询
        if(qsh.equals("110")){
            return pr;
        }

        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //超时时间
        webClient.getOptions().setTimeout(10000);

        HtmlPage page = null;
        if(qsh.charAt(0) == '北'){
            //查北区 北1至北14
            page = webClient.getPage("http://210.42.74.111:8080/admin/sys!chaxun.action");
        } else if (qsh.charAt(0) == '南') {
            //查南区 南1至南9，研究生
            page = webClient.getPage("http://210.42.74.113:8080/admin/sys!chaxun.action");
        } else {
            return null;
        }
        //把第一个字截掉
        qsh = qsh.substring(1);

        //加载js
        //webClient.waitForBackgroundJavaScript(10000);

        //找到输入框
        HtmlInput fjmc = (HtmlInput) page.getByXPath("//input[@name='fjmc']").get(0);

        //找到查询按钮
        HtmlAnchor a = (HtmlAnchor) page.getByXPath("//a[@id='chaxun']").get(0);

        fjmc.setValueAttribute(qsh);

        page = a.click();

        HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='listTable']").get(0);

        //总余额
        String r1 = table.getCellAt(1, 5).asText();
        //总用电量
        String r2 = table.getCellAt(2, 1).asText();

        //瞬时功率
        HtmlTextArea textArea = (HtmlTextArea) page.getByXPath("//text[@id='Energy-1']").get(0);
        String r3 = textArea.asText();

        pr = new PowerRate(r1,r2,r3);

        webClient.close();
        return pr;
    }

    public static PEReport a2get(String xh) throws IOException {
        PEReport pr = null;

        //如果是110表示是一次空查询
        if(xh.equals("110")){
            return pr;
        }


        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //超时时间
        webClient.getOptions().setTimeout(10000);

        //进入网页
        HtmlPage page = webClient.getPage("http://210.42.72.169");

        //加载js
        //webClient.waitForBackgroundJavaScript(10000);

        //角色
        HtmlSelect select = (HtmlSelect) page.getByXPath("//select[@name='displayRole']").get(0);
        select.setSelectedAttribute("911",true);

        //用户名
        HtmlInput input1 = (HtmlInput) page.getByXPath("//input[@name='displayName']").get(0);
        //密码
        HtmlInput input2 = (HtmlInput) page.getByXPath("//input[@name='displayPasswd']").get(0);
        //登录
        HtmlInput input3 = (HtmlInput) page.getByXPath("//input[@name='loginButton']").get(0);

        //填入学号
        input1.setValueAttribute(xh);
        input2.setValueAttribute(xh);

        page = input3.click();
        //回学校接着改好这部分吧

        pr = new PEReport("123","123");
        webClient.close();
        return pr;
    }
}
