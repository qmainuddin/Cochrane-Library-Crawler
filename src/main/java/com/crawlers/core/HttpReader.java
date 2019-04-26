package com.crawlers.core;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HttpReader implements CustomParser {

    @Override
    public Elements read(Document content, String cssClassName) {
        return content.select(Common.getCssClassname(cssClassName));
    }

    @Override
    public String readAttributes(Element element, String atributeName) {
        return element.attr(atributeName);
    }

    @Override
    public String read(Element element, String... cssClassNames) {
        return element.select(Common.buildStringFromParams(cssClassNames)).text();
    }
}
