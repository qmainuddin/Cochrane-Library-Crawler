package com.vantage.crawlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewCrawlerTest {

    @Test
    void start() {
        assertTrue(ReviewCrawler.start());
    }
}