package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.crawler.DocumentCrawlerImpl;
import aau.edu.dolechl.cleancode.domain.Document;
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
        DocumentCrawler crawler = new DocumentCrawlerImpl(htmlFetcher);

        Document doc = null;
        try {
            doc = crawler.crawlDocument(crawlParameter);
        } catch (IOException e) {
            System.out.println("Internet interrupted.");
            return;
        }

        System.out.println("Finished.");
    }
}