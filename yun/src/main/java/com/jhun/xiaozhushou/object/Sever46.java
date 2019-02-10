package com.jhun.xiaozhushou.object;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.HashMap;
import java.util.Map;

//四六级查询服务类
public class Sever46 {
    public String openid;
    public HtmlPage page;
    public HtmlImage img;
    public WebClient webClient;

    //所有正在服务的列表
    public static Map<String, Sever46> Map46 = new HashMap<String, Sever46>();

   public Sever46(String openid, HtmlPage page, HtmlImage img, WebClient webClient){
        this.openid = openid;
        this.page = page;
        this.img = img;
        this.webClient = webClient;
    }

    //查询为openid用户服务的浏览器和信息
    public static Sever46 get(String openid){
       Sever46 sl = null;
       try {
           sl = Map46.get(openid);
       } catch (NullPointerException e){
       }
       return sl;
    }

    //关闭浏览器并清空信息
    public void close(){
        this.webClient.close();
        Map46.remove(this.openid);
        this.openid = null;
        this.page = null;
        this.img = null;
        this.webClient = null;
    }
}
