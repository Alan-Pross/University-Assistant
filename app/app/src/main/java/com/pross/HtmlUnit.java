package com.pross;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
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

        //进入网页
        HtmlPage page = MainActivity.webClient.getPage("https://www.baidu.com/");

        //加载js
        //webClient.waitForBackgroundJavaScript(10000);

        try {
            //找到用户名密码
            HtmlInput xh1 = (HtmlInput) page.getByXPath("//input[@name='wd']").get(0);
            //HtmlInput xh2 = (HtmlInput) page.getByXPath("//input[@name='wd']").get(0);
            HtmlInput xh2 = (HtmlInput) page.getByXPath("//input[@name='wd']").get(0);
            HtmlSubmitInput button = (HtmlSubmitInput) page.getByXPath("//input[@id='su']").get(0);
            xh1.setValueAttribute(qsh);
            //xh2.setValueAttribute(XH);
            HtmlPage nextpage = button.click();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return pr;
    }

    public static PEReport a2get(String xh) throws IOException {
        PEReport pr = null;

        //如果是110表示是一次空查询
        if(xh.equals("110")){
            return pr;
        }

        //进入网页
        HtmlPage page = MainActivity.webClient.getPage("https://www.baidu.com/");

        //加载js
        //webClient.waitForBackgroundJavaScript(10000);

        try {
            //找到用户名密码
            HtmlInput xh1 = (HtmlInput) page.getByXPath("//input[@name='wd']").get(0);
            //HtmlInput xh2 = (HtmlInput) page.getByXPath("//input[@name='wd']").get(0);
            HtmlInput xh2 = (HtmlInput) page.getByXPath("//input[@name='wd']").get(0);
            HtmlSubmitInput button = (HtmlSubmitInput) page.getByXPath("//input[@id='su']").get(0);
            xh1.setValueAttribute(xh);
            //xh2.setValueAttribute(XH);
            HtmlPage nextpage = button.click();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return pr;
    }
}
