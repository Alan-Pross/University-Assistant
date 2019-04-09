package com.jhun.yunzhushou.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    //用于阻塞当前线程
    public static void Sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //用于读取文件
    public static String readTXT(String path){
        StringBuffer buffer = new StringBuffer();
        try {
            String line;
            InputStream is = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
            while (line != null) {
                buffer.append(line);
                line = reader.readLine();
            }
            reader.close();
            is.close();

        } catch (Exception e) {
            System.out.println("读取文件\"" + path + "\"的请求失败，原因是" + e.toString());
        }
            return buffer.toString();
    }

    //用于记录信息
    public static void logi(String path,String str){
        try {
            File file = new File(path);
            if(file.exists()) file.createNewFile();
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(str);
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("将内容{" + str +"}写入文件\"" + path + "\"的请求失败，原因是" + e.toString());
        }
    }

    //把图片流保存成文件
    public static void saveImg(InputStream inputStream, String path) {
        byte[] data = new byte[1024];
        int len;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //得到时间
    public static String getTime(String format){
        Date now = new Date( );
        SimpleDateFormat ft;
        if(format.isEmpty())
            ft = new SimpleDateFormat("MMdd HH:mm:ss|");
        else
            ft = new SimpleDateFormat(format);
        return ft.format(now);
    }
}
