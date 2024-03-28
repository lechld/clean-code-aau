package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.html.Header;
import aau.edu.dolechl.cleancode.html.Link;

import java.util.List;

public record CrawlResult(
        List<Header> headers,
        List<Link> links
) {
}
