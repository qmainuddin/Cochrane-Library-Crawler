package com.crawlers.core;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public interface Reader {
    Document connect(String url) throws IOException;
    Document connect(URL url) throws IOException;
    Document read(String content);
    Elements read(Document content, String cssClassName);
    String read(Element element, String atributeName, String... cssClassNames);
    String read(Element element, String... cssClassNames);
}
