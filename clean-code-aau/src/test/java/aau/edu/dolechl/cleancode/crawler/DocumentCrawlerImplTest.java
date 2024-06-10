package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.domain.DocumentElement;
import aau.edu.dolechl.cleancode.domain.DocumentHeader;
import aau.edu.dolechl.cleancode.domain.DocumentLink;
import aau.edu.dolechl.cleancode.html.elements.HtmlHeader;
import aau.edu.dolechl.cleancode.html.elements.HtmlLink;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetchResult;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DocumentCrawlerImplTest {

    @Test
    void crawlDocumentTest() throws IOException {
        HtmlFetcher htmlFetcher = Mockito.mock(HtmlFetcher.class);

        URL url = new URL("https://example.com");
        URL nestedUrl = new URL("https://example.com/page");

        int depth = 1;
        List<String> websites = new ArrayList<>();
        websites.add("example.com");

        HtmlFetchResult fetchResult = new HtmlFetchResult(
                List.of(new HtmlHeader(1, "Header 1"), new HtmlHeader(2, "Header 2")),
                List.of(new HtmlLink(nestedUrl.toString()))
        );
        when(htmlFetcher.fetch(url)).thenReturn(fetchResult);

        HtmlFetchResult nestedFetchResult = new HtmlFetchResult(
                List.of(new HtmlHeader(1, "Nested Header 1")),
                List.of(new HtmlLink("https://example.com/subpage"))
        );
        when(htmlFetcher.fetch(nestedUrl)).thenReturn(nestedFetchResult);

        DocumentCrawlerImpl crawler = new DocumentCrawlerImpl(htmlFetcher);

        Document document = crawler.crawlDocument(url, depth, websites);

        assertEquals(4, document.getElements().size());

        List<DocumentElement> elements = document.getElements();
        DocumentHeader firstHeader = (DocumentHeader) elements.get(0);
        DocumentHeader secondHeader = (DocumentHeader) elements.get(1);
        DocumentLink nestedLink = (DocumentLink) elements.get(2);
        DocumentHeader nestedHeader = (DocumentHeader) elements.get(3);

        assertEquals(firstHeader.value(), "Header 1");
        assertEquals(firstHeader.depth(), 1);

        assertEquals(secondHeader.value(), "Header 2");
        assertEquals(secondHeader.depth(), 1);

        assertEquals(nestedLink.uri(), nestedUrl.toString());
        assertEquals(firstHeader.depth(), 1);

        assertEquals(nestedHeader.value(), "Nested Header 1");
        assertEquals(nestedHeader.depth(), 0);
    }
}