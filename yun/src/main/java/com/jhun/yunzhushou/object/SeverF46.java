package com.jhun.yunzhushou.object;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.HashMap;
import java.util.Map;

//四六级查询服务类
public class SeverF46 {
    public String rdid;
    public HtmlPage page;
    public HtmlImage img;
    public WebClient webClient;

    //所有正在服务的列表
    public static Map<String, SeverF46> Map46 = new HashMap<String, SeverF46>();

   public SeverF46(String rdid, HtmlPage page, HtmlImage img, WebClient webClient){
        this.rdid = rdid;
        this.page = page;
        this.img = img;
        this.webClient = webClient;
    }

    //查询为rdid用户服务的浏览器和信息
    public static SeverF46 get(String rdid){
       SeverF46 sl = null;
       try {
           sl = Map46.get(rdid);
       } catch (NullPointerException e){
       }
       return sl;
    }

    //关闭浏览器并清空信息
    public void close(){
        webClient.getCookieManager().clearCookies();
        this.webClient.close();
        Map46.remove(this.rdid);
        this.rdid = null;
        this.page = null;
        this.img = null;
        this.webClient = null;
    }
}
