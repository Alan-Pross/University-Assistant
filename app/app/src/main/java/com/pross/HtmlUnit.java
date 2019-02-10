package com.pross;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.pross.object.PEReport;
import com.pross.object.PowerRate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlUnit {

    public static PowerRate a1get(String qsh) throws IOException {
        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        //超时时间
        webClient.getOptions().setTimeout(10000);

        //进入网页
        HtmlPage page = webClient.getPage("https://www.baidu.com/");

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

        PowerRate pr = null;
        return pr;
    }

    public static PEReport a2get(String xh) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        //支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        //超时时间
        webClient.getOptions().setTimeout(10000);

        //进入网页
        HtmlPage page = webClient.getPage("https://www.baidu.com/");

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
            map.put("nextpage.asXml()", nextpage.asXml());

        } catch (NullPointerException e) {
            e.printStackTrace();
            map.put("page.asXml()", page.asXml());
        }

        PEReport pr = null;
        return pr;
    }
}
