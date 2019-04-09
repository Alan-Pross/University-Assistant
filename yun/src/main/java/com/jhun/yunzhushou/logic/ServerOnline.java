package com.jhun.yunzhushou.logic;

import com.jhun.yunzhushou.object.A1A2online;

public class ServerOnline {

    public static boolean get(String which){
        switch (which){
            case "A1":{
                return ((System.currentTimeMillis() - A1A2online.A1online) < (4*1000));
            }
            case "A2":{
                return ((System.currentTimeMillis() - A1A2online.A2online) < (4*1000));
            }
        }
        return false;
    }
}
