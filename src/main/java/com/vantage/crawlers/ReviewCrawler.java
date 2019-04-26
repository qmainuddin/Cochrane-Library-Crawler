package com.vantage.crawlers;

import com.crawlers.core.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReviewCrawler implements Callable<List<ReviewModel>> {

    private Element anchor = null;
    private String topic;
    private static Logger LOGGER = null;

    private ReviewCrawler(Element anchor, String topic){
        this.anchor = anchor;
        this.topic = topic;
    }

    public  static ReviewCrawler getInstance(Element anchor, String topic){
        return new ReviewCrawler(anchor, topic);
    }

    public static void start(){
        LOGGER = LogManager.getLogger();
        LOGGER.info("---------------- Application Started ----------------");

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<ReviewModel> reviewModels = new ArrayList<>();
        List<Future<List<ReviewModel>>> futureList = new ArrayList<>();
        int count = 0;

        try {
            Elements anchors = HttpConnector.connect(Config.propertiesMap.get(Config.searchURL)).getAnchorLinks(Config.propertiesMap.get(Config.topicLink), "a");
            LOGGER.info("Total " + anchors.size() + " topics found from " + Config.propertiesMap.get(Config.searchURL));

            for(Element anchor : anchors){
                Callable<List<ReviewModel>> listCallable = ReviewCrawler.getInstance(anchor, anchor.text());
                futureList.add(executor.submit(listCallable));
            }

            for(Future<List<ReviewModel>> reviews : futureList){
                try{

                    reviewModels.addAll(reviewModels.size(),reviews.get());
                    count++;
                    LOGGER.info("Processed " + count + " out of " + anchors.size());
                }catch (InterruptedException | ExecutionException e){
                    LOGGER.error(e);
                }
            }

            Path filePath = Paths.get(System.getProperty("user.dir"), "cochrane_reviews.txt");

            new FileManager<ReviewModel>().writeToFile(filePath, reviewModels);

            System.out.println("Done !!! Please check the output file at " + filePath);
        } catch (Exception e) {
            LOGGER.error(e);
        }finally {
            executor.shutdown();
        }
        LOGGER.info("---------------- Task Completed ----------------");
    }

    @Override
    public List<ReviewModel> call() throws Exception {
        return getReviews(this.anchor, this.topic);
    }
    public List<ReviewModel> getReviews(Element anchor, String topic){
        CustomParser reader = new HttpReader();
        List<ReviewModel> reviewModels = new ArrayList<>();

        try {
            Document newPage = Jsoup.connect(reader.readAttributes(anchor, "href")).get();

            Elements newPageAnchors = reader.read(newPage, Common.buildStringFromParams(Common.getCssClassname(Config.propertiesMap.get(Config.topicSearchResultBody))));

            for (Element element : newPageAnchors){
                String url = Config.propertiesMap.get(Config.baseURL) + element.select(Common.getCssClassname(Config.propertiesMap.get(Config.titleLink)) + " > a").attr("href");
                String title = element.select(Common.getCssClassname(Config.propertiesMap.get(Config.titleLink)) + " > a").text();
                String author = element.select(Common.getCssClassname(Config.propertiesMap.get(Config.authorsLink))).text();
                String date = Common.processDate(element.select(Common.getCssClassname(Config.propertiesMap.get(Config.dateLink))).text());
                reviewModels.add(
                        new ReviewModel(
                                url.substring(0, url.lastIndexOf("?")),
                                topic, title, author, date
                        ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewModels;
    }
}
