package com.vantage.crawlers;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.util.spi.CalendarDataProvider;

public class ApplicationContainer {

    public static void main(String[] args){
        Document doc = null;
        try {
            String url = "";
            String topic = "";
            String title = "";
            String authors = "";
            String date = "";
            String urlBase = "https://www.cochranelibrary.com";
            doc = Jsoup.connect("https://www.cochranelibrary.com/cdsr/reviews/topics").get();
            Elements links = doc.select(".browse-by-term-list");
            Elements keywords = doc.select(".browse-by-category-label");
            Elements anchors = null;
            //for(Element element : links){
                //anchors = element.select("li > a");
            anchors = links.get(0).select("li > a");
                System.out.println("-----------------------Anchors---------------------------------------");
                //for (Element elements1 : anchors){
                    //System.out.println(elements1.text() + " Links: " + elements1.attr("href"));
            url = anchors.attr("href");
            topic =  anchors.get(0).text();

            Document searchPage = Jsoup.connect(url).get();
            Elements searchResults = searchPage.select(".search-results-item-body");
            String docLink = searchResults.get(0).select(".result-title > a").attr("href");
            title = searchResults.get(0).select(".result-title > a").text();
            authors = searchResults.get(0).select(".search-result-authors").text();
            date = searchResults.get(0).select(".search-result-date").text();

            System.out.println(urlBase+ docLink + "|" + topic + "|" + title + "|" + authors + "|" + date);
                //}
            //}
        } catch (IOException e) {
            Logger.getLogger(ApplicationContainer.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
