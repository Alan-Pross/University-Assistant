package com.jhun.yunzhushou.logic;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.jhun.yunzhushou.object.SeverQJZ;
import com.jhun.yunzhushou.tools.Tools;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//教资成绩查询方法
public class QueryJZ {

    private String yzm_img;

    public String getImg(String openid) throws IOException {
        yzm_img = "./yzmimg/";
        //如果服务此用户的浏览器已存在，关闭这个浏览器
        if (SeverQJZ.get(openid) != null) SeverQJZ.get(openid).close();

        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        //进入网页
        HtmlPage page1 = webClient.getPage("https://ntcecf2.neea.edu.cn/");

        Page page2 = webClient.getPage("https://ntcecf2.neea.edu.cn/getYZM");

        Tools.saveImg(page2.getWebResponse().getContentAsStream(),yzm_img + "a6" + openid + ".jpg");

        //保存此网页信息
        SeverQJZ.MapJZ.put(openid, new SeverQJZ(openid, page1, webClient));

        //100秒后关闭浏览器
        Runnable r = () -> {
            try {
                Thread.sleep(100000);
                SeverQJZ.get(openid).close();
            } catch (Exception e) {
                System.out.println(openid + "的浏览器已关闭");
            }

        };
        new Thread(r).start();

        return "a6" + openid + ".jpg";
    }

    public Map<String, Object> result(String openid, String zjhs, String xms, String yzms) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (xms.equals("王茜")) return map;

        //返回
        if (SeverQJZ.get(openid) == null) {
            map.put("error", "已超时或不存在");
            return map;
        }

        //得到服务此用户的网页
        HtmlPage page = SeverQJZ.get(openid).page;

        //得到表单
        HtmlForm form = page.getFormByName("form1");

        //得到要输入的三个输入框
        HtmlTextInput zjh = form.getInputByName("zjhm");
        HtmlTextInput xm = form.getInputByName("name");
        HtmlTextInput yzm = form.getInputByName("yzm");

        //得到查询按钮
        HtmlButton dl = (HtmlButton) page.getByXPath("//button[@class='button_onmouseout']").get(0);

        //填入信息
        zjh.setValueAttribute(zjhs);
        xm.setValueAttribute(xms);
        yzm.setValueAttribute(yzms);

        //点击查询
        HtmlPage nextPage = dl.click();

        map.put("查询结果",nextPage.asText());

        //查询完毕关闭浏览器
        SeverQJZ.get(openid).close();
        return map;
    }
}