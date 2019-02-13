package com.jhun.yunzhushou.logic;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.jhun.yunzhushou.object.SeverF46;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//四六级考号找回方法
public class QueryPu {

    public static Map<String, Object> result(String sfzhs, String xms) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 取消css支持
        webClient.getOptions().setCssEnabled(false);
        //超时时间
        webClient.getOptions().setTimeout(10000);

        //进入网页
        HtmlPage page = webClient.getPage("http://www.cltt.org/studentscore");

        //得到要输入的两个输入框
        HtmlTextInput sfzh = (HtmlTextInput) page.getByXPath("//input[@name='idCard']").get(0);
        HtmlTextInput xm = (HtmlTextInput) page.getByXPath("//input[@name='name']").get(0);

        //得到查询按钮
        HtmlButton cx = (HtmlButton) page.getByXPath("//button[@class='btn-01']").get(0);

        //填入信息
        sfzh.setValueAttribute(sfzhs);
        xm.setValueAttribute(xms);

        //点击提交
        HtmlPage nextPage = cx.click();

        //得到表格
        HtmlTable table = (HtmlTable) nextPage.getByXPath("//table").get(0);

        //姓名
        String r1 = table.getCellAt(2, 1).asText();
        String r1s = r1.substring(r1.indexOf("：") + 1);
        //分数
        String r2 = table.getCellAt(2, 2).asText();
        String r2s = r2.substring(r2.indexOf("：") + 1);
        //性别
        String r3 = table.getCellAt(3, 1).asText();
        String r3s = r3.substring(r3.indexOf("：") + 1);
        //等级
        String r4 = table.getCellAt(3, 2).asText();
        String r4s = r4.substring(r4.indexOf("：") + 1);
        //准考证号
        String r5 = table.getCellAt(4, 1).asText();
        String r5s = r5.substring(r5.indexOf("：") + 1);
        //证书编号
        String r6 = table.getCellAt(4, 2).asText();
        String r6s = r6.substring(r6.indexOf("：") + 1);

        map.put("r1", r1s);
        map.put("r2", r2s);
        map.put("r3", r3s);
        map.put("r4", r4s);
        map.put("r5", r5s);
        map.put("r6", r6s);

        //查询完毕关闭浏览器
        webClient.close();
        return map;
    }

}