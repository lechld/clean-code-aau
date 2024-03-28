package aau.edu.dolechl.cleancode.crawler;

import java.io.IOException;

public interface Crawler {

    CrawlResult crawl() throws IOException;
}
