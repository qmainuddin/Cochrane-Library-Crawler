package com.crawlers.core;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface CustomParser {
    Elements read(Document content, String cssClassName);
    String readAttributes(Element element, String atributeName);
    String read(Element element, String... cssClassNames);
}
