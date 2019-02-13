package com.jhun.yunzhushou.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Tools {
    //用于阻塞当前线程
    public static void Sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //用于记录信息
    public static void logi(String path,String str){
        try {
            File file = new File(path);
            if(file.exists()){
                FileWriter fw = new FileWriter(file,true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.newLine();
                bw.write(str);
                bw.close();
                fw.close();
            }
        } catch (Exception e) {
            System.out.println("将内容{" + str +"}写入文件\"" + path + "\"的请求失败，原因是" + e.toString());
        }
    }
}
