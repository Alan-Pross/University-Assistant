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
public class Forgot46 {

    final private static String img_path = "C:/YZS/img/";

    public static String getImg(String openid) throws IOException {
        //如果服务此用户的浏览器已存在，关闭这个浏览器
        if (SeverF46.get(openid) != null) SeverF46.get(openid).close();

        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        // 取消css支持
        webClient.getOptions().setCssEnabled(false);
        //超时时间
        webClient.getOptions().setTimeout(10000);

        //进入网页
        HtmlPage page1 = webClient.getPage("http://cet.etest.net.cn/Home/QuickPrintTestTicket");

        //填写省份
        HtmlSelect select1 = (HtmlSelect) page1.getByXPath("//select[@id='selProvince']").get(0);
        HtmlPage page2 = select1.setSelectedAttribute("42", true);

        //填写证件类型
        HtmlSelect select2 = (HtmlSelect) page2.getByXPath("//select[@id='selIDType']").get(0);
        HtmlPage page3 = select2.setSelectedAttribute("1", true);

        //获取验证码图片
        HtmlImage img = (HtmlImage) page3.getByXPath("//img[@id='vcodeImg']").get(0);

        //保存图片
        File file = new File(img_path + "a4" + openid + ".jpg");
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().getParentFile().mkdir();
            file.getParentFile().mkdir();
        }
        img.saveAs(file);

        //保存此网页信息
        SeverF46.Map46.put(openid, new SeverF46(openid, page3, img, webClient));

        //100秒后关闭浏览器
        Runnable r = () -> {
            try {
                Thread.sleep(100000);
                SeverF46.get(openid).close();
            } catch (Exception e) {
                System.out.println(openid + "的浏览器已关闭");
            }

        };
        new Thread(r).start();

        return "a4" + openid + ".jpg";
    }

    public static Map<String, Object> result(String openid, String sfzhs, String xms, String yzms) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        //返回
        if (SeverF46.get(openid) == null) {
            map.put("error", "已超时或不存在");
            return map;
        }

        //得到服务此用户的网页
        HtmlPage page = SeverF46.get(openid).page;

        //得到要输入的三个输入框
        HtmlTextInput sfzh = (HtmlTextInput) page.getByXPath("//input[@id='txtIDNumber']").get(0);
        HtmlTextInput xm = (HtmlTextInput) page.getByXPath("//input[@id='txtName']").get(0);
        HtmlTextInput yzm = (HtmlTextInput) page.getByXPath("//input[@id='txtVerificationCode']").get(0);

        //得到提交按钮
        HtmlButton tj = (HtmlButton) page.getByXPath("//button[@id='ibtnLogin']").get(0);

        //填入信息
        sfzh.setValueAttribute(sfzhs);
        xm.setValueAttribute(xms);
        yzm.setValueAttribute(yzms);

        //点击提交
        HtmlPage nextPage = tj.click();

        //得到表格
        HtmlTable table = (HtmlTable) nextPage.getByXPath("//table[@class='table table-bordered table-hover ']").get(0);

        //得到单元格
        HtmlTableCell t = table.getCellAt(1, 1);

        //截取字符串并填入SID
        map.put("SID", t.asXml().substring(48, 176));

        //查询完毕关闭浏览器
        SeverF46.get(openid).close();
        return map;
    }
}