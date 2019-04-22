package com.crawlers.core;

import org.apache.http.HttpResponse;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public interface Reader {
    HttpResponse connect(String url) throws IOException;
    HttpResponse connect(URL url) throws IOException;
    Document read(String content);
    Elements read(Document content, String cssClassName);
    String read(Element element, String atributeName, String... cssClassNames);
    String read(Element element, String... cssClassNames);
}
