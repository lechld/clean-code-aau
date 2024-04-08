package aau.edu.dolechl.cleancode.input;

import java.net.URL;
import java.util.List;

public record CrawlParameter(
        URL url,
        int depth,
        List<String> websites,
        String targetLanguage
) {
}