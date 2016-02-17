package com.key4dream.sun.crawler;

import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.key4dream.sun.dao.MaterialMapper;
import com.key4dream.sun.entity.Material;
import com.key4dream.sun.utils.SpringUtils;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

@Component(value = "qbMaterialCrawler")
public class QBMaterialCrawler extends WebCrawler {

    private static Logger logger = LoggerFactory.getLogger(QBMaterialCrawler.class);

    private MaterialMapper mapper = (MaterialMapper) SpringUtils.getBean("materialMapper");

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches() && href.startsWith("http://www.qiushibaike.com/text");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            Document doc = Jsoup.parse(html);
            Elements articles = doc.select("div.article");
            if (articles != null) {
                for (Element article : articles) {
                    Elements contents = article.select("div.content");
                    String midStr = article.id();
                    String[] midparts = midStr.split("_");
                    Long mid = Long.valueOf(midparts[midparts.length - 1]);
                    if (contents != null && contents.size() > 0) {
                        Element content = contents.get(0);
                        Material material = new Material();
                        material.setMid(mid);
                        material.setContent(content.ownText());
                        mapper.add(material);
                        logger.info(material.toString());
                    }
                }
            }

        }

    }

    public static void main(String[] args) throws Exception {
        CrawlerMain main = new CrawlerMain();
    }

}
