package com.crawlers.core;

public class Common {
    public static String buildStringFromParams(String... params){
        String paramStr = "";
        for (String param : params){
            paramStr += param + " > ";
        }
        return paramStr.substring(0, paramStr.lastIndexOf(">")-2);
    }
}
