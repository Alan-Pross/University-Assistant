package com.jhun.yunzhushou.logic;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.jhun.yunzhushou.object.SeverQ46;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//四六级成绩查询方法
public class Query46 {

    private String yzm_img;

    public String getImg(String openid) throws IOException {
        yzm_img = "./yzmimg/";
        //如果服务此用户的浏览器已存在，关闭这个浏览器
        if (SeverQ46.get(openid) != null) SeverQ46.get(openid).close();

        //打开浏览器
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        //进入网页
        HtmlPage page1 = webClient.getPage("https://www.chsi.com.cn/cet/");

        //找到表单
        HtmlForm form = page1.getFormByName("form1");

        //找到验证码并点击
        HtmlTextInput yzm = form.getInputByName("yzm");
        HtmlPage page = yzm.click();

        //获取验证码图片
        HtmlImage img = (HtmlImage) page.getByXPath("//img[@id='stu_reg_vcode']").get(0);

        //保存图片
        File file = new File(yzm_img + "a3" + openid + ".jpg");
        if (!file.exists()) file.createNewFile();
        img.saveAs(file);

        //保存此网页信息
        SeverQ46.Map46.put(openid, new SeverQ46(openid, page, webClient));

        //100秒后关闭浏览器
        Runnable r = () -> {
            try {
                Thread.sleep(100000);
                SeverQ46.get(openid).close();
            } catch (Exception e) {
                System.out.println(openid + "的浏览器已关闭");
            }

        };
        new Thread(r).start();

        return "a3" + openid + ".jpg";
    }

    public Map<String, Object> result(String openid, String zkzhs, String xms, String yzms) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if(xms.equals("王茜"))  {
            map.put("r1", "黑名单用户");
            map.put("r2", "禁止使用本小程序");
            return map;
        }

        //返回
        if (SeverQ46.get(openid) == null) {
            map.put("error", "已超时或不存在");
            return map;
        }

        //得到服务此用户的网页
        HtmlPage page = SeverQ46.get(openid).page;

        //得到表单
        HtmlForm form = page.getFormByName("form1");

        //得到要输入的三个输入框
        HtmlTextInput zkzh = form.getInputByName("zkzh");
        HtmlTextInput xm = form.getInputByName("xm");
        HtmlTextInput yzm = form.getInputByName("yzm");

        //得到查询按钮
        HtmlSubmitInput cx = form.getInputByValue("查询");

        //填入信息
        zkzh.setValueAttribute(zkzhs);
        xm.setValueAttribute(xms);
        yzm.setValueAttribute(yzms);

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

        //查询完毕关闭浏览器
        SeverQ46.get(openid).close();
        return map;
    }
}