package com.jhun.yunzhushou.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Admin {

    //管理员列表
    public static Map<String, String> MapAdmin = new HashMap<>();

    //登录成功秘钥
    public static String key = "pross";

    public static boolean enter(String root, String pass) {
        //账号密码列表
        if(key.equals("pross")) {
            MapAdmin.put("root","wangfummin");
        }

        //如果有这个账号
        if(MapAdmin.containsKey(root)){
            //如果账号密码都正确
            if(MapAdmin.get(root).equals(pass)){
                return true;
            }
        }
        return false;
    }

    public static String getKey(){
        key = getRandomString(8);
        return key;
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
