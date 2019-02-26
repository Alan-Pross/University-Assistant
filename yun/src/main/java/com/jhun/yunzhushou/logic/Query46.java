package com.jhun.yunzhushou.logic;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.jhun.yunzhushou.object.SeverQ46;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//四六级成绩查询方法
public class Query46 {

    private String yzm_img;

    public String getImg(String openid) throws IOException {
        yzm_img = "./yzmimg/";

        FileInputStream in = new FileInputStream(new File("./asd.jpg"));
        FileOutputStream out = new FileOutputStream(new File(yzm_img + "a3" + openid + ".jpg"));
        byte[] buff = new byte[10240]; //限制大小
        int n = 0;
        while ((n = in.read(buff)) != -1) {
            out.write(buff, 0, n);
        }
        out.flush();
        in.close();
        out.close();

        return "a3" + openid + ".jpg";
    }

    public Map<String, Object> result(String openid, String zkzhs, String xms, String yzms) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //设置
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        //进入网页
        HtmlPage page = webClient.getPage("https://www.chsi.com.cn/cet");

        webClient.waitForBackgroundJavaScript(5000);

        //得到要输入的输入框
        HtmlInput zkzh = (HtmlInput) page.getByXPath("//input[@class='input_text input_t_l']").get(0);
        HtmlInput xm = (HtmlInput) page.getByXPath("//input[@class='input_text input_t_l']").get(1);

        //得到查询按钮
        HtmlInput cx = (HtmlInput) page.getByXPath("//input[@id='submitCET']").get(0);
        //填入信息
        zkzh.setValueAttribute(zkzhs);
        xm.setValueAttribute(xms);
        //点击查询
        HtmlPage nextPage = cx.click();

        //得到成绩表格
        HtmlTable table = (HtmlTable) nextPage.getByXPath("//table[@class='cetTable']").get(0);

        //姓名
        String r1 = table.getCellAt(0, 1).asText();
        //学校
        String r2 = table.getCellAt(1, 1).asText();
        //考试级别
        String r3 = table.getCellAt(2, 1).asText();
        //笔试成绩分割行3
        //String r4 = table.getCellAt(3, 1).asText();
        //准考证号
        //String r5 = table.getCellAt(4, 1).asText();
        //总分
        String r6 = table.getCellAt(5, 1).asText();
        //听力
        String r7 = table.getCellAt(6, 2).asText();
        //阅读
        String r8 = table.getCellAt(7, 2).asText();
        //写作和翻译
        String r9 = table.getCellAt(8, 2).asText();
        //口试成绩分割行9
        //String r10 = table.getCellAt(9, 1).asText();
        //准考证号
        //String r11 = table.getCellAt(10, 1).asText();
        //等级
        String r12 = table.getCellAt(11, 1).asText();
        map.put("r1", r1);
        map.put("r2", r2);
        map.put("r3", r3);
        map.put("r6", r6);
        map.put("r7", r7);
        map.put("r8", r8);
        map.put("r9", r9);
        map.put("r12", r12);

        return map;
    }
}