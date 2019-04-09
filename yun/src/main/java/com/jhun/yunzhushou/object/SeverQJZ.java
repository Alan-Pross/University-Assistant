package com.jhun.yunzhushou.object;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.util.HashMap;
import java.util.Map;

//四六级查询服务类
public class SeverQJZ {
    public String openid;
    public HtmlPage page;
    public WebClient webClient;

    //所有正在服务的列表
    public static Map<String, SeverQJZ> MapJZ = new HashMap<String, SeverQJZ>();

   public SeverQJZ(String openid, HtmlPage page, WebClient webClient){
        this.openid = openid;
        this.page = page;
        this.webClient = webClient;
    }

    //查询为openid用户服务的浏览器和信息
    public static SeverQJZ get(String openid){
       SeverQJZ sl = null;
       try {
           sl = MapJZ.get(openid);
       } catch (NullPointerException e){
       }
       return sl;
    }

    //关闭浏览器并清空信息
    public void close(){
        webClient.getCookieManager().clearCookies();
        this.webClient.close();
        MapJZ.remove(this.openid);
        this.openid = null;
        this.page = null;
        this.webClient = null;
    }
}
