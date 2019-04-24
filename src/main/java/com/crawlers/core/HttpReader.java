package com.crawlers.core;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

    public HttpReader() throws Exception {
        this.httpClient = HttpClientFactory.getHttpsClient();
        //this.httpClient = HttpClients.createDefault();
    }

//    @Override
//    public HttpResponse connect(String url) throws IOException {
//        HttpGet request = new HttpGet(url);
//        CloseableHttpResponse response = null;
//        try{
//            response = httpClient.execute(request);
//            System.out.println("Executing request " + request.getRequestLine());
//            System.out.println("Executing response " + EntityUtils.toString(response.getEntity()));
//        }catch (Exception ex){
//            //write logs
//        }finally {
//            httpClient.close();
//        }
//        return response;
//    }

    @Override
    public HttpResponse connect(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = null;
        try{
            response = httpClient.execute(request);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            String responseBody = httpClient.execute(request, responseHandler);
            System.out.println("Executing request " + request.getRequestLine());
            System.out.println("Executing response " + responseBody);
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
        System.out.println(Common.getCssClassyname(cssClassName));
        System.out.println(content);
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
