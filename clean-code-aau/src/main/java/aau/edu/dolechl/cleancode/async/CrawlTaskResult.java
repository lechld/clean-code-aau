package aau.edu.dolechl.cleancode.async;

import aau.edu.dolechl.cleancode.domain.Document;

import java.net.URL;

public record CrawlTaskResult(
        Document document,
        URL url
) {
}