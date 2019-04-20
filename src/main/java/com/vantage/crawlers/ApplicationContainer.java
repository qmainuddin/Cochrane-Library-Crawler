package com.vantage.crawlers;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;

public class ApplicationContainer {

    public static void main(String[] args){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.cochranelibrary.com/cdsr/reviews/topics").get();
            Elements links = doc.select(".browse-by-term-list");
            Elements keywords = doc.select(".browse-by-category-label");
            Elements anchors = null;
            for(Element element : links){
                anchors = element.select("li > a");
                System.out.println("-----------------------Anchors---------------------------------------");
                for (Element elements1 : anchors){
                    System.out.println(elements1.text() + " Links: " + elements1.attr("href"));
                }
            }
        } catch (IOException e) {
            Logger.getLogger(ApplicationContainer.class.getName()).log(Level.SEVERE, null, e);
        }
        String title = doc.title();
        System.out.println("title is: " + title);
    }
}
