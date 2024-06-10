package aau.edu.dolechl.cleancode.input;

import java.net.URL;
import java.util.List;

public record CrawlParameter(
        List<URL> urls,
        int depth,
        List<String> websites,
        String targetLanguage
) {
}