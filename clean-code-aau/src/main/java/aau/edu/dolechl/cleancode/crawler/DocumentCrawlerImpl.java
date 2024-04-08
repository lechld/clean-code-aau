package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentHeader;
import aau.edu.dolechl.cleancode.domain.DocumentLink;
import aau.edu.dolechl.cleancode.html.elements.HtmlHeader;
import aau.edu.dolechl.cleancode.html.elements.HtmlLink;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetchResult;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.input.CrawlParameter;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class DocumentCrawlerImpl implements DocumentCrawler {

    private final HtmlFetcher htmlFetcher;

    public DocumentCrawlerImpl(HtmlFetcher htmlFetcher) {
        this.htmlFetcher = htmlFetcher;
    }

    @Override
    public Document crawlDocument(CrawlParameter crawlParameter) throws IOException {
        Document document = new Document();

        addHeadersRecursively(document, crawlParameter.url(), crawlParameter.depth(), crawlParameter.websites());

        return document;
    }

    private void addHeadersRecursively(Document document, URL url, int depth, List<String> websites) throws IOException {
        HtmlFetchResult fetchResult = htmlFetcher.fetch(url);
        addHeaders(document, fetchResult.headers(), depth);

        if (depth > 0) {
            for (HtmlLink link : fetchResult.links()) {
                boolean isRelevantSite = websites.stream().anyMatch(expectedSite -> link.url().contains(expectedSite));

                if (isRelevantSite) {
                    document.addElement(new DocumentLink(depth, link.url()));

                    addHeadersRecursively(document, new URL(link.url()), depth - 1, websites);
                }
            }
        }
    }

    private void addHeaders(Document document, List<HtmlHeader> headers, int depth) {
        for (HtmlHeader header : headers) {
            document.addElement(new DocumentHeader(depth, header.level(), header.value()));
        }
    }
}
