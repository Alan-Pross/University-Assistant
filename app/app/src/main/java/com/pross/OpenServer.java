package com.pross;

public class OpenServer {

    public static void start(){
        print();
        if(!MainActivity.isWifiConnected()){
            MainActivity.print("当前网络非WIFI状态，开启服务失败");
            MainActivity.isClosed = true;
            return;
        }
        if(!MainActivity.isJhunWIFI()){
            MainActivity.print("当前网络非江大WIFI，服务器可能无法正常工作");
        }
        new A1Thread().start();
        //new A2Thread().start();
        MainActivity.print("开启服务成功");
    }

    static void print(){
        MainActivity.print("Jhun-Cloud app by ProSS");
        MainActivity.print("|_|   |_|   \\___(______(______/");
        MainActivity.print("| |   | |  | |_| |____) )____) )");
        MainActivity.print("|  ____/ ___) _ \\\\____ \\\\____ \\");
        MainActivity.print(" _____) )___ ___( (____( (____");
        MainActivity.print("(_____ \\         / _____) _____)");
        MainActivity.print(" ______           ______ ______");
    }
}
