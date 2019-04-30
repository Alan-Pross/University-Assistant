package com.jhun.yunzhushou.logic;

import com.jhun.yunzhushou.object.AppOnline;

public class ServerOnline {

    public static boolean get(){
        return ((System.currentTimeMillis() - AppOnline.A1online) < (4*1000));
    }
}
