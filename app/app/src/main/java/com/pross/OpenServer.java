package com.pross;

public class OpenServer {

    public static void start(){
        while (MainActivity.isClosing){
            new A1Thread().start();
            new A2Thread().start();
            MainActivity.sleep(3);
        }
    }
}
