package com.crawlers.core;

import com.sun.deploy.net.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;


public class HttpReader implements Reader {

    @Override
    public Document connect(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    @Override
    public Document connect(URL url) throws IOException {
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
