package com.jhun.xiaozhushou.logic;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//四六级准考证号找回方法
public class Forgot46 {
    public static boolean ClientOn = false;
    public static WebClient webClient;
    public static HtmlPage page;
    public static HtmlInput yzm;

    public static Map<String, Object> start() throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!ClientOn) {
            //打开浏览器
            webClient = new WebClient(BrowserVersion.CHROME);

            //支持AJAX
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            //超时时间
            webClient.getOptions().setTimeout(60000);

            //进入网页
            page = webClient.getPage("http://bbcjzm.neea.edu.cn/html1/folder/1508/786-1.htm");

            //加载20秒js
            webClient.waitForBackgroundJavaScript(60000);

            try {
                //找到用户名密码
                HtmlInput name = (HtmlInput) page.getByXPath("//input[@name='loginName']").get(0);
                name.setValueAttribute("874798417@qq.com");
                HtmlInput password = (HtmlInput) page.getByXPath("//input[@name='loginPwd']").get(0);
                password.setValueAttribute("EVE2TC7jhf3P9zr");

                //找到验证码
                yzm = (HtmlInput) page.getByXPath("//input[@name='verificationCode']").get(0);

                //获取验证码图片
                HtmlImage img = (HtmlImage) page.getByXPath("//img[@id='kaptchaImage']").get(0);

                //保存图片
                File file = new File("C:/XZS/img/a/a.jpg");
                if (!file.exists()) {
                    if (!file.getParentFile().exists())
                        if (!file.getParentFile().getParentFile().exists())
                            file.getParentFile().getParentFile().getParentFile().mkdir();
                    file.getParentFile().getParentFile().mkdir();
                    file.getParentFile().mkdir();
                }
                img.saveAs(file);
                System.out.println("a4开启成功，验证码已生成");
                map.put("Client", "a4开启成功，验证码已生成");
                map.put("size", "" + page.asXml().length());
            } catch (Exception e) {
                e.printStackTrace();
                map.put("asXml()", page.asXml());
            }
        }
        return map;
    }

    public static Map<String, Object> a(String a) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        //填入验证码
        yzm.setValueAttribute(a);

        //获取登录按钮
        HtmlButton button = (HtmlButton) page.getByXPath("//button[@id='login_button']").get(0);
        page = button.click();
        //webClient.waitForBackgroundJavaScript(10000);

        //检查输入结果
        if (page.getDocumentURI() != "http://bbcjzm.neea.edu.cn/html1/folder/1508/786-1.htm") {
            System.out.println("a4跳转页面不正确,可能出现了错误");
            map.put("Client", "a4跳转页面不正确,可能出现了错误");
        }

        HtmlButton button2 = (HtmlButton) page.getByXPath("//button[@name='button2']").get(0);
        page = button2.click();

        //查询考号服务开启成功，等待用户查询
        ClientOn = true;

        map.put("Client", "查询考号服务开启成功，等待用户查询");
        return map;
    }

    public static Map<String, Object> get(String CET46, String time, String xm, String sfzh) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }
}
