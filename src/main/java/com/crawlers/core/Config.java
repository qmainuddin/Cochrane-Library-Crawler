package com.crawlers.core;

import java.util.*;

public class Config {
    private static ResourceBundle properties;
    public static String baseURL = "base-url";
    public static String searchURL = "search-url";
    public static String topicLink = "topic-link";
    public static String topicSearchResultBody = "topic-search-result-body";
    public static String titleLink = "title-link";
    public static String authorsLink = "authors";
    public static String dateLink = "date";
    public static String existingDateFormat  = "date-format";
    public static String targetDateFormat = "target-date-format";

    public static Map<String, String> propertiesMap;

    static {
        properties = ResourceBundle.getBundle("config");
        propertiesMap = new HashMap<>();
        List<String> keys = Collections.list(properties.getKeys());
        for (String key : keys){
            propertiesMap.put(key, properties.getString(key));
        }
    }
}
