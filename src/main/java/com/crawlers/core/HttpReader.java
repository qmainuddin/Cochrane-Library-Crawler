package com.crawlers.core;

import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

@Contract(
    threading = ThreadingBehavior.SAFE
)
public class HttpReader implements Reader {
    private CloseableHttpClient httpClient = null;
    RequestConfig requestConfig;

    public HttpReader(){
        this.httpClient = HttpClients.createDefault();
        this.requestConfig = RequestConfig.custom()
                .setSocketTimeout(1000)
                .setConnectTimeout(1000)
                .build();
    }

    @Override
    public HttpResponse connect(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        request.setConfig(this.requestConfig);
        CloseableHttpResponse response = null;
        try{
            response = httpClient.execute(request);
            System.out.println("Executing request " + request.getRequestLine());
        }catch (Exception ex){
            //write logs
        }finally {
            httpClient.close();
        }
        return response;
    }

    @Override
    public HttpResponse connect(URL url) throws IOException {
        return connect(url.toString());
    }

    @Override
    public Document read(String content) {
        return Jsoup.parse(content);
    }

    @Override
    public Elements read(Document content, String cssClassName) {
        return content.select(Common.getCssClassyname(cssClassName));
    }

    @Override
    public String read(Element element, String atributeName, String... cssClassNames) {
        return element.select(Common.buildStringFromParams(cssClassNames)).attr(atributeName);
    }

    @Override
    public String read(Element element, String... cssClassNames) {
        return element.select(Common.buildStringFromParams(cssClassNames)).text();
    }
}
