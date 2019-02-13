package com.pross;

public class OpenServer {

    public static void start(){
        new A1Thread().start();
        new A2Thread().start();
    }
}
