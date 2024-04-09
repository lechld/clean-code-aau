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
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;
import aau.edu.dolechl.cleancode.translator.DocumentTranslatorImpl;

import java.io.File;
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

        DocumentTranslator translator = new DocumentTranslatorImpl();
        doc = translator.translate(doc, crawlParameter.targetLanguage());

        DocumentWriter writer;
        try {
            File file = new File("crawl.md");
            writer = new MarkdownDocumentWriter(new FileWriter(file));
            writer.write(crawlParameter, doc);

            System.out.println("Crawl written to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Can't write to file.");
        }

        System.out.println("Finished.");
    }
}