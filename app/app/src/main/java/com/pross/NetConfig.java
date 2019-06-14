package com.pross;

public class NetConfig {
    public final static String UrlMain = "https://www.jhuncloud.com/";
    public final static String UrlA1 = "transa1";
    public final static String UrlA2 = "transa2";
    public final static String UrlLog = "applog";
    public final static String UrlUpdate = "appupdate";
    public static String getUrl(String UrlName){
        switch (UrlName) {
            case "A1":return UrlMain + UrlA1;
            case "A2":return UrlMain + UrlA2;
            case "Log":return UrlMain + UrlLog;
            case "Update":return UrlMain + UrlUpdate;
            default: return UrlMain;
        }
    }
}
