package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.Crawler;
import aau.edu.dolechl.cleancode.crawler.CrawlerImpl;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.html.fetch.JsoupHtmlFetcher;
import aau.edu.dolechl.cleancode.input.CliCrawlParameterFactory;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        CrawlParameterFactory crawlParameterFactory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = crawlParameterFactory.create();

        if (crawlParameter == null) {
            return;
        }

        HtmlFetcher htmlFetcher = new JsoupHtmlFetcher();

        Crawler crawler = new CrawlerImpl(htmlFetcher, crawlParameter);

        String result;
        try {
            result = crawler.crawl();
        } catch (IOException e) {
            System.out.println("Internet interrupted.");
            return;
        }

        System.out.println("Finished.");
    }
}