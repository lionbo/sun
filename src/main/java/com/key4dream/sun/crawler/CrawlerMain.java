package com.key4dream.sun.crawler;

import com.key4dream.sun.utils.PropertiesLoader;
import com.key4dream.sun.utils.SpringUtils;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerMain {

    private PropertiesLoader propertiesLoader = (PropertiesLoader) SpringUtils.getBean("propertiesLoader");

    public CrawlerMain() {
        this.init();
    }

    private void init() {
        String crawlStorageFolder = "/tmp/crawl";
        int numberOfCrawlers = propertiesLoader.getInteger("crawlerThreadNumer");

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(propertiesLoader.getInteger("crawlerDepth"));

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller;
        try {
            controller = new CrawlController(config, pageFetcher, robotstxtServer);
            controller.addSeed("http://www.qiushibaike.com/text");
            controller.start(QBMaterialCrawler.class, numberOfCrawlers);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
