package aau.edu.dolechl.cleancode.input;

public record CrawlParameter(
        String url,
        int depth,
        String targetLanguage
) {
}