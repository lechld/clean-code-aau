package aau.edu.dolechl.cleancode.html.fetch;

import aau.edu.dolechl.cleancode.html.elements.Header;
import aau.edu.dolechl.cleancode.html.elements.Link;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class JsoupHtmlFetcher implements HtmlFetcher {

    @Override
    public HtmlFetchResult fetch(URL url) throws IOException {
        Document doc = Jsoup.connect(url.toString()).get();

        List<Header> headers = doc.select("h1, h2, h3, h4, h5, h6")
                .stream()
                .map(element -> {
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

        return new HtmlFetchResult(headers, links);
    }
}
