package com.jhun.yunzhushou.tools;

public class Tools {
    //用于阻塞当前线程
    public static void Sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
