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
import aau.edu.dolechl.cleancode.translator.DeepLTranslationWrapper;
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;
import aau.edu.dolechl.cleancode.translator.DocumentTranslatorImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class CrawlerApplication {
    public void run(String[] args) {
        CrawlParameterFactory crawlParameterFactory = new CliCrawlParameterFactory(args);
        CrawlParameter crawlParameter = crawlParameterFactory.create();

        if (crawlParameter == null) {
            return;
        }

        HtmlFetcher htmlFetcher = createHtmlFetcher();
        DocumentCrawler crawler = createDocumentCrawler(htmlFetcher);
        Document doc;

        try {
            doc = crawler.crawlDocument(crawlParameter);
        } catch (IOException e) {
            System.out.println("Internet interrupted.");
            return;
        }

        DocumentTranslator translator = createDocumentTranslator();
        doc = translator.translate(doc, crawlParameter.targetLanguage());

        try {
            DocumentWriter writer = createDocumentWriter();
            writer.write(crawlParameter, doc);
            System.out.println("Crawl written to " + new File("crawl.md").getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Can't write to file.");
        }

        System.out.println("Finished.");
    }

    private HtmlFetcher createHtmlFetcher() {
        return new JsoupHtmlFetcher();
    }

    private DocumentCrawler createDocumentCrawler(HtmlFetcher htmlFetcher) {
        return new DocumentCrawlerImpl(htmlFetcher);
    }

    private DocumentTranslator createDocumentTranslator() {
        DeepLTranslationWrapper deepLTranslationWrapper = new DeepLTranslationWrapper();
        return new DocumentTranslatorImpl(deepLTranslationWrapper);
    }

    private DocumentWriter createDocumentWriter() throws IOException {
        File file = new File("crawl.md");
        return new MarkdownDocumentWriter(new FileWriter(file));
    }
}
