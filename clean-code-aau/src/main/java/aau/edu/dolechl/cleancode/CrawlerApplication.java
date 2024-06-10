package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentWriter;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;

import java.io.File;
import java.io.IOException;

class CrawlerApplication {
    public void run(
            CrawlParameterFactory crawlParameterFactory,
            DocumentCrawler crawler,
            DocumentTranslator translator,
            DocumentWriter writer
    ) {
        CrawlParameter crawlParameter = crawlParameterFactory.create();

        if (crawlParameter == null) {
            return;
        }

        Document doc;

        try {
            doc = crawler.crawlDocument(crawlParameter);
        } catch (IOException e) {
            System.out.println("Internet interrupted.");
            return;
        }

        doc = translator.translate(doc, crawlParameter.targetLanguage());
        writer.write(crawlParameter, doc);
        System.out.println("Crawl written to " + new File("crawl.md").getAbsolutePath());
    }
}
