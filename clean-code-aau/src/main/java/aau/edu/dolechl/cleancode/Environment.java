package aau.edu.dolechl.cleancode;

import aau.edu.dolechl.cleancode.crawler.DocumentCrawler;
import aau.edu.dolechl.cleancode.domain.DocumentWriter;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.input.CrawlParameterFactory;
import aau.edu.dolechl.cleancode.translator.DocumentTranslator;

import java.io.IOException;

public interface Environment {
    CrawlParameterFactory getCrawlParameterFactory();

    HtmlFetcher getHtmlFetcher();

    DocumentCrawler getDocumentCrawler();

    DocumentTranslator getDocumentTranslator();

    DocumentWriter getDocumentWriter(String fileIdentifier) throws IOException;
}
