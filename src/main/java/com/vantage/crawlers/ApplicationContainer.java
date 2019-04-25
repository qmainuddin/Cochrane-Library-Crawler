package com.vantage.crawlers;

import com.crawlers.core.Common;
import com.crawlers.core.HttpReader;
import com.crawlers.core.Reader;
import com.sun.deploy.net.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.*;

public class ApplicationContainer {

    public static void main(String[] args){
        Document doc = null;
        try {
            ApplicationContainer applicationContainer = new ApplicationContainer();
            String url = "";
            String topic = "";
            String title = "";
            String authors = "";
            String date = "";
            String urlBase = "https://www.cochranelibrary.com";
            Elements anchors = applicationContainer.getAnchorLinks("https://www.cochranelibrary.com/cdsr/reviews/topics", "browse-by-list-item");



            System.out.println("----------------------------Anchors---------------------------------------");
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
            //Logger.getLogger(ApplicationContainer.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getStackTrace());
        }
    }

    public void StartTraversing(Elements anchors){

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (Element anchor : anchors){

        }
    }
    public Elements getAnchorLinks(String url, String cssPropertyName){
        Elements anchors = null;
        try{
            Reader reader = new HttpReader();
            Document response = reader.connect(url);
            anchors = reader.read(response, Common.buildStringFromParams(cssPropertyName, "a"));
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
        System.out.println(anchors);
        return anchors;
    }
}
