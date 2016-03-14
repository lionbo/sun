package com.key4dream.sun.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.key4dream.sun.utils.CacheMapNeverDel;
import com.key4dream.sun.utils.Constants;
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
                    crawlQb();
                    crawCl();
                }

                private void crawlQb() {
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

                private void crawCl() {
                    boolean hasError = false;
                    for (int i = 0; i < 10; i++) {
                        org.jsoup.nodes.Document document;
                        List<String> keywords = new ArrayList<String>();
                        keywords.add("地址1");
                        keywords.add("地址2");
                        keywords.add("地址3");
                        keywords.add("地址4");
                        keywords.add("手机地址");
                        try {
                            document = Jsoup.parse(new URL("https://groups.yahoo.com/neo/groups/caoliushequn/info"),
                                    1000 * 60);
                            Elements selects = document.select("div.section-row-desc a");
                            if (selects != null) {
                                Map<String, String> urlList = new HashMap<String, String>();
                                for (org.jsoup.nodes.Element element : selects) {
                                    if (keywords.contains(element.ownText().trim())) {
                                        urlList.put(element.ownText(), element.attr("abs:href"));
                                    }
                                }
                                if (urlList != null && urlList.size() > 0) {
                                    Map<String, String> existList = (Map<String, String>) CacheMapNeverDel.instance()
                                            .get(Constants.KEY_WORD_WYCL);
                                    if (existList == null) {
                                        existList = new HashMap<String, String>();
                                    }
                                    boolean haschanged = false;
                                    for (Entry<String, String> entry : urlList.entrySet()) {
                                        if (existList.get(entry.getKey()) != null
                                                && !existList.get(entry.getKey()).equalsIgnoreCase(entry.getValue())) {
                                            CacheMapNeverDel.instance().put(Constants.KEY_WORD_WYCL, urlList);
                                            haschanged = true;
                                            break;
                                        }
                                    }
                                    if (haschanged) {
                                        logger.info("get new cllist:" + urlList);
                                    } else {
                                        logger.info("current cllist:" + urlList);
                                    }

                                }
                            }
                        } catch (MalformedURLException e) {
                            logger.error("cllist error", e);
                            hasError = true;
                        } catch (IOException e) {
                            logger.error("cllist error", e);
                            hasError = true;
                        } catch (Exception e) {
                            logger.error("cllist error", e);
                            hasError = true;
                        }
                        if (!hasError) {
                            break;
                        }
                    }
                }

            }, 10, 3600 * 1000);

        }
    }

}
