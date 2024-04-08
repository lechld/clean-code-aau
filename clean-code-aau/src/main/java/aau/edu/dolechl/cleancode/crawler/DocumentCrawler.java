package aau.edu.dolechl.cleancode.crawler;

import aau.edu.dolechl.cleancode.domain.Document;
import aau.edu.dolechl.cleancode.input.CrawlParameter;

import java.io.IOException;

public interface DocumentCrawler {
    Document crawlDocument(CrawlParameter crawlParameter) throws IOException;
}
