package com.crawlers.core;

public class Common {
    public static String buildStringFromParams(String... params){
        String paramStr = "";
        for (String param : params){
            paramStr += param + " > ";
        }
        return paramStr.substring(0, paramStr.lastIndexOf(">")-1);
    }
    public static String getCssClassname(String cssClassName){
        if(!cssClassName.startsWith(".")){
            cssClassName = "." + cssClassName;
        }
        return cssClassName;
    }
    public static String processDate(String date){
        if(!Config.propertiesMap.get(Config.existingDateFormat).isEmpty()) {
            if (Config.propertiesMap.get(Config.existingDateFormat).equalsIgnoreCase("DD MONTH YYYY")) {
                String[] dateSplitted = date.split(" ");
                if (!Config.propertiesMap.get(Config.targetDateFormat).isEmpty() &&
                        Config.propertiesMap.get(Config.targetDateFormat).equalsIgnoreCase("YYYY-MM-dd")) {
                    date = dateSplitted[2] + "-" + dateSplitted[1] + "-" + dateSplitted[0];
                }
            }
        }
        return date;
    }
}
