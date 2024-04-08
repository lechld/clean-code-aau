package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.crawler.DocumentCrawlerImpl;
import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentWriter;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.html.fetch.JsoupHtmlFetcher;
import aau.edu.dolechl.cleancode.input.CliCrawlParameterFactory;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;
import aau.edu.dolechl.cleancode.markdown.MarkdownDocumentWriter;

import java.io.FileWriter;
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

        DocumentWriter writer = null;
        try {
            writer = new MarkdownDocumentWriter(new FileWriter("crawl.md"));
            writer.write(crawlParameter, doc);

            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Can't write to file.");
        }

        System.out.println("Finished.");
    }
}