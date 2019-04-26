package com.crawlers.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

public class HttpConnector implements Callable<Document> {
    private String url = null;

    private HttpConnector(String url){
        this.url = url;
    }

    public static HttpConnector connect(String url){
        return new HttpConnector(url);
    }

    @Override
    public Document call() throws Exception {
        return Jsoup.connect(this.url).get();
    }

    public Elements getAnchorLinks(String... cssPropertyName){
        Elements anchors = null;
        try{
            CustomParser reader = new HttpReader();
            Document response = HttpConnector.connect(url).call();
            anchors = reader.read(response, Common.buildStringFromParams(cssPropertyName));
        }catch (Exception ex){
            System.out.println(ex.getStackTrace());
        }
        return anchors;
    }
}
