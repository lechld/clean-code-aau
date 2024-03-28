package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.html.Header;
import aau.edu.dolechl.cleancode.html.Link;
import aau.edu.dolechl.cleancode.input.CrawlParameter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class CrawlerImpl implements Crawler {

    private final CrawlParameter crawlParameter;

    public CrawlerImpl(CrawlParameter crawlParameter) {
        this.crawlParameter = crawlParameter;
    }

    @Override
    public CrawlResult crawl() throws IOException {
        Document doc = Jsoup.connect(crawlParameter.url().toString()).get();

        List<Header> headers = doc.select("h1, h2, h3, h4, h5, h6").stream().map(element -> {
            int level = Integer.parseInt(element.tag().getName().substring(1));
            String text = element.text();

            return new Header(level, text);
        }).toList();

        List<Link> links = doc.select("a[href^=http], a[href^=https]")
                .stream()
                .map(element -> {
                    String ref = element.attr("href");
                    return new Link(ref);
                })
                .toList();

        return new CrawlResult(headers, links);

    }
}
