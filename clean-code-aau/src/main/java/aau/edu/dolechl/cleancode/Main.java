package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.crawler.DocumentCrawlerImpl;
import aau.edu.dolechl.cleancode.domain.DocumentWriter;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.html.fetch.JsoupHtmlFetcher;
import aau.edu.dolechl.cleancode.input.CliCrawlParameterFactory;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;
import aau.edu.dolechl.cleancode.markdown.MarkdownDocumentWriter;
import aau.edu.dolechl.cleancode.translator.DeepLTranslationWrapper;
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;
import aau.edu.dolechl.cleancode.translator.DocumentTranslatorImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        CrawlParameterFactory crawlParameterFactory = createCrawlParameterFactory(args);
        DocumentCrawler crawler = createDocumentCrawler(createHtmlFetcher());
        DocumentTranslator translator = createDocumentTranslator();
        DocumentWriter writer;
        try {
            writer = createDocumentWriter();
        } catch (IOException e) {
            System.err.println("Unexpected error occurred.");
            return;
        }
        CrawlerApplication app = new CrawlerApplication();

        app.run(crawlParameterFactory, crawler, translator, writer);
    }

    private static CrawlParameterFactory createCrawlParameterFactory(String[] args) {
        return new CliCrawlParameterFactory(args);
    }

    private static HtmlFetcher createHtmlFetcher() {
        return new JsoupHtmlFetcher();
    }

    private static DocumentCrawler createDocumentCrawler(HtmlFetcher htmlFetcher) {
        return new DocumentCrawlerImpl(htmlFetcher);
    }

    private static DocumentTranslator createDocumentTranslator() {
        DeepLTranslationWrapper deepLTranslationWrapper = new DeepLTranslationWrapper();
        return new DocumentTranslatorImpl(deepLTranslationWrapper);
    }

    private static DocumentWriter createDocumentWriter() throws IOException {
        File file = new File("crawl.md");
        return new MarkdownDocumentWriter(new FileWriter(file));
    }
}