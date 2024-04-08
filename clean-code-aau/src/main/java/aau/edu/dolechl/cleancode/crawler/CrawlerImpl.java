package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.html.elements.Header;
import aau.edu.dolechl.cleancode.html.elements.Link;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetchResult;
import aau.edu.dolechl.cleancode.html.fetch.HtmlFetcher;
import aau.edu.dolechl.cleancode.input.CrawlParameter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CrawlerImpl implements Crawler {

    private final HtmlFetcher htmlFetcher;

    private final CrawlParameter crawlParameter;

    public CrawlerImpl(HtmlFetcher htmlFetcher, CrawlParameter crawlParameter) {
        this.htmlFetcher = htmlFetcher;
        this.crawlParameter = crawlParameter;
    }

    @Override
    public String crawl() throws IOException {
        StringBuilder result = new StringBuilder();

        return crawlInternal(
                crawlParameter.url().toString(),
                0,
                crawlParameter.depth(),
                result
        ).toString();
    }

    private String getIndentionByLevel(int level) {
        if (level == 0) return "";

        return "--".repeat(level) + "> ";
    }

    private StringBuilder crawlInternal(
            String url,
            int currentLevel,
            int maxLevel,
            StringBuilder document
    ) throws IOException {
        String indention = getIndentionByLevel(currentLevel);

        URL currentUrl;
        try {
            currentUrl = new URL(url);

            if (currentLevel != 0) {
                document.append(indention).append("link to: ").append(url).append('\n');
            }

        } catch (MalformedURLException e) {
            document.append(indention).append("broken link: ").append(url).append('\n');
            return document;
        }

        HtmlFetchResult fetchResult = htmlFetcher.fetch(currentUrl);

        for (Header header : fetchResult.headers()) {
            String headerPrefix = "######".substring(0, header.level());

            document.append(indention)
                    .append(headerPrefix)
                    .append(" ")
                    .append(header.value())
                    .append('\n');
        }

        if (currentLevel == maxLevel) {
            return document;
        }

        for (Link link : fetchResult.links()) {
            if (crawlParameter.websites().isEmpty() || crawlParameter.websites().contains(link.url())) {
                document = crawlInternal(link.url(), currentLevel + 1, maxLevel, document);
            }
        }

        return document;
    }
}
