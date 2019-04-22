package com.crawlers.core;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Common {
    public static String buildStringFromParams(String... params){
        String paramStr = "";
        for (String param : params){
            paramStr += param + " > ";
        }
        return paramStr.substring(0, paramStr.lastIndexOf(">")-2);
    }
    public static String dateProcessor(String date){
        String[] partsOfDate = date.split(" ");
        if(partsOfDate.length >= 3){
            LocalDate localDate = LocalDate.of(Integer.parseInt(partsOfDate[2]), Month.valueOf(partsOfDate[1]), Integer.parseInt(partsOfDate[0]));
            return localDate.toString();
        }else {

        }
        return "";
    }
}
