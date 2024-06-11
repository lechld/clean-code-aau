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

import java.io.FileWriter;
import java.io.IOException;

public class DefaultEnvironment implements Environment {

    private final String[] args;

    public DefaultEnvironment(String[] args) {
        this.args = args;
    }

    @Override
    public CrawlParameterFactory getCrawlParameterFactory() {
        return new CliCrawlParameterFactory(args);
    }

    @Override
    public HtmlFetcher getHtmlFetcher() {
        return new JsoupHtmlFetcher();
    }

    @Override
    public DocumentCrawler getDocumentCrawler() {
        HtmlFetcher htmlFetcher = getHtmlFetcher();
        return new DocumentCrawlerImpl(htmlFetcher);
    }

    @Override
    public DocumentTranslator getDocumentTranslator() {
        DeepLTranslationWrapper deepLTranslationWrapper = new DeepLTranslationWrapper();
        return new DocumentTranslatorImpl(deepLTranslationWrapper);
    }

    @Override
    public DocumentWriter getDocumentWriter(String fileIdentifier) throws IOException {
        return new MarkdownDocumentWriter(new FileWriter(fileIdentifier, true));
    }
}
