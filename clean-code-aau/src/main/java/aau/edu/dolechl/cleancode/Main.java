package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.CrawlResult;
import aau.edu.dolechl.cleancode.crawler.Crawler;
import aau.edu.dolechl.cleancode.crawler.CrawlerImpl;
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

        Crawler crawler = new CrawlerImpl(crawlParameter);
        CrawlResult crawlResult;
        try {
            crawlResult = crawler.crawl();
        } catch (IOException e) {
            System.out.println("Internet interrupted.");
            return;
        }

        System.out.println("Received result.");
    }
}