package com.pross;

public class OpenServer {

    public static void start(){
        while (MainActivity.isClosing){
            MainActivity.print("循环一次上传");
            new A1Thread().start();
            new A2Thread().start();
            MainActivity.sleep(3);
        }
    }
}
