package com.key4dream.sun.crawler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.key4dream.sun.utils.PropertiesLoader;
import com.key4dream.sun.utils.SpringUtils;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlerListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(CrawlerListener.class);

    private PropertiesLoader propertiesLoader = (PropertiesLoader) SpringUtils.getBean("propertiesLoader");

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {

            Timer timer = new Timer("createSitemap", true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    logger.info("crawl" + new Date() + "-----start");
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
                        logger.error("crawler error", e);
                    }
                }
            }, 10, 3600 * 1000);

        }
    }

}
