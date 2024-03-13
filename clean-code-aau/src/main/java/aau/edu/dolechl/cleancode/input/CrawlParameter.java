package aau.edu.dolechl.cleancode.input;

import java.net.URL;

public record CrawlParameter(
        URL url,
        int depth,
        String targetLanguage
) {
}